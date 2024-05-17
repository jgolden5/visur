package com.ple.visur;

import CursorPositionDC.CursorPositionDCHolder;
import DataClass.CompoundDataClassBrick;
import DataClass.PrimitiveDataClassBrick;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ple.visur.EditorMode.editing;
import static com.ple.visur.EditorModelKey.globalVariableMap;

public class InitializerService {

  private final EditorModelCoupler emc;

  public static InitializerService make(EditorModelCoupler editorModelCoupler) {
    return new InitializerService(editorModelCoupler);
  }

  private InitializerService(EditorModelCoupler editorModelCoupler) {
    this.emc = editorModelCoupler;
    initializeEditorModel();
  }

  public void initializeEditorModel() {
    VariableMap initialGvm = new VariableMap(new HashMap<>());
    emc.editorModel.put(globalVariableMap, initialGvm);
    emc.putVirtualX(0);
    emc.putVirtualXIsAtEndOfLine(false);
    emc.putEditorMode(editing);
    emc.putKeyBuffer(KeysPressed.from(new KeyPressed[]{}));
    emc.putExecutionDataStack(new ExecutionDataStack());
    final String initialEditorContent = "Hello world\n" +
      "How are you?\n" +
      "Goodbye";
//    final String initialEditorContent = "Qazlal Stormbringer is a violent god of tempests, who delights in unleashing the forces of nature against the unsuspecting.\n" +
//      "Those who invite Qazlal's gaze will find themselves the eye in a storm of elemental destruction, from which only their god can protect them.\n" +
//      "Pious worshippers of Qazlal will gain the ability to direct and control the destructive might of the storm.\n" +
//      "Followers of Qazlal are protected from the clouds they create.\n";
    emc.putEditorContent(initialEditorContent);

    emc.putIsInCommandState(false);
    emc.putCommandStateContent("");
    emc.putCommandCursor(emc.getCommandStateContent().length());

    initializeCoordinates();

    initializeQuantums();

    initializeKeymaps();

    initializeHandlers();

    OperatorToService opToService = OperatorToService.make();
    emc.putOperatorToService(opToService);
  }

  private void initializeCoordinates() {
    CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
    emc.putCursorPositionDCHolder(cursorPositionDCHolder);
    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick();
    ArrayList<Integer> newlineIndices = emc.getNewlineIndices();
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cursorPosDCB.getInner("ni");
    CompoundDataClassBrick cxcycaDCB = (CompoundDataClassBrick) cursorPosDCB.getInner("cxcyca");
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    //the below 2 methods will be uncommented after putSafe is tested in TestDC
//    niDCB.putSafe(newlineIndices);
//    caDCB.putSafe(0);
    BrickVisurVar caDCBVV = BrickVisurVar.make(caDCB);
    BrickVisurVar cxDCBVV = BrickVisurVar.make(cxDCB);
    BrickVisurVar cyDCBVV = BrickVisurVar.make(cyDCB);

    emc.putGlobalVar("ca", caDCBVV);
    emc.putGlobalVar("cx", cxDCBVV);
    emc.putGlobalVar("cy", cyDCBVV);

  }

  private void initializeQuantums() {
    QuantumMap qm = new QuantumMap();
    String startingQuantumName = "character";
    qm.put("word", new RegexQuantum("word", "\\S+"));
    qm.put("character", new CharacterQuantum());
    qm.put("wrappedLine", new WrappedLineQuantum());
    emc.putQuantumMap(qm);
    emc.putCurrentQuantum(emc.getQuantumMap().get(startingQuantumName));
    int bounds[] = emc.getQuantumMap().get(startingQuantumName).getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), false);
    emc.putQuantumStart(bounds[0]);
    emc.putQuantumEnd(bounds[1]);
    System.out.println("start bound = " + bounds[0]);
    System.out.println("end bound = " + bounds[1]);
  }

  private void initializeHandlers() {
    final KeysToOperatorHandler[] editorKeyToOperatorHandlers = new KeysToOperatorHandler[1];
    editorKeyToOperatorHandlers[0] = KeymapHandler.make(emc);

    final KeysToOperatorHandler[] insertKeyToOperatorHandlers = new KeysToOperatorHandler[2];
    insertKeyToOperatorHandlers[0] = KeymapHandler.make(emc);
    insertKeyToOperatorHandlers[1] = InsertCharHandler.make(emc);

    ModeToHandlerArray modeToHandlerArray = ModeToHandlerArray.make();
    modeToHandlerArray.put(EditorMode.editing, editorKeyToOperatorHandlers);
    modeToHandlerArray.put(EditorMode.insert, insertKeyToOperatorHandlers);

    emc.putModeToHandlerArray(modeToHandlerArray);
  }

  private void initializeKeymaps() {

    ModeToKeymap keymapMap = ModeToKeymap.make();

    KeysToVisurCommand editingKeymap = KeysToVisurCommand.make();
    editingKeymap = initializeEditingKeymap(editingKeymap);
    keymapMap.put(EditorMode.editing, editingKeymap);

    KeysToVisurCommand insertKeymap = KeysToVisurCommand.make();
    insertKeymap = initializeInsertKeymap(insertKeymap);
    keymapMap.put(EditorMode.insert, insertKeymap);

    emc.putKeymapMap(keymapMap);

  }

  private KeysToVisurCommand initializeEditingKeymap(KeysToVisurCommand keysToVisurCommand) {
    CommandCompileService scs = ServiceHolder.commandCompileService;
    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("h")}),
      scs.compile("-1 0 relativeMove")
    );
    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("l")}),
      scs.compile("1 0 relativeMove")
    );
    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("j")}),
      scs.compile("0 1 relativeMove")
    );
    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("k")}),
      scs.compile("0 -1 relativeMove")
    );
    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("q")}),
      scs.compile("\"character\" changeQuantum")
    );
    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("w")}),
      scs.compile("\"word\" changeQuantum")
    );
    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("e")}),
      scs.compile("\"wrappedLine\" changeQuantum")
    );
//    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("j")}), Operator.cursorDown);
//    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("k")}), Operator.cursorUp);
//    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("0")}), Operator.moveCursorToBeginningOfCurrentLine);
//    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("^")}), Operator.moveCursorToFirstNonSpaceInCurrentLine);
//    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("$")}), Operator.moveCursorToEndOfCurrentLine);
//    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("i")}), Operator.enterInsertMode);
    return keysToVisurCommand;
  }

  private KeysToVisurCommand initializeInsertKeymap(KeysToVisurCommand keyToOperator) {
//    keyToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("Escape")}), Operator.enterEditingMode);
//    keyToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("Enter")}), Operator.insertNewLine);
//    keyToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("Backspace")}), Operator.deleteCurrentChar);
    return keyToOperator;
  }

}
