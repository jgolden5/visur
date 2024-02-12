package com.ple.visur;

public class RecallOperator implements Operator {
  public static RecallOperator make() {
    return new RecallOperator();
  }

  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    VariableMap gvm = ems.getGlobalVariableMap();
    VisurVar globalVar = gvm.get((String)opInfo);
    ExecutionData es = ems.getExecutionData();
    es.stack.push(globalVar);
  }

}
