package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.SharedData;

public class EditorModelService {
  private final SharedData sharedData;

  private EditorModelService(SharedData sharedData) {
    this.sharedData = sharedData;
  }

  public static EditorModelService make(SharedData sharedData) {
    return new EditorModelService(sharedData);
  }


  public String[] getContentLines() {

  }

  public int getContentX() {

  }

  public int getContentY() {

  }

  public int getVirtualX() {

  }

  public boolean getVirtualXIsAtEndOfLine() {

  }

  public int getCanvasWidth() {

  }

  public int getCanvasHeight() {

  }

  //only getters, no setters
  public int getCanvasX() {
    int contentX = getContentX();
    int contentY = getContentY();
    int adjustedContentX = contentX;
    String[] contentLines = getContentLines();
    if(contentX > contentLines[contentY].length() - 1) {
      adjustedContentX = contentLines[contentY].length() - 1;
    }
    int canvasWidth = getCanvasWidth();
    return adjustedContentX % canvasWidth;
  }

  public int getCanvasY() {
    String[] contentLines = getContentLines();
    int canvasWidth = getCanvasWidth();
    int contentX = getContentX();
    int contentY = getContentY();
    int rows = 0;
    for(int i = 0; i < contentY; i++) {
      String currentLine = contentLines[i];
      rows += currentLine.length() / canvasWidth;
      if(currentLine.length() % canvasWidth != 0) {
        rows++;
      }
    }
    rows += contentX / canvasWidth;
    return rows;
  }

  public KeymapMap getKeymapMap() {

  }

  public Operator getOperator(EditorMode editorMode) {
    editorMode = getEditorMode();
    Keymap keymap = getKeymapMap().get();
  }

  public void putContentLines(String[] editorContentLines) {

  }

  public void putContentX(int x) {

  }

  public void putContentY(int y) {

  }

  public void putVirtualX(int x) {

  }

  public void putVirtualXIsAtEndOfLine(boolean isAtEndOfLine) {

  }

  public void putCanvasWidth(int width) {

  }

  public void putCanvasHeight(int height) {

  }

}
