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
    return (int)sharedData.getLocalMap("canvasWidth").get(canvasWidth.name());
  }

  public int getCanvasHeight() {
    return (int)sharedData.getLocalMap("canvasHeight").get(canvasHeight.name());
  }


  public void putCursorX(int x) {
    sharedData.getLocalMap("modelInt").put(cursorX.name(), x);
  }

  public void putCursorY(int y) {
    sharedData.getLocalMap("modelInt").put(cursorY.name(), y);
  }

  public void putCanvasWidth(int width) {
    sharedData.getLocalMap("canvasWidth").put(canvasWidth.name(), width);
  }

  public void putCanvasHeight(int height) {
    sharedData.getLocalMap("canvasHeight").put(canvasHeight.name(), height);
  }

  EditorModelService(SharedData sharedData) {
    this.sharedData = sharedData;
  }

}
