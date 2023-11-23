package com.ple.visur;

public class KeymapService {
  int contentX = editorModelService.getContentX();
  int virtualX = editorModelService.getVirtualX();
  int contentY = editorModelService.getContentY();
  final int canvasWidth = editorModelService.getCanvasWidth();
  final int canvasHeight = editorModelService.getCanvasHeight();
  int canvasX = editorModelService.getCanvasX();
  int canvasY = editorModelService.getCanvasY();
  String[] contentLines = editorModelService.getContentLines();
  String currentContentLine = contentLines[contentY];
  int currentContentLineLength = currentContentLine.length();
  int numberOfRowsInCurrentContentLine = currentContentLineLength / canvasWidth;
  boolean virtualXIsAtEndOfLine = editorModelService.getVirtualXIsAtEndOfLine();
  if(currentContentLineLength % canvasWidth != 0) {
    numberOfRowsInCurrentContentLine++;
  }
  String keysThatMakeAtEndOfLineFalse = "hl0^";
    if(virtualXIsAtEndOfLine && keysThatMakeAtEndOfLineFalse.contains(key)) {
    editorModelService.putVirtualXIsAtEndOfLine(false);
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
    if (contentY < contentLines.length - 1) {
      String nextLine = contentLines[contentY + 1];
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
      String previousLine = contentLines[contentY - 1];
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
        if(contentY < contentLines.length - 1) {
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


}
