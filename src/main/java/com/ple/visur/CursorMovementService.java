package com.ple.visur;

public class CursorMovementService {
  protected final EditorModelService editorModelService;

  int contentX;
  int virtualX;
  int contentY;
  int canvasWidth;
  int canvasHeight;
  int canvasX;
  int canvasY;
  String[] editorContentLines;
  String currentContentLine;

  int currentContentLineLength;

  int numberOfRowsInCurrentContentLine;
  boolean virtualXIsAtEndOfLine;
  String keysThatMakeAtEndOfLineFalse;

  private CursorMovementService(EditorModelService editorModelService) {
    this.editorModelService = editorModelService;
    contentX = editorModelService.getContentX();
    virtualX = editorModelService.getVirtualX();
    contentY = editorModelService.getContentY();
    canvasWidth = editorModelService.getCanvasWidth();
    canvasHeight = editorModelService.getCanvasHeight();
    canvasX = editorModelService.getCanvasX();
    canvasY = editorModelService.getCanvasY();
    editorContentLines = editorModelService.getEditorContentLines();
    currentContentLine = editorContentLines[contentY];
    currentContentLineLength = currentContentLine.length();
    numberOfRowsInCurrentContentLine = currentContentLineLength / canvasWidth;
    virtualXIsAtEndOfLine = editorModelService.getVirtualXIsAtEndOfLine();
    if(currentContentLineLength % canvasWidth != 0) {
      numberOfRowsInCurrentContentLine++;
    }
    keysThatMakeAtEndOfLineFalse = "hl0^";
  }

  public static CursorMovementService make(EditorModelService editorModelService) {
    return new CursorMovementService(editorModelService);
  }


  //map key
  public void moveLeft() { //h
    if (contentX >= currentContentLineLength) {
      contentX = currentContentLineLength - 1;
    }
    if (contentX > 0) {
      contentX--;
      editorModelService.putContentX(contentX);
      editorModelService.putVirtualX(contentX);
    }
  }
  public void moveRight() { //l
    if(contentX >= currentContentLineLength) {
      contentX = currentContentLineLength - 1;
    }
    if(contentX < currentContentLineLength - 1) {
      contentX++;
      editorModelService.putContentX(contentX);
      editorModelService.putVirtualX(contentX);
    }
  }
  public void moveDown() { //j
    if (contentY < editorContentLines.length - 1) {
      String nextLine = editorContentLines[contentY + 1];
      int nextLineLength = nextLine.length();
      contentY++;
      if (nextLineLength - 1 < virtualX || virtualXIsAtEndOfLine) {
        contentX = nextLineLength - 1;
      } else {
        contentX = virtualX;
      }
      assignCursorCoordinates(contentX, contentY);
    }
  }
  public void moveUp() { //k
    if(contentY > 0) {
      String previousLine = editorContentLines[contentY - 1];
      int previousLineLength = previousLine.length();
      contentY--;
      if(previousLineLength - 1 < virtualX || virtualXIsAtEndOfLine) {
        contentX = previousLineLength - 1;
      }
      else {
        contentX = virtualX;
      }
      assignCursorCoordinates(contentX, contentY);
    }
  }
  public void skipToBeginningOfNextWord() { //w
    char currentChar = currentContentLine.charAt(contentX);
    int i = 0;
    boolean currentCharShouldBeWord;
    while(i < 2) {
      if(i == 0) {
        currentCharShouldBeWord = true;
      } else {
        currentCharShouldBeWord = false;
      }
      while(contentX < currentContentLineLength && isWordChar(currentChar) == currentCharShouldBeWord) {
        contentX++;
        if(contentX < currentContentLineLength) {
          currentChar = currentContentLine.charAt(contentX);
        }
      }
      if(contentX >= currentContentLineLength) {
        if(contentY < editorContentLines.length - 1) {
          contentX = 0;
          contentY++;
        } else {
          contentX--;
        }
        break;
      }
      i++;
    }
    assignCursorCoordinates(contentX, contentY);
  }
  public void goToBeginningOfCurrentLine() { //0
    editorModelService.putContentX(0);
    editorModelService.putVirtualX(0);
  }
  public void goToEndOfCurrentLine() { //$
    contentX = currentContentLineLength - 1;
    editorModelService.putContentX(contentX);
    editorModelService.putVirtualXIsAtEndOfLine(true);
  }
  public void goToFirstNonSpaceInCurrentLine() { //^
    int firstNonSpaceIndex = -1;
    for(int i = 0; i < currentContentLineLength; i++) {
      if(currentContentLine.charAt(i) != ' ' && currentContentLine.charAt(i) != '\t') {
        firstNonSpaceIndex = i;
        break;
      }
    }
    if(firstNonSpaceIndex == -1) {
      firstNonSpaceIndex = currentContentLineLength - 1;
    }
    editorModelService.putContentX(firstNonSpaceIndex);
    editorModelService.putVirtualX(firstNonSpaceIndex);
  }
  public void enterInsertMode() { //i

  }

  public void assignCursorCoordinates(int contentX, int contentY) {
    editorModelService.putContentX(contentX);
    editorModelService.putContentY(contentY);
  }

  private boolean isWordChar(char currentChar) {
    String wordCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_";
    return wordCharacters.contains(String.valueOf(currentChar));
  }

}
