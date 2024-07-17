package com.ple.visur;

public class SetSpanOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    int spanToSet = (int)eds.pop();
    spanToSet = spanToSet > 0 ? 1 : 0;
    System.out.println("span was switched to = " + spanToSet);
    int[] bounds = emc.getCursorQuantum().getBoundaries(emc.getRealCA(), emc.getNewlineIndices(), spanToSet, false);
    emc.putCursorQuantumStart(bounds[0]);
    emc.putCursorQuantumEnd(bounds[1]);
    if(bounds[0] == bounds[1] || spanToSet == 0) {
      emc.putSpan(0);
    } else {
      emc.putSpan(1);
    }
  }
}
