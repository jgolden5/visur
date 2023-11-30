package com.ple.visur;

public class OperatorToService {
  public OperatorToService(OperatorService cursorMovementService) {
    this.cursorMovementService = cursorMovementService;
  }

  public static OperatorToService make(CursorMovementService cursorMovementService) {
    return new OperatorToService(cursorMovementService);
  }


  private final OperatorService cursorMovementService;

  public OperatorService get(Operator operator) {
    switch(operator) {
      case moveLeft, moveRight, moveDown, moveUp,
        skipToBeginningOfNextWord, goToBeginningOfCurrentLine,
        goToEndOfCurrentLine, goToFirstNonSpaceInCurrentLine:
        return cursorMovementService;
      break;
      default:
        System.out.println("operator not recognized");
    }
  }


}
