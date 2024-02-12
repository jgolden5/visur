package com.ple.visur;

public class AbsoluteMoveOperator implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionData eds = ems.getExecutionData();
    IntVisurVar contentY = new IntVisurVar((int)eds.stack.pop());
    IntVisurVar contentX = new IntVisurVar((int)eds.stack.pop());
    ems.putGlobalVar("contentX", contentX);
    ems.putGlobalVar("contentY", contentY);
  }
}
