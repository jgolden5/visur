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
    final String initialContentLines = "Hello" +
      "\nWho's there??" +
      "\nGoodbye world.";
//    final String initialContentLines = "31 Yea, I would that ye would come forth and harden not your hearts any longer; for behold, now is the time and the day of your salvation; and therefore, if ye will repent and harden not your hearts, immediately shall the great plan of redemption be brought about unto you." +
//      "\n\t32 For behold, this life is the time for men to prepare to meet God; yea, behold the day of this life is the day for men to perform their labors." +
//      "\n\t\t33 And now, as I said unto you before, as ye have had so many witnesses, therefore, I beseech of you that ye do not procrastinate the day of your repentance until the end; for after this day of life, which is given us to prepare for eternity, behold, if we do not improve our time while in this life, then cometh the night of darkness wherein there can be no labor performed." +
//      "\n 34 Ye cannot say, when ye are brought to that awful crisis, that I will repent, that I will return to my God. Nay, ye cannot say this; for that same spirit which doth possess your bodies at the time that ye go out of this life, that same spirit will have power to possess your body in that eternal world.";
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
    keyToOperator.put(KeyPressed.from("Enter"), Operator.insertEmptyLineBelowCurrentLine);
    keyToOperator.put(KeyPressed.from("Backspace"), Operator.deleteCurrentChar);
    return keyToOperator;
  }


}
