package com.ple.visur;

public class CursorMovementService implements OperatorService {

  final EditorModelService ems = ServiceHolder.editorModelService;

  public static CursorMovementService make() {
    return new CursorMovementService();
  }


  //map key
  public void cursorLeft() { //h
    if (ems.getContentX() >= ems.getCurrentContentLineLength()) {
      ems.putContentX(ems.getCurrentContentLineLength() - 1);
    }
    if (ems.getContentX() > 0) {
      ems.putContentX(ems.getContentX() - 1);
      ems.putVirtualX(ems.getContentX());
    }
  }
  public void cursorRight() { //l
    if(ems.getContentX() >= ems.getCurrentContentLineLength()) {
      ems.putContentX(ems.getCurrentContentLineLength() - 1);
    }
    if(ems.getContentX() < ems.getCurrentContentLineLength() - 1) {
      ems.putContentX(ems.getContentX() + 1);
      ems.putVirtualX(ems.getContentX());
    }
  }
  public void cursorDown() { //j
    if (ems.getContentY() < ems.getEditorContentLines().length - 1) {
      String nextLine = ems.getEditorContentLines()[ems.getContentY() + 1];
      int nextLineLength = nextLine.length();
      ems.putContentY(ems.getContentY() + 1);
      if (nextLineLength - 1 < ems.getVirtualX() || ems.getVirtualXIsAtEndOfLine()) {
        ems.putContentX(nextLineLength - 1);
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
      if(previousLineLength - 1 < ems.getVirtualX() || ems.getVirtualXIsAtEndOfLine()) {
        ems.putContentX(previousLineLength - 1);
      }
      else {
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
    ems.putContentX(ems.getCurrentContentLineLength() - 1);
    ems.putContentX(ems.getContentX());
    ems.putVirtualXIsAtEndOfLine(true);
  }

  public void moveCursorToFirstNonSpaceInCurrentLine() { //^
    int firstNonSpaceIndex = -1;
    for(int i = 0; i < ems.getCurrentContentLineLength(); i++) {
      if(ems.getCurrentContentLine().charAt(i) != ' ' && ems.getCurrentContentLine().charAt(i) != '\t') {
        firstNonSpaceIndex = i;
        break;
      }
    }
    if(firstNonSpaceIndex == -1) {
      firstNonSpaceIndex = ems.getCurrentContentLineLength() - 1;
    }
    ems.putContentX(firstNonSpaceIndex);
    ems.putVirtualX(firstNonSpaceIndex);
  }
  public void enterInsertMode() { //i

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
  public void execute(Operator operator) {
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
      case enterInsertMode:
        enterInsertMode();
        break;
      default:
        System.out.println("Operator not recognized");
    }
  }
}
