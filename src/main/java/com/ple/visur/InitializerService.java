package com.ple.visur;

import static com.ple.visur.EditorMode.editing;

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
    ems.putContentX(0);
    ems.putContentY(0);
    ems.putVirtualX(0);
    ems.putVirtualXIsAtEndOfLine(false);
    ems.putEditorMode(editing);
    ems.putKeyBuffer(KeysPressed.from(new KeyPressed[]{}));
    ems.putIsInCommandState(false);
    final String initialContentLines = "Hello world" +
      "\nHow are you?" +
      "\nGoodbye";
//    final String initialContentLines = "Qazlal Stormbringer is a violent god of tempests, who delights in unleashing the forces of nature against the unsuspecting." +
//      "\nThose who invite Qazlal's gaze will find themselves the eye in a storm of elemental destruction, from which only their god can protect them." +
//      "\nPious worshippers of Qazlal will gain the ability to direct and control the destructive might of the storm." +
//      "\nFollowers of Qazlal are protected from the clouds they create.";
    ems.putEditorContentLines(initialContentLines.split("\n"));

    initializeKeymaps();

    initializeHandlers();

    OperatorToService opToService = OperatorToService.make();
    ems.putOperatorToService(opToService);
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

    KeysToOperator editingKeymap = KeysToOperator.make();
    editingKeymap = initializeEditingKeymap(editingKeymap);
    keymapMap.put(EditorMode.editing, editingKeymap);

    KeysToOperator insertKeymap = KeysToOperator.make();
    insertKeymap = initializeInsertKeymap(insertKeymap);
    keymapMap.put(EditorMode.insert, insertKeymap);

    ems.putKeymapMap(keymapMap);

  }

  private KeysToOperator initializeEditingKeymap(KeysToOperator keysToOperator) {
    keysToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("h")}), Operator.cursorLeft);
    keysToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("l")}), Operator.cursorRight);
    keysToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("j")}), Operator.cursorDown);
    keysToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("k")}), Operator.cursorUp);
    keysToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("w")}), Operator.moveCursorToBeginningOfNextWord);
    keysToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("0")}), Operator.moveCursorToBeginningOfCurrentLine);
    keysToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("^")}), Operator.moveCursorToFirstNonSpaceInCurrentLine);
    keysToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("$")}), Operator.moveCursorToEndOfCurrentLine);
    keysToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("i")}), Operator.enterInsertMode);
    return keysToOperator;
  }

  private KeysToOperator initializeInsertKeymap(KeysToOperator keyToOperator) {
    keyToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("Escape")}), Operator.enterEditingMode);
    keyToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("Enter")}), Operator.insertNewLine);
    keyToOperator.put(KeysPressed.from(new KeyPressed[]{KeyPressed.from("Backspace")}), Operator.deleteCurrentChar);
    return keyToOperator;
  }


}
