package com.ple.visur;

import java.util.ArrayList;

public class DeleteCursorQuantumOp implements Operator {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public void execute(Object opInfo) {
    int span = emc.getSpan();
    if(span > 0) {
      int startBound = emc.getCursorQuantumStart();
      int endBound = emc.getCursorQuantumEnd();
      String editorContent = emc.getEditorContent();
      String editorContentBeforeDeletedPortion = editorContent.substring(0, startBound);
      String editorContentAfterDeletedPortion = editorContent.substring(endBound);
      String resultingEditorContent = editorContentBeforeDeletedPortion + editorContentAfterDeletedPortion;
      emc.putEditorContent(resultingEditorContent);
      int ca = emc.getCA();
      if(ca >= resultingEditorContent.length()) {
        ca = resultingEditorContent.length() > 0 ? resultingEditorContent.length() - 1 : 0;
        emc.putCA(ca);
        emc.putVirtualCX(emc.getCX());
      }
      adjustCanvas(resultingEditorContent.length());
      ArrayList<Integer> nl = emc.getNextLineIndices();
      Quantum cursorQuantum = emc.getCursorQuantum();
      int[] bounds = cursorQuantum.getBoundaries(ca, nl, span, false);
      emc.putCursorQuantumStartAndScroll(bounds[0]);
      emc.putCursorQuantumEndAndScroll(bounds[1]);
    }
  }

  private void adjustCanvas(int contentLimit) {
    int canvasStart = emc.getCanvasStart();
    int canvasEnd = emc.getCanvasEnd();
    int cursorQuantumEnd = emc.getCursorQuantumEnd();
    boolean canvasStartCanBeDecremented = canvasStart > 0;
    boolean cursorQuantumEndIsAtContentEnd = cursorQuantumEnd >= contentLimit;
    boolean canvasEndGoesPastContentLimit = cursorQuantumEndIsAtContentEnd ? canvasEnd >= contentLimit : canvasEnd > contentLimit;
    boolean canvasStartShouldBeDecremented = canvasEndGoesPastContentLimit && canvasStartCanBeDecremented;
    if(canvasStartShouldBeDecremented) {
      emc.decrementCanvasStart();
      adjustCanvas(contentLimit);
    }
  }

}
