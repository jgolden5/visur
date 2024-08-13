package com.ple.visur;

public class InsertNewlineOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    String editorContent = emc.getEditorContent();
    int realCA = emc.getCA();
    String contentBeforeChar = editorContent.substring(0, realCA);
    String contentAfterChar = editorContent.substring(realCA, editorContent.length());
    String resultingEditorContent = contentBeforeChar + "\n" + contentAfterChar;
    emc.putEditorContent(resultingEditorContent);
    emc.putCA(++realCA);
    emc.updateNextLineIndices();
    Quantum cursorQuantum = emc.getCursorQuantum();
    int[] bounds = cursorQuantum.getBoundaries(realCA, emc.getNextLineIndices(), emc.getSpan(), false);
    emc.putCursorQuantumStart(bounds[0]);
    emc.putCursorQuantumEnd(bounds[1]);
  }
}
