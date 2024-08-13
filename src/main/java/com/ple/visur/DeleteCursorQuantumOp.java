package com.ple.visur;

public class DeleteCursorQuantumOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int span = emc.getSpan();
    if(span > 0) {
      int startBound = emc.getCursorQuantumStart();
      int endBound = emc.getCursorQuantumEnd();
      String editorContent = emc.getEditorContent();
      String editorContentBeforeDeletedPortion = editorContent.substring(0, startBound);
      String editorContentAfterDeletedPortion = editorContent.substring(endBound, editorContent.length());
      String resultingEditorContent = editorContentBeforeDeletedPortion + editorContentAfterDeletedPortion;
      emc.putEditorContent(resultingEditorContent);
      int realCA = emc.getCA();
      if(realCA > resultingEditorContent.length()) {
        realCA = resultingEditorContent.length();
        emc.putCA(realCA);
      }
      Quantum cursorQuantum = emc.getCursorQuantum();
      int[] bounds = cursorQuantum.getBoundaries(realCA, emc.getNextLineIndices(), span, false);
      emc.putCursorQuantumStart(bounds[0]);
      emc.putCursorQuantumEnd(bounds[1]);
    }
  }
}
