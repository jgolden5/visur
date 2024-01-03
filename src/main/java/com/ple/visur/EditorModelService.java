package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import static com.ple.visur.EditorModelKey.*;

public class EditorModelService {

  LocalMap<EditorModelKey, Object> editorModel;

  private EditorModelService(SharedData sharedData) {
    this.editorModel = sharedData.getLocalMap("editorModel");
  }

  public static EditorModelService make(SharedData sharedData) {
    return new EditorModelService(sharedData);
  }

  //get contentLines variable using
  public String[] getEditorContentLines() {
    //typecasting is necessary because of the generic Object type of the value
    return (String[])editorModel.get(editorContentLines);
  }

  public int getCurrentContentLineLength() {
    final String[] editorContentLines = getEditorContentLines();
    final String currentContentLine = editorContentLines[getContentY()];
    return currentContentLine.length();
  }

  public String getCurrentContentLine() {
    String[] contentLines = getEditorContentLines();
    int currentContentLineIndex = getContentY();
    return contentLines[currentContentLineIndex];
  }

  public int getContentX() {
    return (int)editorModel.get(contentX);
  }

  public int getContentY() {
    return (int)editorModel.get(contentY);
  }

  public int getVirtualX() {
    return (int)editorModel.get(virtualX);
  }

  public boolean getVirtualXIsAtEndOfLine() {
    return (boolean)editorModel.get(virtualXIsAtEndOfLine);
  }

  public int getCanvasWidth() {
    return (int)editorModel.get(canvasWidth);
  }

  public int getCanvasHeight() {
    return (int)editorModel.get(canvasHeight);
  }

  public EditorMode getEditorMode() {
    return (EditorMode)editorModel.get(editorMode);
  }

  public ModeToKeymap getKeymapMap() {
    return (ModeToKeymap)editorModel.get(modeToKeymap);
  }



  public KeyToOperatorHandler[] getKeyToOperatorHandlers(EditorMode mode) {
    return (KeyToOperatorHandler[])editorModel.get(mode);
  }

  public ModeToHandlerArray getModeToHandlerArray() {
    return (ModeToHandlerArray)editorModel.get(modeToHandlerArray);
  }

  public boolean getIsInCommandState() {
    return (boolean)editorModel.get(isInCommandState);
  }

// add/improve getters and setters for:
// Operator, KeyPressed, (not sure: KeyToOperator, OperatorService)

  //only getters, no setters
  public int getCanvasX() {
    int contentX = getContentX();
    int canvasX;
    int currentLineLength = getCurrentContentLineLength();
    int canvasWidth = getCanvasWidth();
    if(contentX == currentLineLength && contentX % canvasWidth == 0 && currentLineLength > 0) {
      canvasX = canvasWidth;
    } else {
      canvasX = contentX % canvasWidth;
    }
    return canvasX;
  }

  public int getCanvasY() {
    int canvasY = 0;
    canvasY += calculateCanvasYBeforeCurrentLine();
    canvasY += calculateCanvasYAtCurrentLine();
    return canvasY;
  }

  private int calculateCanvasYBeforeCurrentLine() {
    int canvasY = 0;
    for(int i = 0; i < getContentY(); i++) {
      String currentIteratedLine = getEditorContentLines()[i];
      canvasY += currentIteratedLine.length() / getCanvasWidth();
      if(currentIteratedLine.length() % getCanvasWidth() != 0 || currentIteratedLine.length() == 0) {
        canvasY++;
      }
    }
    return canvasY;
  }

  private int calculateCanvasYAtCurrentLine() {
    int contentX = getContentX();
    int canvasWidth = getCanvasWidth();
    int canvasY = 0;
    if(contentX != getCurrentContentLineLength() || contentX % canvasWidth != 0) {
      canvasY += contentX / canvasWidth;
    } else if(contentX > canvasWidth) {
      canvasY += contentX / canvasWidth - 1;
    }
    return canvasY;
  }

  public void putEditorContentLines(String[] contentLines) {
    editorModel.put(editorContentLines, contentLines);
  }

  public void putContentX(int x) {
    editorModel.put(contentX, x);
  }

  public void putContentY(int y) {
    editorModel.put(contentY, y);
  }

  public void putVirtualX(int x) {
    editorModel.put(virtualX, x);
    putVirtualXIsAtEndOfLine(false);
  }

  public void putVirtualXIsAtEndOfLine(boolean isAtEndOfLine) {
    editorModel.put(virtualXIsAtEndOfLine, isAtEndOfLine);
  }

  public void putCanvasWidth(int width) {
    editorModel.put(canvasWidth, width);
  }

  public void putCanvasHeight(int height) {
    editorModel.put(canvasHeight, height);
  }

  public void putEditorMode(EditorMode mode) {
    editorModel.put(editorMode, mode);
  }

  public void putKeyPressed(KeyPressed key) {
    editorModel.put(keyPressed, key.getKey());
  }

  public void putKeymapMap(ModeToKeymap keymapMap) {
    editorModel.put(modeToKeymap, keymapMap);
  }

  public void putOperatorToService(OperatorToService opToService) {
    editorModel.put(operatorToService, opToService);
  }

  public void putModeToHandlerArray(ModeToHandlerArray modeToHandlerArrayMap) {
    editorModel.put(modeToHandlerArray, modeToHandlerArrayMap);
  }

  public void putIsInCommandState(boolean inCommandState) {
    editorModel.put(isInCommandState, inCommandState);
  }

  public void reportError(String message) {
    System.out.println(message);
  }

}
