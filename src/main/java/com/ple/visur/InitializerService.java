package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.LocalMap;

import static com.ple.visur.EditorMode.editing;
import static com.ple.visur.EditorModelKey.*;
import static com.ple.visur.EditorModelKey.editorMode;

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
    ems.putEditorContentLines(new String[0]);
    ems.putContentX(0);
    ems.putContentY(0);
    ems.putVirtualX(0);
    ems.putVirtualXIsAtEndOfLine(false);
    ems.putEditorMode(editing);
    final String initialContentLines = "Hello world" +
      "\nHow are you?" +
      "\nGoodbye";
//    final String initialContentLines = "Qazlal Stormbringer is a violent god of tempests, who delights in unleashing the forces of nature against the unsuspecting." +
//      "\nThose who invite Qazlal's gaze will find themselves the eye in a storm of elemental destruction, from which only their god can protect them." +
//      "\nPious worshippers of Qazlal will gain the ability to direct and control the destructive might of the storm." +
//      "\nFollowers of Qazlal are protected from the clouds they create.";
    ems.putEditorContentLines(initialContentLines.split("\n"));

    ModeToKeymap keymapMap = ModeToKeymap.make();

    KeyToOperator editingKeymap = KeyToOperator.make();
    editingKeymap = initializeEditingKeymap(editingKeymap);
    keymapMap.put(EditorMode.editing, editingKeymap);

    KeyToOperator insertKeymap = KeyToOperator.make();
    insertKeymap = initializeInsertKeymap(insertKeymap);
    keymapMap.put(EditorMode.insert, insertKeymap);

    ems.putKeymapMap(keymapMap);

    initializeHandlers();

    OperatorToService opToService = OperatorToService.make();
    ems.putOperatorToService(opToService);
  }


  private void initializeHandlers() {
    final KeyToOperatorHandler[] editorKeyToOperatorHandlers = new KeyToOperatorHandler[1];
    editorKeyToOperatorHandlers[0] = KeymapHandler.make(ems);

    final KeyToOperatorHandler[] insertKeyToOperatorHandlers = new KeyToOperatorHandler[2];
    insertKeyToOperatorHandlers[0] = KeymapHandler.make(ems);
    insertKeyToOperatorHandlers[1] = InsertCharHandler.make(ems);

    ModeToHandlerArray modeToHandlerArray = ModeToHandlerArray.make();
    modeToHandlerArray.put(EditorMode.editing, editorKeyToOperatorHandlers);
    modeToHandlerArray.put(EditorMode.insert, insertKeyToOperatorHandlers);

    ems.putModeToHandlerArray(modeToHandlerArray);
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
    keyToOperator.put(KeyPressed.from("i"), Operator.enterInsertMode);
    return keyToOperator;
  }

  private KeyToOperator initializeInsertKeymap(KeyToOperator keyToOperator) {
    keyToOperator.put(KeyPressed.from("Escape"), Operator.enterEditingMode);
    keyToOperator.put(KeyPressed.from("Enter"), Operator.insertNewLine);
    keyToOperator.put(KeyPressed.from("Backspace"), Operator.deleteCurrentChar);
    return keyToOperator;
  }


}
