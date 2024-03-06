package com.ple.visur;

public class AssignmentOp implements Operator {

  public static AssignmentOp make() {
    return new AssignmentOp();
  }

  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionDataStack es = ems.getExecutionDataStack();
    Object topElementFromStack = es.pop();
    VariableMap gvm = ems.getGlobalVariableMap();
    VisurVar globalVar = gvm.get((String)opInfo);
    globalVar.put(topElementFromStack);
  }

}
