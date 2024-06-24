package com.ple.visur;

import CursorPositionDC.EditorContentService;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import java.util.ArrayList;
import java.util.Stack;

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

  public String getStrToMatch(boolean searchForwards, int startingPoint) {
    String editorContent = getEditorContent();
    String strToMatch = "";
    if(searchForwards && startingPoint < editorContent.length()) {
      strToMatch = editorContent.substring(startingPoint, startingPoint + 1);
    } else if(startingPoint > 0) {
      strToMatch = editorContent.substring(startingPoint - 1, startingPoint);
    }
    return strToMatch;
  }

  public String getEditorContent() {
    return ecs.getEditorContent(editorModel);
  }

  public VisurVar getGlobalVar(String varName) {
    return ecs.getGlobalVar(varName, editorModel);
  }

  public VariableMap getGlobalVarMap() {
    return ecs.getGlobalVarMap(editorModel);
  }

  public int getCA() {
    BrickVisurVar caBVV = (BrickVisurVar) getGlobalVar("ca");
    return (int)caBVV.getVal();
  }

  public int getCX() {
    BrickVisurVar cxBVV = (BrickVisurVar) getGlobalVar("cx");
    return (int)cxBVV.getVal();
  }

  public int getCY() {
    BrickVisurVar cyBVV = (BrickVisurVar) getGlobalVar("cy");
    return (int)cyBVV.getVal();
  }

  public String getContentLineAtY(int y) {
    return ecs.getContentLineAtY(y, editorModel);
  }

  public ArrayList<Integer> getNewlineIndices() {
    return ecs.getNewlineIndices(editorModel);
  }

  public int[] getCurrentLineBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    return ecs.getCurrentLineBoundaries(editorContent, newlineIndices, includeTail, editorModel);
  }

  public int getVirtualCX() {
    return ecs.getVirtualCX(editorModel);
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

  public EditorMode getEditorMode() {
    return (EditorMode)editorModel.get(editorMode);
  }

  public EditorSubmode getEditorSubmode() {
    Stack<EditorSubmode> submodeStack = getEditorSubmodeStack();
    return submodeStack.peek();
  }

  public Stack<EditorSubmode> getEditorSubmodeStack() {
    return (Stack<EditorSubmode>) editorModel.get(editorSubmodeStack);
  }

  public KeymapMap getKeymapMap() {
    return (KeymapMap) editorModel.get(modeToKeymap);
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

  public QuantumNameToQuantum getQuantumNameToQuantum() {
    return (QuantumNameToQuantum)editorModel.get(quantumNameToQuantum);
  }

  public KeyToQuantumName getKeyToQuantumName() {
    return (KeyToQuantumName) editorModel.get(keyToQuantumName);
  }

  public Quantum getCursorQuantum() {
    return (Quantum) getGlobalVar("cursorQuantum").getVal();
  }

  public int getCursorQuantumStart() {
    return (int)editorModel.get(quantumStart);
  }
  public int getCursorQuantumEnd() {
    return (int)editorModel.get(quantumEnd);
  }

  public boolean getIsAtQuantumStart() {
    return (boolean)editorModel.get(isAtQuantumStart);
  }

  public Quantum getScopeQuantum() {
    return (Quantum) getGlobalVar("scopeQuantum").getVal();
  }

  public int getSpan() {
    ObjectVisurVar spanVV = (ObjectVisurVar) getGlobalVar("span");
    return (int)spanVV.getVal();
  }

  public void initializeEditorContent(String content) {
    ecs.initializeEditorContent(content, editorModel);
  }

  public void putEditorContent(String content) {
    ecs.putEditorContent(content, editorModel);
  }
  public void putGlobalVar(String globalVarName, VisurVar globalVarValue) {
    ecs.putGlobalVar(globalVarName, globalVarValue, editorModel);
  }
  public void putGlobalVarMap(VariableMap gvm) {
    ecs.putGlobalVarMap(gvm, editorModel);
  }

  public void putCA(int ca) {
    BrickVisurVar caBVV = (BrickVisurVar) getGlobalVar("ca");
    caBVV.putVal(ca);
    putGlobalVar("ca", caBVV);
  }

  public void putCX(int cx) {
    BrickVisurVar cxBVV = (BrickVisurVar) getGlobalVar("cx");
    cxBVV.putVal(cx);
    putGlobalVar("cx", cxBVV);
  }

  public void putCY(int cy) {
    BrickVisurVar cyBVV = (BrickVisurVar) getGlobalVar("cy");
    cyBVV.putVal(cy);
    putGlobalVar("cy", cyBVV);
  }

  public void updateNewlineIndices() {
    ecs.updateNewlineIndices(editorModel);
  }

  public void putVirtualCX(int x) {
    ecs.putVirtualCX(x, editorModel);
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

  public void putEditorMode(EditorMode mode) {
    editorModel.put(editorMode, mode);
  }

  public void putEditorSubmode(EditorSubmode submode) {
    Stack<EditorSubmode> editorSubmodeStack = getEditorSubmodeStack();
    editorSubmodeStack.push(submode);
    putEditorSubmodeStack(editorSubmodeStack);
  }

  public void putEditorSubmodeStack(Stack<EditorSubmode> submodeStack) {
    editorModel.put(editorSubmodeStack, submodeStack);
  }

  public void putKeymapMap(KeymapMap keymapMap) {
    editorModel.put(modeToKeymap, keymapMap);
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

  public void putQuantumNameToQuantum(QuantumNameToQuantum qNameToQuantum) {
    editorModel.put(quantumNameToQuantum, qNameToQuantum);
  }

  public void putKeyToQuantumName(KeyToQuantumName keyToQName) {
    editorModel.put(keyToQuantumName, keyToQName);
  }

  public void putCursorQuantum(Quantum cq) {
    ObjectVisurVar cursorQuantumVV = ObjectVisurVar.make(cq);
    putGlobalVar("cursorQuantum", cursorQuantumVV);
  }

  public void putCursorQuantumStart(int startBound) {
    editorModel.put(quantumStart, startBound);
  }

  public void putCursorQuantumEnd(int endBound) {
    editorModel.put(quantumEnd, endBound);
  }

  public void putIsAtQuantumStart(boolean isAtQStart) {
    editorModel.put(isAtQuantumStart, isAtQStart);
  }

  public void putScopeQuantum(Quantum q) {
    ObjectVisurVar scopeQuantumVV = ObjectVisurVar.make(q);
    putGlobalVar("scopeQuantum", scopeQuantumVV);
  }

  public void putSpan(int s) {
    ObjectVisurVar spanVV = ObjectVisurVar.make(s);
    putGlobalVar("span", spanVV);
  }

  public void reportError(String message) {
    System.out.println(message);
  }

}
