package com.ple.visur;

public class InsertNewlineOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    String editorContent = emc.getEditorContent();
    BrickVisurVar realCABVV = (BrickVisurVar) emc.getGlobalVar("realCA");
    int realCA = (int)realCABVV.getVal();
    String contentBeforeChar = editorContent.substring(0, realCA);
    String contentAfterChar = editorContent.substring(realCA, editorContent.length());
    String resultingEditorContent = contentBeforeChar + "\n" + contentAfterChar;
    emc.putEditorContent(resultingEditorContent);
    realCABVV.putVal(realCA + 1);
    emc.updateNewlineIndices();
    Quantum cursorQuantum = emc.getCursorQuantum();
    int[] bounds = cursorQuantum.getBoundaries(editorContent, emc.getNewlineIndices(), emc.getSpan(), false);
    emc.putCursorQuantumStart(bounds[0]);
    emc.putCursorQuantumEnd(bounds[1]);
    emc.putGlobalVar("realCA", realCABVV);
  }
}
