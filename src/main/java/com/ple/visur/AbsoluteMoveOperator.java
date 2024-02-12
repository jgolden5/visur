package com.ple.visur;

public class AbsoluteMoveOperator implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionDataStack eds = ems.getExecutionData();
    IntVisurVar contentY = new IntVisurVar();
    IntVisurVar contentX = new IntVisurVar();
    contentY.put(eds.pop());
    contentX.put(eds.pop());
    ems.putGlobalVar("contentX", contentX);
    ems.putGlobalVar("contentY", contentY);
  }
}
