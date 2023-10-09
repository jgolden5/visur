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

  public void putCursorX(int x) {
    sharedData.getLocalMap("modelInt").put(cursorX.name(), x);
  }

  public void putCursorY(int y) {
    sharedData.getLocalMap("modelInt").put(cursorY.name(), y);
  }

  EditorModelService(SharedData sharedData) {
    this.sharedData = sharedData;
  }

}
