package com.ple.visur;

public class AssignmentOp implements Operator {

  public static AssignmentOp make() {
    return new AssignmentOp();
  }

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler ems = ServiceHolder.editorModelCoupler;
    ExecutionDataStack es = ems.getExecutionDataStack();
    Object topElementFromStack = es.pop();
    VariableMap gvm = ems.getGlobalVarMap();
    VisurVar globalVar = gvm.get((String)opInfo);
    globalVar.put(topElementFromStack);
  }

}
