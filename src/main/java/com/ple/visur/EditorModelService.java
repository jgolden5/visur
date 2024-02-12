package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import java.util.Stack;

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

  public VisurVar getGlobalVar(String varName) {
    VariableMap globalVarMap = (VariableMap)editorModel.get(globalVariableMap);
    return globalVarMap.get(varName);
  }

  public VariableMap getGlobalVariableMap() {
    return (VariableMap)editorModel.get(globalVariableMap);
  }

  public int getCurrentContentLineLength() {
    final String[] editorContentLines = getEditorContentLines();
    int contentY = getGlobalVar("contentY").getInt();
    final String currentContentLine = editorContentLines[contentY];
    return currentContentLine.length();
  }

  public String getCurrentContentLine() {
    String[] contentLines = getEditorContentLines();
    int currentContentLineIndex = getGlobalVar("contentY").getInt();
    return contentLines[currentContentLineIndex];
  }

  public int getVirtualX() {
    return (int)editorModel.get(virtualX);
  }

  public boolean getVirtualXIsAtEndOfLine() {
    return (boolean)editorModel.get(virtualXIsAtEndOfLine);
  }

  public KeysPressed getKeyBuffer() {
    return (KeysPressed)editorModel.get(keyBuffer);
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



  public KeysToOperatorHandler[] getKeyToOperatorHandlers(EditorMode mode) {
    return (KeysToOperatorHandler[])editorModel.get(mode);
  }

  public ModeToHandlerArray getModeToHandlerArray() {
    return (ModeToHandlerArray)editorModel.get(modeToHandlerArray);
  }

  public boolean getIsInCommandState() {
    return (boolean)editorModel.get(isInCommandState);
  }

  public String getCommandStateContent() {
    return (String)editorModel.get(commandStateContent);
  }

  public int getCommandCursor() {
    return (int)editorModel.get(commandCursor);
  }

  public ExecutionData getExecutionData() {
    return (ExecutionData)editorModel.get(executionData);
  }

  public SimpleQuantum getQuantum() {
    return (SimpleQuantum)editorModel.get(quantum);
  }

// add/improve getters and setters for:
// Operator, KeyPressed, (not sure: KeysToVisurCommand, OperatorService)

  //only getters, no setters
  public int getCanvasX() {
    int contentX = getGlobalVar("contentX").getInt();
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
    for(int i = 0; i < getGlobalVar("contentY").getInt(); i++) {
      String currentIteratedLine = getEditorContentLines()[i];
      canvasY += currentIteratedLine.length() / getCanvasWidth();
      if(currentIteratedLine.length() % getCanvasWidth() != 0 || currentIteratedLine.length() == 0) {
        canvasY++;
      }
    }
    return canvasY;
  }

  private int calculateCanvasYAtCurrentLine() {
    int contentX = getGlobalVar("contentX").getInt();
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

  public void putGlobalVar(String globalVarName, VisurVar globalVarValue) {
    VariableMap gvm = getGlobalVariableMap();
    gvm.put(globalVarName, globalVarValue); //updates value that was previously at associated key
    editorModel.put(globalVariableMap, gvm);
  }

  public void putVirtualX(int x) {
    editorModel.put(virtualX, x);
    putVirtualXIsAtEndOfLine(false);
  }

  public void putVirtualXIsAtEndOfLine(boolean isAtEndOfLine) {
    editorModel.put(virtualXIsAtEndOfLine, isAtEndOfLine);
  }

  public void putKeyBuffer(KeysPressed buffer) {
    editorModel.put(keyBuffer, buffer);
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

  public void putCommandStateContent(String content) {
    editorModel.put(commandStateContent, content);
  }

  public void putCommandCursor(int x) {
    editorModel.put(commandCursor, x);
  }

  public void putExecutionDataStack(ExecutionData stack) {
    editorModel.put(executionData, stack);
  }

  public void putOnExecutionDataStack(Object element) {
    ExecutionData executionData = getExecutionData();
    executionData.stack.push(element);

    editorModel.put(EditorModelKey.executionData, executionData);
  }

  public void putQuantum(SimpleQuantum q) {
    editorModel.put(quantum, q);
  }

  public void reportError(String message) {
    System.out.println(message);
  }

}
