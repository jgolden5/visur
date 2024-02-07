package com.ple.visur;

public class AssignmentOperator implements Operator {

  public static AssignmentOperator make() {
    return new AssignmentOperator();
  }

  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionState es = ems.getExecutionState();
    Object topElementFromStack = es.stack.pop();
    VariableMap gvm = ems.getGlobalVariableMap();
    VisurVar gvmVal = gvm.get((String)opInfo);
    gvmVal.put(topElementFromStack);
  }

}
