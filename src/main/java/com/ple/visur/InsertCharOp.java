package com.ple.visur;

public class InsertCharOp implements Operator {

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String charToBeInserted = (String) eds.pop();
    System.out.println("insert char " + charToBeInserted);
  }

}
