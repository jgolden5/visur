package com.ple.visur;

public class ChangeScopeOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String scopeName = (String) eds.pop();
    System.out.println("scope = " + scopeName);
  }
}
