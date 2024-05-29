package com.ple.visur;

import CursorPositionDC.CursorPositionDCHolder;
import CursorPositionDC.EditorContentService;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import java.util.ArrayList;

import static com.ple.visur.EditorModelKey.*;

public class EditorModelCoupler {

  LocalMap<EditorModelKey, Object> editorModel;
  private EditorContentService ecs = ServiceHolder.editorContentService;

  private EditorModelCoupler(SharedData sharedData) {
    this.editorModel = sharedData.getLocalMap("editorModel");
  }

  public static EditorModelCoupler make(SharedData sharedData) {
    return new EditorModelCoupler(sharedData);
  }

  public String getEditorContent() {
    return ecs.getEditorContent(editorModel);
  }

  public CursorPositionDCHolder getCursorPositionDCHolder() {
    return ecs.getCursorPositionDCHolder(editorModel);
  }

  public VisurVar getGlobalVar(String varName) {
    return ecs.getGlobalVar(varName, editorModel);
  }

  public VariableMap getGlobalVarMap() {
    return ecs.getGlobalVarMap(editorModel);
  }

  public String getContentLineAtY(int y) {
    return ecs.getContentLineAtY(y, editorModel);
  }

  public ArrayList<Integer> getNewlineIndices() {
    return ecs.getNewlineIndices(editorModel);
  }

  public int getVirtualX() {
    return ecs.getVirtualX(editorModel);
  }

  public boolean getVirtualXIsAtEndOfLine() {
    return ecs.getVirtualXIsAtEndOfLine(editorModel);
  }

  public int getCanvasWidth() {
    return ecs.getCanvasWidth(editorModel);
  }

  public int getCanvasHeight() {
    return ecs.getCanvasHeight(editorModel);
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
  public OperatorToService getOperatorToService() {
    return (OperatorToService) editorModel.get(operatorToService);
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
    ecs.putEditorContent(contentLines, editorModel);
  }
  public void putCursorPositionDCHolder(CursorPositionDCHolder cursorPositionDCHolder) {
    ecs.putCursorPositionDCHolder(cursorPositionDCHolder, editorModel);
  }
  public void putGlobalVar(String globalVarName, VisurVar globalVarValue) {
    ecs.putGlobalVar(globalVarName, globalVarValue, editorModel);
  }
  public void putGlobalVarMap(VariableMap gvm) {
    ecs.putGlobalVarMap(gvm, editorModel);
  }

  public void putNewlineIndices() {
    ecs.putNewlineIndices(editorModel);
  }

  public void putVirtualX(int x) {
    ecs.putVirtualX(x, editorModel);
  }

  public void putVirtualXIsAtEndOfLine(boolean isAtEndOfLine) {
    ecs.putVirtualXIsAtEndOfLine(isAtEndOfLine, editorModel);
  }

  public void putCanvasWidth(int width) {
    ecs.putCanvasWidth(width, editorModel);
  }

  public void putCanvasHeight(int height) {
    ecs.putCanvasHeight(height, editorModel);
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
  public void putModeToHandlerArray(ModeToHandlerArray mToHA) {
    editorModel.put(modeToHandlerArray, mToHA);
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
