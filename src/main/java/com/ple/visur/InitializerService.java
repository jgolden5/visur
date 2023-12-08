package com.ple.visur;

public class InitializerService {

  public static InitializerService make(EditorModelService editorModelService) {
    return new InitializerService(editorModelService);
  }

  private InitializerService(EditorModelService editorModelService) {
    KeyToOperator editingKeymap = KeyToOperator.make();
    editingKeymap = initializeEditingKeymap(editingKeymap);
    editorModelService.putEditingKeymap(editingKeymap);

    KeyToOperator insertKeymap = KeyToOperator.make();
    insertKeymap = initializeInsertKeymap(insertKeymap);
    editorModelService.putInsertKeymap(insertKeymap);

    ModeToKeymap keymapMap = ModeToKeymap.make();
    keymapMap = initializeModeToKeymap(keymapMap, editingKeymap, insertKeymap);
    editorModelService.putKeymapMap(keymapMap);

    OperatorToService opToService = OperatorToService.make();
    editorModelService.putOperatorToService(opToService);
  }

  private KeyToOperator initializeEditingKeymap(KeyToOperator keyToOperator) {
    keyToOperator.put(KeyPressed.from("h"), Operator.cursorLeft);
    keyToOperator.put(KeyPressed.from("l"), Operator.cursorRight);
    keyToOperator.put(KeyPressed.from("j"), Operator.cursorDown);
    keyToOperator.put(KeyPressed.from("k"), Operator.cursorUp);
    keyToOperator.put(KeyPressed.from("w"), Operator.moveCursorToBeginningOfNextWord);
    keyToOperator.put(KeyPressed.from("0"), Operator.moveCursorToBeginningOfCurrentLine);
    keyToOperator.put(KeyPressed.from("^"), Operator.moveCursorToFirstNonSpaceInCurrentLine);
    keyToOperator.put(KeyPressed.from("$"), Operator.moveCursorToEndOfCurrentLine);
    return keyToOperator;
  }

  private KeyToOperator initializeInsertKeymap(KeyToOperator keyToOperator) {
    keyToOperator.put(KeyPressed.from("validKey"), Operator.insertChar);
    keyToOperator.put(KeyPressed.from("invalidKey"), Operator.doNothing);
    return keyToOperator;
  }


  private ModeToKeymap initializeModeToKeymap(ModeToKeymap modeToKeymap,
                                             KeyToOperator editingKeymap, KeyToOperator insertKeymap) {
    modeToKeymap.put(EditorMode.editing, editingKeymap);
    modeToKeymap.put(EditorMode.insert, insertKeymap);
    return modeToKeymap;
  }

}
