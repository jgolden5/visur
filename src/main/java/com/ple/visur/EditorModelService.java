package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.SharedData;

import javax.swing.*;

import com.ple.visur.DataModelService;

import static com.ple.visur.ModelIntKey.*;

public class EditorModelService {

  private final SharedData sharedData;


  public int getContentLineX() {
    return (int)sharedData.getLocalMap("modelInt").get(contentLineX.name());
  }

  public int getContentLineY() {
    return (int)sharedData.getLocalMap("modelInt").get(contentLineY.name());
  }

  public int getCanvasX() {
    int contentX = getContentLineX();
    int canvasWidth = getCanvasWidth();
    return contentX % canvasWidth;
  }

  public int getCanvasY() {
    DataModelService dataModelService = new DataModelService(sharedData);
    String[] contentLines = dataModelService.getContentLines();
    int contentX = getContentLineX();
    int contentY = getContentLineY();
    int numberOfRowsBeforeCurrent = 0;
    int canvasWidth = getCanvasWidth();
    int canvasY = 0;
    for(int i = 0; i < contentY; i++) {
      String currentLine = contentLines[i];
      numberOfRowsBeforeCurrent += currentLine.length() - 1 / canvasWidth;
      if(currentLine.length() - 1 % canvasWidth != 0) {
        numberOfRowsBeforeCurrent++;
      }
    }
    numberOfRowsBeforeCurrent += contentX / canvasWidth;
    canvasY = numberOfRowsBeforeCurrent;
    return canvasY;
  }

  public int getCanvasWidth() {
    return (int)sharedData.getLocalMap("modelInt").get(canvasWidth.name());
  }

  public int getCanvasHeight() {
    return (int)sharedData.getLocalMap("modelInt").get(canvasHeight.name());
  }

  public void putContentLineX(int x) {
    sharedData.getLocalMap("modelInt").put(contentLineX.name(), x);
  }

  public void putContentLineY(int y) {
    sharedData.getLocalMap("modelInt").put(contentLineY.name(), y);
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
