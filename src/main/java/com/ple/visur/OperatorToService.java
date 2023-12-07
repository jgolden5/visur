package com.ple.visur;

import java.awt.*;

public class OperatorToService {

  public static OperatorToService make() {
    return new OperatorToService();
  }

  private final CursorMovementService cursorMovementService = CursorMovementService.make();

  public OperatorService get(Operator operator) {
    OperatorService targetService;
    switch(operator) {
      case cursorLeft, cursorRight, cursorDown, cursorUp,
        moveCursorToBeginningOfNextWord, moveCursorToBeginningOfCurrentLine,
        moveCursorToEndOfCurrentLine, moveCursorToFirstNonSpaceInCurrentLine:
        targetService = cursorMovementService;
        break;
      default:
        System.out.println("operator not recognized");
        targetService = null;
    }
    return targetService;
  }


}
