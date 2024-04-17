package com.ple.visur;

import CursorPositionDC.EditorContentService;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import java.util.ArrayList;

import static com.ple.visur.EditorModelKey.*;

public class EditorModelCoupler {

  LocalMap<EditorModelKey, Object> editorModel;
  private EditorContentService editorContentService = ServiceHolder.editorContentService;

  private EditorModelCoupler(SharedData sharedData) {
    this.editorModel = sharedData.getLocalMap("editorModel");
  }

  public static EditorModelCoupler make(SharedData sharedData) {
    return new EditorModelCoupler(sharedData);
  }

  public String getEditorContent() {
    return editorContentService.getEditorContent(editorModel);
  }

  public VisurVar getGlobalVar(String varName) {
    return editorContentService.getGlobalVar(varName, editorModel);
  }

  public VariableMap getGlobalVariableMap() {
    return editorContentService.getGlobalVariableMap(editorModel);
  }

  public String getCurrentContentLine() {
    return editorContentService.getCurrentContentLine(editorModel);
  }

  public String getContentLineAtY(int y) {
    return editorContentService.getContentLineAtY(y, editorModel);
  }

  public ArrayList<Integer> getNewlineIndices() {
    //note that this must be re-updated every time editorContent is changed
    return editorContentService.getNewlineIndices(editorModel);
  }

  public int getVirtualX() {
    return editorContentService.getVirtualX(editorModel);
  }

  public boolean getVirtualXIsAtEndOfLine() {
    return editorContentService.getVirtualXIsAtEndOfLine(editorModel);
  }

  public int getCanvasWidth() {
    return editorContentService.getCanvasWidth(editorModel);
  }

  public int getCanvasHeight() {
    return editorContentService.getCanvasHeight(editorModel);
  }

  public KeysPressed getKeyBuffer() {
    return (KeysPressed) editorModel.get(keyBuffer);
  }

  public EditorMode getEditorMode() {
    return (EditorMode)editorModel.get(editorMode);
  }

  public ModeToKeymap getKeymapMap() {
    return (ModeToKeymap) editorModel.get(modeToKeymap);
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
    editorContentService.putEditorContent(contentLines, editorModel);
  }

  public void putGlobalVar(String globalVarName, VisurVar globalVarValue) {
    editorContentService.putGlobalVar(globalVarName, globalVarValue, editorModel);
  }

  public void updateNewlineIndices() {
    editorContentService.updateNewlineIndices(editorModel);
  }

  public void putVirtualX(int x) {
    editorContentService.putVirtualX(x, editorModel);
  }

  public void putVirtualXIsAtEndOfLine(boolean isAtEndOfLine) {
    editorContentService.putVirtualXIsAtEndOfLine(isAtEndOfLine, editorModel);
  }

  public void putCanvasWidth(int width) {
    editorContentService.putCanvasWidth(width, editorModel);
  }

  public void putCanvasHeight(int height) {
    editorContentService.putCanvasHeight(height, editorModel);
  }

  public void putKeyBuffer(KeysPressed buffer) {
    editorModel.put(keyBuffer, buffer);
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

  public void putOnExecutionDataStack(Object element) {
    ExecutionDataStack eds = (ExecutionDataStack) editorModel.get(executionData);
    eds.push(element);
    putExecutionDataStack(eds);
  }

  public void putExecutionDataStack(ExecutionDataStack stack) {
    editorModel.put(executionData, stack);
  }

  public void putQuantumMap(QuantumMap qm) {
    editorModel.put(quantumMap, qm);
  }

  public void putCurrentQuantum(Quantum q) {
    editorModel.put(quantum, q);
  }

  public void putQuantumStart(int startBound) {
    editorModel.put(quantumStart, startBound);
  }

  public void putQuantumEnd(int endBound) {
    editorModel.put(quantumEnd, endBound);
  }

  public void reportError(String message) {
    System.out.println(message);
  }

}
