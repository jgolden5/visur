package com.ple.visur;

public class OperatorService {

  final CursorMovementService cursorMovementService;

  public static OperatorService make(EditorModelService editorModelService) {
    return new OperatorService(editorModelService);
  }

  private OperatorService(CursorMovementService cursorMovementService) {
    this.cursorMovementService = cursorMovementService;
  }

  //input: mode, keymapMap, key
  //output: Operator
  //probably should be called in KeyWasPressedVerticle once a key is pressed
  public Operator getOperator(EditorMode mode, KeymapMap keymapMap, KeyPressed keyPressed) {
    Keymap keymap = keymapMap.get(mode);
    Operator operator = keymap.get(keyPressed);
    return operator;
  }

  public void executeOperator(Operator operator) {
    switch(operator) {
      case moveLeft:
        cursorMovementService.moveLeft();
        break;
      case moveRight:
        cursorMovementService.moveRight();
        break;
      case moveDown:
        cursorMovementService.moveDown();
        break;
      case moveUp:
        cursorMovementService.moveUp();
        break;
      case skipToBeginningOfNextWord:
        cursorMovementService.skipToBeginningOfNextWord();
        break;
      case goToBeginningOfCurrentLine:
        cursorMovementService.goToBeginningOfCurrentLine();
        break;
      case goToEndOfCurrentLine:
        cursorMovementService.goToEndOfCurrentLine();
        break;
      case goToFirstNonSpaceInCurrentLine:
        cursorMovementService.goToFirstNonSpaceInCurrentLine();
        break;
      case enterInsertMode:
        cursorMovementService.enterInsertMode();
        break;
      default:
        System.out.println("operator not recognized");
    }
  }

}
