package com.ple.visur;

import CursorPositionDC.CursorPositionDC;
import CursorPositionDC.CursorPositionDCHolder;
import DataClass.CompoundDataClassBrick;
import DataClass.PrimitiveDataClassBrick;

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
      "Goodbye \n";
//    final String initialEditorContent = "Vehumet is a god of the destructive powers of magic.\n" +
//      "Followers will gain divine assistance in commanding the hermetic arts, and the most favoured stand to gain access to some of the fearsome spells in Vehumet's library.\n" +
//      "One's devotion to Vehumet can be proven by the causing of as much carnage and destruction as possible.\n" +
//      "Worshippers of Vehumet will quickly be able to recover their magical energy upon killing beings.\n" +
//      "As they gain favour, they will also gain enhancements to their destructive spells — first assistance in casting such spells and then increased range for conjurations.\n" +
//      "Vehumet will offer followers the knowledge of increasingly powerful destructive spells as they gain piety.";

    initializeUnsetCoordinateBricks();

    emc.initializeEditorContent(initialEditorContent);
    emc.putNewlineIndices();
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(0);
    emc.putGlobalVar("ca", caBVV);
    emc.putIsInCommandState(false);
    emc.putCommandStateContent("");
    emc.putCommandCursor(emc.getCommandStateContent().length());

    initializeQuantums();

    initializeKeymaps();

    initializeHandlers();

    OperatorToService opToService = OperatorToService.make();
    emc.putOperatorToService(opToService);

  }

  private void initializeUnsetCoordinateBricks() {
    CursorPositionDC cursorPositionDC = CursorPositionDCHolder.make().cursorPositionDC;
    CompoundDataClassBrick cursorPosDCB = cursorPositionDC.makeBrick();
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cursorPosDCB.getInner("ni");
    CompoundDataClassBrick cxcycaDCB = (CompoundDataClassBrick) cursorPosDCB.getInner("cxcyca");
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    cxcycaDCB.putInner("ca", caDCB);
    cxcycaDCB.putInner("cxcy", cxcyDCB);
    cursorPosDCB.putInner("ni", niDCB);
    cursorPosDCB.putInner("cxcyca", cxcycaDCB);

    BrickVisurVar caDCBVV = BrickVisurVar.make(caDCB);
    BrickVisurVar cxDCBVV = BrickVisurVar.make(cxDCB);
    BrickVisurVar cyDCBVV = BrickVisurVar.make(cyDCB);
    BrickVisurVar niBVV = BrickVisurVar.make(niDCB);

    emc.putGlobalVar("ca", caDCBVV);
    emc.putGlobalVar("cx", cxDCBVV);
    emc.putGlobalVar("cy", cyDCBVV);
    emc.putGlobalVar("ni", niBVV);

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
