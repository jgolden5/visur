package com.ple.visur;

public class SetSpanOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    int spanToSet = (int)eds.pop();
    System.out.println("span will switch to = " + spanToSet);
//    emc.putSpan(spanToSet);
  }
}
