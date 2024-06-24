package com.ple.visur;

public class SearchOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String searchTarget = "";
    if(eds.size() > 0) {
      searchTarget = (String) eds.pop();
    }
    if(searchTarget == "") {
      System.out.println("No search target found");
    } else {
      System.out.println("search target = " + searchTarget);
    }
  }
}
