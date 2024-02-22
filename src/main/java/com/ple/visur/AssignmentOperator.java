package com.ple.visur;

public class AssignmentOperator implements Operator {

  public static AssignmentOperator make() {
    return new AssignmentOperator();
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
