package com.ple.visur;

import java.util.HashMap;

import static com.ple.visur.EditorMode.editing;
import static com.ple.visur.EditorModelKey.globalVariableMap;

public class InitializerService {

  private final EditorModelService ems;

  public static InitializerService make(EditorModelService editorModelService) {
    return new InitializerService(editorModelService);
  }

  private InitializerService(EditorModelService editorModelService) {
    this.ems = editorModelService;
    initializeEditorModel();
  }

  public void initializeEditorModel() {
    VariableMap initialGvm = new VariableMap(new HashMap<>());
    ems.editorModel.put(globalVariableMap, initialGvm);
    VariableMap gvm = (VariableMap)ems.editorModel.get(globalVariableMap);
    gvm.put("contentX", new IntVisurVar(12));
    gvm.put("contentY", new IntVisurVar(0));
    ems.putVirtualX(0);
    ems.putVirtualXIsAtEndOfLine(false);
    ems.putEditorMode(editing);
    ems.putKeyBuffer(KeysPressed.from(new KeyPressed[]{}));
    ems.putExecutionDataStack(new ExecutionDataStack());
    final String initialEditorContent = "Hello world\n" +
      "How are you?\n" +
      "Goodbye";
//    final String initialEditorContent = "Qazlal Stormbringer is a violent god of tempests, who delights in unleashing the forces of nature against the unsuspecting.\n" +
//      "Those who invite Qazlal's gaze will find themselves the eye in a storm of elemental destruction, from which only their god can protect them.\n" +
//      "Pious worshippers of Qazlal will gain the ability to direct and control the destructive might of the storm.\n" +
//      "Followers of Qazlal are protected from the clouds they create.\n";
    ems.putEditorContent(initialEditorContent);

    ems.putIsInCommandState(false);
    ems.putCommandStateContent("");
    ems.putCommandCursor(ems.getCommandStateContent().length());

    initializeQuantums();

    initializeKeymaps();

    initializeHandlers();

    OperatorToService opToService = OperatorToService.make();
    ems.putOperatorToService(opToService);
  }

  private void initializeQuantums() {
    QuantumMap qm = new QuantumMap();
    String startingQuantumName = "word";
    qm.put("character", new RegexQuantum("."));
    qm.put("word", new RegexQuantum("\\S+"));
    ems.putQuantumMap(qm);
    ems.putCurrentQuantum(ems.getQuantumMap().get(startingQuantumName));
    int contentX = ems.getGlobalVar("contentX").getInt();
    int contentY = ems.getGlobalVar("contentY").getInt();
    int bounds[] = ems.getQuantumMap().get(startingQuantumName).getBoundaries(ems.getEditorContent(), ems.getNewlineIndices(), contentX, contentY);
    ems.putQuantumStart(bounds[0]);
    ems.putQuantumEnd(bounds[1]);
    System.out.println("start bound = " + bounds[0]);
    System.out.println("end bound = " + bounds[1]);
  }

  private void initializeHandlers() {
    final KeysToOperatorHandler[] editorKeyToOperatorHandlers = new KeysToOperatorHandler[1];
    editorKeyToOperatorHandlers[0] = KeymapHandler.make(ems);

    final KeysToOperatorHandler[] insertKeyToOperatorHandlers = new KeysToOperatorHandler[2];
    insertKeyToOperatorHandlers[0] = KeymapHandler.make(ems);
    insertKeyToOperatorHandlers[1] = InsertCharHandler.make(ems);

    ModeToHandlerArray modeToHandlerArray = ModeToHandlerArray.make();
    modeToHandlerArray.put(EditorMode.editing, editorKeyToOperatorHandlers);
    modeToHandlerArray.put(EditorMode.insert, insertKeyToOperatorHandlers);

    ems.putModeToHandlerArray(modeToHandlerArray);
  }

  private void initializeKeymaps() {

    ModeToKeymap keymapMap = ModeToKeymap.make();

    KeysToVisurCommand editingKeymap = KeysToVisurCommand.make();
    editingKeymap = initializeEditingKeymap(editingKeymap);
    keymapMap.put(EditorMode.editing, editingKeymap);

    KeysToVisurCommand insertKeymap = KeysToVisurCommand.make();
    insertKeymap = initializeInsertKeymap(insertKeymap);
    keymapMap.put(EditorMode.insert, insertKeymap);

    ems.putKeymapMap(keymapMap);

  }

  private KeysToVisurCommand initializeEditingKeymap(KeysToVisurCommand keysToVisurCommand) {
    CommandCompileService scs = ServiceHolder.commandCompileService;
    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("h")}),
      scs.compile("-5 0 relativeMove")
    );
    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("l")}),
      scs.compile("1 0 relativeMove")
    );
//    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("j")}), Operator.cursorDown);
//    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("k")}), Operator.cursorUp);
//    keysToVisurCommand.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("w")}), Operator.moveCursorToBeginningOfNextWord);
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
