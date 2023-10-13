package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.SharedData;

import static com.ple.visur.ModelIntKey.*;
import static com.ple.visur.ModelStringArrayKey.*;

public class EditorModelService {

  private final SharedData sharedData;

  public int getCursorX() {
    return (int)sharedData.getLocalMap("modelInt").get(cursorX.name());
  }

  public int getCursorY() {
    return (int)sharedData.getLocalMap("modelInt").get(cursorY.name());
  }

  public int getCanvasWidth() {
    return (int)sharedData.getLocalMap("modelInt").get(canvasWidth.name());
  }

  public int getCanvasHeight() {
    return (int)sharedData.getLocalMap("modelInt").get(canvasHeight.name());
  }

  public int getCurrentLineNumber() {
    return (int)sharedData.getLocalMap("modelInt").get(currentLineNumber.name());
  }

  public void putCursorX(int x) {
    sharedData.getLocalMap("modelInt").put(cursorX.name(), x);
  }

  public void putCursorY(int y) {
    sharedData.getLocalMap("modelInt").put(cursorY.name(), y);
  }

  public void putCanvasWidth(int width) {
    sharedData.getLocalMap("modelInt").put(canvasWidth.name(), width);
  }

  public void putCanvasHeight(int height) {
    sharedData.getLocalMap("modelInt").put(canvasHeight.name(), height);
  }

  public void putCurrentLineNumber(int lineNumber) {
    sharedData.getLocalMap("modelInt").put(currentLineNumber.name(), lineNumber);
  }


  EditorModelService(SharedData sharedData) {
    this.sharedData = sharedData;
  }

}
