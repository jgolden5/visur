package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import static com.ple.visur.EditorModelKey.*;

public class EditorModelService {
  //declare editor model as localMap
  final LocalMap<EditorModelKey, Object> editorModel;

  //editorModelService constructor
  private EditorModelService(LocalMap<EditorModelKey, Object> editorModel) {
    this.editorModel = editorModel;
  }

  //factory method to control constructor with more precision
  public static EditorModelService make(LocalMap<EditorModelKey, Object> editorModel) {
    // if editorModel variable is empty, set initial values for each of the variables in the editor model
    // that require an initial value
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
    return editorModel;
  }

  //get contentLines variable using
  public String[] getContentLines() {
    return (String[])sharedData.getLocalMap("editorModel").get(editorContentLines.name());
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

  public void putContentLines(String[] contentLines) {
    sharedData.getLocalMap("editorModel").put(editorContentLines.name(), contentLines);
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
