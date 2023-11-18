package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.SharedData;

import static com.ple.visur.ModelIntKey.*;
import static com.ple.visur.ModelStringArrayKey.*;
import static com.ple.visur.ModelBooleanKey.*;

public class EditorModelService {

  private final SharedData sharedData;

  public String[] getContentLines() {
    return (String[])sharedData.getLocalMap("modelStringArray").get(contentLines.name());
  }

  public int getContentX() {
    return (int)sharedData.getLocalMap("modelInt").get(contentLineX.name());
  }

  public int getContentY() {
    return (int)sharedData.getLocalMap("modelInt").get(contentLineY.name());
  }

  public int getVirtualX() {
    return (int)sharedData.getLocalMap("modelInt").get(virtualX.name());
  }

  public boolean getVirtualXIsAtEndOfLine() {
    return (boolean)sharedData.getLocalMap("modelBoolean").get(virtualXIsAtEndOfLine.name());
  }

  public int getCanvasWidth() {
    return (int)sharedData.getLocalMap("modelInt").get(canvasWidth.name());
  }

  public int getCanvasHeight() {
    return (int)sharedData.getLocalMap("modelInt").get(canvasHeight.name());
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


  public void putContentLines(String[] editorContentLines) {
    sharedData.getLocalMap("modelStringArray").put(contentLines.name(), editorContentLines);
  }

  public void putContentX(int x) {
    sharedData.getLocalMap("modelInt").put(contentLineX.name(), x);
  }

  public void putContentY(int y) {
    sharedData.getLocalMap("modelInt").put(contentLineY.name(), y);
  }

  public void putVirtualX(int x) {
    sharedData.getLocalMap("modelInt").put(virtualX.name(), x);
  }

  public void putVirtualXIsAtEndOfLine(boolean isAtEndOfLine) {
    sharedData.getLocalMap("modelBoolean").put(virtualXIsAtEndOfLine.name(), isAtEndOfLine);
  }

  public void putCanvasWidth(int width) {
    sharedData.getLocalMap("modelInt").put(canvasWidth.name(), width);
  }

  public void putCanvasHeight(int height) {
    sharedData.getLocalMap("modelInt").put(canvasHeight.name(), height);
  }


  EditorModelService(SharedData sharedData) {
    this.sharedData = sharedData;
  }

}
