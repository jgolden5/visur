package com.ple.visur;

import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.shareddata.SharedData;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.Vertx;

import static com.ple.visur.ModelIntKey.*;
import static com.ple.visur.ModelStringKey.*;
import static com.ple.visur.ModelStringArrayKey.*;

public class EditorModelService {

  private final SharedData sharedData;

  public int getCursorX() {
    return (int)sharedData.getLocalMap("modelInt").get(cursorX.name());
  }

  public int getCursorY() {
    return (int)sharedData.getLocalMap("modelInt").get(cursorY.name());
  }

  public String[] getContentLines() {
    return (String[])sharedData.getLocalMap("modelStringArray").get(contentLines.name());
  }

  public void setCursorX(int x) {
    sharedData.getLocalMap("modelInt").put(cursorX.name(), x);
  }

  public void setCursorY(int y) {
    sharedData.getLocalMap("modelInt").put(cursorY.name(), y);
  }

  public void setContentLines(String[] contentLinesInput) {
    sharedData.getLocalMap("modelStringArray").put(contentLines.name(), contentLinesInput);
  }

  EditorModelService(SharedData sharedData) {
    this.sharedData = sharedData;
  }

}
