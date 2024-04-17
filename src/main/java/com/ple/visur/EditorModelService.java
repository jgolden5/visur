package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import java.util.ArrayList;

import static com.ple.visur.EditorModelKey.*;

public class EditorModelService {

  LocalMap<EditorModelKey, Object> editorModel;
  EditorContentService editorContentService = ServiceHolder.editorContentService;

  private EditorModelService(SharedData sharedData) {
    this.editorModel = sharedData.getLocalMap("editorModel");
  }

  public static EditorModelService make(SharedData sharedData) {
    return new EditorModelService(sharedData);
  }

  //get contentLines variable using
  public String getEditorContent() {
    //typecasting is necessary because of the generic Object type of the value
    return (String)editorModel.get(editorContent);
  }

  public VisurVar getGlobalVar(String varName) {
    VariableMap globalVarMap = (VariableMap)editorModel.get(globalVariableMap);
    return globalVarMap.get(varName);
  }

  public VariableMap getGlobalVariableMap() {
    return (VariableMap)editorModel.get(globalVariableMap);
  }

  public String getCurrentContentLine() {
    int contentY = getGlobalVar("contentY").getInt();
    return getContentLineAtY(contentY);
  }

  public String getContentLineAtY(int y) {
    final String content = getEditorContent();
    ArrayList<Integer> newlineIndices = getNewlineIndices();
    final String currentContentLine;
    int lastNewlineIndex = newlineIndices.get(newlineIndices.size() - 1);
    if(y < newlineIndices.size()) {
      if(y > 0) {
        currentContentLine = content.substring(newlineIndices.get(y - 1) + 1, newlineIndices.get(y));
      } else {
        currentContentLine = content.substring(0, newlineIndices.get(y));
      }
    } else if(content.length() > lastNewlineIndex + 1) {
      currentContentLine = content.substring(lastNewlineIndex + 1);
    } else {
      currentContentLine = null;
    }
    return currentContentLine;
  }

  public int getCurrentContentLineLength() {
    return getCurrentContentLine().length();
  }

  public ArrayList<Integer> getNewlineIndices() {
    //note that this must be re-updated every time editorContent is changed
    return (ArrayList<Integer>) editorModel.get(newlineIndices);
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

  public ExecutionDataStack getExecutionDataStack() {
    return (ExecutionDataStack)editorModel.get(executionData);
  }

  public QuantumMap getQuantumMap() {
    return (QuantumMap)editorModel.get(quantumMap);
  }

  public Quantum getCurrentQuantum() {
    return (Quantum)editorModel.get(quantum);
  }

  public int getQuantumStart() {
    return (int)editorModel.get(quantumStart);
  }

  public int getQuantumEnd() {
    return (int)editorModel.get(quantumEnd);
  }

  public void putEditorContent(String contentLines) {
    editorModel.put(editorContent, contentLines);
    updateNewlineIndices();
  }

  public void putGlobalVar(String globalVarName, VisurVar globalVarValue) {
    VariableMap gvm = getGlobalVariableMap();
    gvm.put(globalVarName, globalVarValue); //updates value that was previously at associated key
    editorModel.put(globalVariableMap, gvm);
  }

  public void updateNewlineIndices() {
    String content = getEditorContent();
    ArrayList<Integer> indices = new ArrayList<>();
    boolean keepGoing = true;
    int fullStringIndex = 0;
    while(keepGoing) {
      int substringIndex = content.indexOf("\n");
      if(substringIndex != -1) {
        fullStringIndex += substringIndex;
        indices.add(fullStringIndex);
        content = content.substring(substringIndex + 1);
        fullStringIndex++; //because of newline char
      } else {
        keepGoing = false;
      }
    }
    editorModel.put(newlineIndices, indices);
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

  public void putExecutionDataStack(ExecutionDataStack stack) {
    editorModel.put(executionData, stack);
  }

  public void putOnExecutionDataStack(Object element) {
    ExecutionDataStack executionData = getExecutionDataStack();
    executionData.push(element);

    editorModel.put(EditorModelKey.executionData, executionData);
  }

  public void putQuantumMap(QuantumMap qm) {
    editorModel.put(quantumMap, qm);
  }

  public void putCurrentQuantum(Quantum q) {
    editorModel.put(quantum, q);
  }

  public void reportError(String message) {
    System.out.println(message);
  }

  public void putQuantumStart(int startBound) {
    editorModel.put(quantumStart, startBound);
  }

  public void putQuantumEnd(int endBound) {
    editorModel.put(quantumEnd, endBound);
  }

}
