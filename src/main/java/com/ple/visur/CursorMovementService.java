package com.ple.visur;

import static com.ple.visur.EditorModelKey.canvasHeight;
import static com.ple.visur.EditorModelKey.contentX;

public class CursorMovementService implements OperatorService {

  final EditorModelService ems = ServiceHolder.editorModelService;

  public static CursorMovementService make() {
    return new CursorMovementService();
  }


  //map key
  public void cursorLeft() { //h
    final VisurVar contentXVisurVar = ems.getGlobalVar("contentX");
    final int contentX = contentXVisurVar.getInt();
    if (contentX > 0) {
      contentXVisurVar.put(contentX - 1);
      ems.putVirtualX(contentX);
    }
  }

  public void cursorRight() { //l
    final VisurVar contentYVisurVar = ems.getGlobalVar("contentY");
    if(ems.getContentX() < ems.getCurrentContentLineLength()) {
      ems.putContentX(ems.getContentX() + 1);
      ems.putVirtualX(ems.getContentX());
    }
  }

  public void cursorDown() { //j
    boolean isAtBottomOfLine = ems.getContentY() >= ems.getEditorContentLines().length - 1;
    boolean isAtLineLimit = ems.getContentY() >= ems.getCanvasHeight() - 2;
    if (!isAtBottomOfLine && !isAtLineLimit) {
      String nextLine = ems.getEditorContentLines()[ems.getContentY() + 1];
      int nextLineLength = nextLine.length();
      ems.putContentY(ems.getContentY() + 1);
      if (nextLineLength - 1 < ems.getVirtualX() || ems.getVirtualXIsAtEndOfLine()) {
        ems.putContentX(nextLineLength);
      } else {
        ems.putContentX(ems.getVirtualX());
      }
      assignCursorCoordinates(ems.getContentX(), ems.getContentY());
    }
  }

  public void cursorUp() { //k
    if(ems.getContentY() > 0) {
      String previousLine = ems.getEditorContentLines()[ems.getContentY() - 1];
      int previousLineLength = previousLine.length();
      ems.putContentY(ems.getContentY() - 1);
      if(previousLineLength < ems.getVirtualX() || ems.getVirtualXIsAtEndOfLine()) {
        ems.putContentX(previousLineLength);
      } else {
        ems.putContentX(ems.getVirtualX());
      }
      assignCursorCoordinates(ems.getContentX(), ems.getContentY());
    }
  }

  public void moveCursorToBeginningOfNextWord() { //w
    char currentChar = ems.getCurrentContentLine().charAt(ems.getContentX());
    int i = 0;
    boolean currentCharShouldBeWord;
    while(i < 2) {
      if(i == 0) {
        currentCharShouldBeWord = true;
      } else {
        currentCharShouldBeWord = false;
      }
      while(ems.getContentX() < ems.getCurrentContentLineLength() && isWordChar(currentChar) == currentCharShouldBeWord) {
        ems.putContentX(ems.getContentX() + 1);
        if(ems.getContentX() < ems.getCurrentContentLineLength()) {
          currentChar = ems.getCurrentContentLine().charAt(ems.getContentX());
        }
      }
      if(ems.getContentX() >= ems.getCurrentContentLineLength()) {
        if(ems.getContentY() < ems.getEditorContentLines().length - 1) {
          ems.putContentX(0);
          ems.putContentY(ems.getContentY() + 1);
        } else {
          ems.putContentX(ems.getContentX() - 1);
        }
        break;
      }
      i++;
    }
    ems.putVirtualX(ems.getContentX());
    assignCursorCoordinates(ems.getContentX(), ems.getContentY());
  }

  public void moveCursorToBeginningOfCurrentLine() { //0
    ems.putContentX(0);
    ems.putVirtualX(0);
  }

  public void moveCursorToEndOfCurrentLine() { //$
    ems.putContentX(ems.getCurrentContentLineLength());
    ems.putVirtualXIsAtEndOfLine(true);
  }

  public void moveCursorToFirstNonSpaceInCurrentLine() { //^
    int firstNonSpaceIndex = -1;
    int currentLineLength = ems.getCurrentContentLineLength();
    for(int i = 0; i < currentLineLength; i++) {
      if(ems.getCurrentContentLine().charAt(i) != ' ' && ems.getCurrentContentLine().charAt(i) != '\t') {
        firstNonSpaceIndex = i;
        break;
      }
    }
    if(firstNonSpaceIndex == -1) {
      if(currentLineLength > 0) {
        firstNonSpaceIndex = currentLineLength - 1;
      } else {
        firstNonSpaceIndex = 0;
      }
    }
    ems.putContentX(firstNonSpaceIndex);
    ems.putVirtualX(firstNonSpaceIndex);
  }

  public void assignCursorCoordinates(int contentX, int contentY) {
    ems.putContentX(contentX);
    ems.putContentY(contentY);
  }

  private boolean isWordChar(char currentChar) {
    String wordCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_";
    return wordCharacters.contains(String.valueOf(currentChar));
  }

  @Override
  public void execute(Operator operator, Object... args) {
    if(args.length > 0) {
      throw new RuntimeException("input was of length " + args.length + ". Length should have been 0.");
    }
    switch(operator) {
      case cursorLeft:
        cursorLeft();
        break;
      case cursorRight:
        cursorRight();
        break;
      case cursorUp:
        cursorUp();
        break;
      case cursorDown:
        cursorDown();
        break;
      case moveCursorToBeginningOfNextWord:
        moveCursorToBeginningOfNextWord();
        break;
      case moveCursorToBeginningOfCurrentLine:
        moveCursorToBeginningOfCurrentLine();
        break;
      case moveCursorToEndOfCurrentLine:
        moveCursorToEndOfCurrentLine();
        break;
      case moveCursorToFirstNonSpaceInCurrentLine:
        moveCursorToFirstNonSpaceInCurrentLine();
        break;
      default:
        ems.reportError("Operator not recognized in " + this.getClass().getSimpleName());
    }
  }
}
