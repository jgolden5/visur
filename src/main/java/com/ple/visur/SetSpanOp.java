package com.ple.visur;

public class SetSpanOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    int spanToSet = (int)eds.pop();
    emc.putSpan(spanToSet);
    System.out.println("span was switched to = " + spanToSet);
    int[] bounds = emc.getCursorQuantum().getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), false);
    emc.putCursorQuantumStart(bounds[0]);
    emc.putCursorQuantumEnd(bounds[1]);
  }
}
