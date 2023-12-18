package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

public class OperatorToService implements Shareable {

  public static OperatorToService make() {
    return new OperatorToService();
  }

  public OperatorService get(Operator operator) {
    OperatorService targetService;
    switch(operator) {
      case cursorLeft, cursorRight, cursorDown, cursorUp,
        moveCursorToBeginningOfNextWord, moveCursorToBeginningOfCurrentLine,
        moveCursorToEndOfCurrentLine, moveCursorToFirstNonSpaceInCurrentLine, enterInsertMode:
        targetService = ServiceHolder.cursorMovementService;
        break;
      case insertChar:
        targetService = ServiceHolder.insertCharService;
        break;
      default:
        System.out.println("operator not recognized");
        targetService = null;
    }
    return targetService;
  }


}
