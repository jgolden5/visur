package com.ple.visur;

import static com.ple.visur.EditorModelKey.canvasHeight;
import static com.ple.visur.EditorModelKey.contentX;

public class CursorMovementService implements OperatorService {

  final EditorModelService ems = ServiceHolder.editorModelService;
  final VisurVar contentXVisurVar = ems.getGlobalVar("contentX");
  final VisurVar contentYVisurVar = ems.getGlobalVar("contentY");

  public static CursorMovementService make() {
    return new CursorMovementService();
  }


  //map key
  public void cursorLeft() { //h
    final int contentX = contentXVisurVar.getInt();
    if (contentX > 0) {
      contentXVisurVar.put(contentX - 1);
      ems.putVirtualX(contentX);
    }
  }

  public void cursorRight() { //l
    final int contentX = contentXVisurVar.getInt();
    if(contentX < ems.getCurrentContentLineLength()) {
      contentXVisurVar.put(contentX + 1);
      ems.putVirtualX(contentX);
    }
  }

  public void cursorDown() { //j
    final int contentY = contentYVisurVar.getInt();
    boolean isAtBottomOfLine = contentY >= ems.getEditorContentLines().length - 1;
    boolean isAtLineLimit = contentY >= ems.getCanvasHeight() - 2;
    if (!isAtBottomOfLine && !isAtLineLimit) {
      String nextLine = ems.getEditorContentLines()[contentY + 1];
      int nextLineLength = nextLine.length();
      contentYVisurVar.put(contentY + 1);
      if (nextLineLength - 1 < ems.getVirtualX() || ems.getVirtualXIsAtEndOfLine()) {
        contentXVisurVar.put(nextLineLength);
      } else {
        contentXVisurVar.put(ems.getVirtualX());
      }
    }
  }

  public void cursorUp() { //k
    final int contentX = contentXVisurVar.getInt();
    final int contentY = contentYVisurVar.getInt();
    if(contentY > 0) {
      String previousLine = ems.getEditorContentLines()[contentY - 1];
      int previousLineLength = previousLine.length();
      contentYVisurVar.put(contentY - 1);
      if(previousLineLength < ems.getVirtualX() || ems.getVirtualXIsAtEndOfLine()) {
        contentXVisurVar.put(previousLineLength);
      } else {
        contentXVisurVar.put(ems.getVirtualX());
      }
    }
  }

  public void moveCursorToBeginningOfNextWord() { //w
    final int contentX = contentXVisurVar.getInt();
    final int contentY = contentYVisurVar.getInt();
    char currentChar = ems.getCurrentContentLine().charAt(contentX);
    int i = 0;
    boolean currentCharShouldBeWord;
    while(i < 2) {
      if(i == 0) {
        currentCharShouldBeWord = true;
      } else {
        currentCharShouldBeWord = false;
      }
      while(contentX < ems.getCurrentContentLineLength() && isWordChar(currentChar) == currentCharShouldBeWord) {
        contentXVisurVar.put(contentX + 1);
        if(contentX < ems.getCurrentContentLineLength()) {
          currentChar = ems.getCurrentContentLine().charAt(contentX);
        }
      }
      if(contentX >= ems.getCurrentContentLineLength()) {
        if(contentY < ems.getEditorContentLines().length - 1) {
          contentXVisurVar.put(0);
          contentYVisurVar.put(contentY + 1);
        } else {
          contentXVisurVar.put(contentX - 1);
        }
        break;
      }
      i++;
    }
    ems.putVirtualX(contentX);
  }

  public void moveCursorToBeginningOfCurrentLine() { //0
    contentXVisurVar.put(0);
    ems.putVirtualX(0);
  }

  public void moveCursorToEndOfCurrentLine() { //$
    contentXVisurVar.put(ems.getCurrentContentLineLength());
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
    contentXVisurVar.put(firstNonSpaceIndex);
    ems.putVirtualX(firstNonSpaceIndex);
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
