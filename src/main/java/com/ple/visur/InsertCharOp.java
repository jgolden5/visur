package com.ple.visur;

public class InsertCharOp implements Operator {

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String charToBeInsertedWithQuotes = (String) eds.pop();
    String charToBeInsertedWithoutQuotes = charToBeInsertedWithQuotes.substring(1, charToBeInsertedWithQuotes.length() - 1);
    System.out.println("insert char " + charToBeInsertedWithQuotes);
  }

}
