package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

public class OperatorToService implements Shareable {

  public static OperatorToService make() {
    return new OperatorToService();
  }

  public OperatorService get(Operator operator) {
    OperatorService targetService;
    switch(operator) {
      case moveRight, cursorLeft, cursorRight, cursorDown, cursorUp,
        moveCursorToBeginningOfNextWord, moveCursorToBeginningOfCurrentLine,
        moveCursorToEndOfCurrentLine, moveCursorToFirstNonSpaceInCurrentLine:
        targetService = ServiceHolder.cursorMovementService;
        break;
      case insertChar, insertNewLine, deleteCurrentChar:
        targetService = ServiceHolder.insertCharService;
        break;
      case enterEditingMode, enterInsertMode:
        targetService = ServiceHolder.modeSwitchService;
        break;
      default:
        System.out.println("operator not recognized");
        targetService = null;
    }
    return targetService;
  }


}
