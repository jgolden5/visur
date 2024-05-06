package com.ple.visur;

public class AssignmentOp implements Operator {

  public static AssignmentOp make() {
    return new AssignmentOp();
  }

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack es = emc.getExecutionDataStack();
    Object topElementFromStack = es.pop();
    emc.putGlobalVar((String)opInfo, VisurVar.make(topElementFromStack));
  }

}
