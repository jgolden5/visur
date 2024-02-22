package com.ple.visur;

public class AddOperator implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionDataStack es = ems.getExecutionDataStack();
    int a = (int)es.pop();
    int b = (int)es.pop();
    es.push(a + b);
  }
}
