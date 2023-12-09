package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import static com.ple.visur.EditorMode.*;
import static com.ple.visur.EditorModelKey.*;

public class EditorModelService {

  LocalMap<EditorModelKey, Object> editorModel;

  private EditorModelService(SharedData sharedData) {
    this.editorModel = sharedData.getLocalMap("editorModel");
    setInitialValues(editorModel);
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

  public KeyPressed getKeyPressed() {
    return (KeyPressed)editorModel.get(keyPressed);
  }

  public KeyToOperator getEditingKeymap() {
    return (KeyToOperator)editorModel.get(editingKeymap);
  }

  public KeyToOperator getInsertKeymap() {
    return (KeyToOperator)editorModel.get(insertKeymap);
  }

  public ModeToKeymap getKeymapMap() {
    return (ModeToKeymap)editorModel.get(modeToKeymap);
  }

// add/improve getters and setters for:
// Operator, KeyPressed, (not sure: KeyToOperator, OperatorService)

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

  public void putEditingKeymap(KeyToOperator keymap) {
    editorModel.put(editingKeymap, keymap);
  }

  public void putInsertKeymap(KeyToOperator keymap) {
    editorModel.put(insertKeymap, keymap);
  }

  public void putKeymapMap(ModeToKeymap keymapMap) {
    editorModel.put(modeToKeymap, keymapMap);
  }

  public void putOperatorToService(OperatorToService opToService) {
    editorModel.put(operatorToService, opToService);
  }

  private void setInitialValues(LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(editorContentLines, "test words");
    editorModel.put(contentX, 0);
    editorModel.put(contentY, 0);
    editorModel.put(virtualX, 0);
    editorModel.put(virtualXIsAtEndOfLine, false);
    editorModel.put(editorMode, editing);
//    final String initialContentLines = "Hello" +
//      "\nWho's there??" +
//      "\nGoodbye world.";
    final String initialContentLines = "31 Yea, I would that ye would come forth and harden not your hearts any longer; for behold, now is the time and the day of your salvation; and therefore, if ye will repent and harden not your hearts, immediately shall the great plan of redemption be brought about unto you." +
      "\n\t32 For behold, this life is the time for men to prepare to meet God; yea, behold the day of this life is the day for men to perform their labors." +
      "\n\t\t33 And now, as I said unto you before, as ye have had so many witnesses, therefore, I beseech of you that ye do not procrastinate the day of your repentance until the end; for after this day of life, which is given us to prepare for eternity, behold, if we do not improve our time while in this life, then cometh the night of darkness wherein there can be no labor performed." +
      "\n 34 Ye cannot say, when ye are brought to that awful crisis, that I will repent, that I will return to my God. Nay, ye cannot say this; for that same spirit which doth possess your bodies at the time that ye go out of this life, that same spirit will have power to possess your body in that eternal world.";
    putEditorContentLines(initialContentLines.split("\n"));
  }

  public boolean handleKeyPress(KeyPressed keyPressed) {
    boolean keyPressWasHandled = false;
    final ModeToKeymap modeToKeymap = getKeymapMap();
//    final KeyToOperator keyToOperator = modeToKeymap.keymapMap.get(getEditorMode());
    final KeyToOperator keymap = getEditingKeymap();
    Operator operator = keymap.get(keyPressed);
    if(operator != null) {
      OperatorToService operatorToService = OperatorToService.make();
      OperatorService operatorService = operatorToService.get(operator);
      operatorService.execute(operator);
    }
    keyPressWasHandled = true;
    return keyPressWasHandled;
  }

}
