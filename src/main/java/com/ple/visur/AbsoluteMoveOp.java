package com.ple.visur;

public class AbsoluteMoveOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionDataStack eds = ems.getExecutionDataStack();
    IntVisurVar contentY = new IntVisurVar();
    IntVisurVar contentX = new IntVisurVar();
    contentY.put(eds.pop());
    contentX.put(eds.pop());
    ems.putGlobalVar("ca", contentX);
    ems.putGlobalVar("contentY", contentY);
  }
}
