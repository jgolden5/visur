package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.LocalMap;

import static com.ple.visur.EditorMode.*;
import static com.ple.visur.EditorModelKey.*;

public class EditorModelService {
  final LocalMap<EditorModelKey, Object> editorModel;

  private EditorModelService(LocalMap<EditorModelKey, Object> editorModel) {
    this.editorModel = editorModel;
  }

  public static EditorModelService make(LocalMap<EditorModelKey, Object> editorModel) {
    if(editorModel.isEmpty()) {
      editorModel = setInitialValues(editorModel);
    }
    return new EditorModelService(editorModel);
  }

  private static LocalMap<EditorModelKey, Object> setInitialValues(LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(editorContentLines, "test words");
    editorModel.put(contentX, 0);
    editorModel.put(contentY, 0);
    editorModel.put(virtualX, 0);
    editorModel.put(virtualXIsAtEndOfLine, false);
    editorModel.put(EditorModelKey.modeToKeymap, ModeToKeymap.make());
    editorModel.put(editorMode, editing);
    return editorModel;
  }

  //get contentLines variable using
  public String[] getEditorContentLines() {
    //typecasting is necessary because of the generic Object type of the value
    return (String[])editorModel.get(editorContentLines);
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

  public ModeToKeymap getModeToKeymap() {
    return (ModeToKeymap)editorModel.get(modeToKeymap);
  }

  public Operator getOperator(EditorMode editorMode) {
    editorMode = getEditorMode();
    KeyToOperator keymap = ModeToKeymap.
  }

  //only getters, no setters
  public int getCanvasX() {
    int contentX = getContentX();
    int contentY = getContentY();
    int adjustedContentX = contentX;
    String[] contentLines = getEditorContentLines();
    if(contentX > contentLines[contentY].length() - 1) {
      adjustedContentX = contentLines[contentY].length() - 1;
    }
    int canvasWidth = getCanvasWidth();
    return adjustedContentX % canvasWidth;
  }

  public int getCanvasY() {
    String[] contentLines = getEditorContentLines();
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

}
