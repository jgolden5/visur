package com.ple.visur;

public class RecallOp implements Operator {
  public static RecallOp make() {
    return new RecallOp();
  }

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler ems = ServiceHolder.editorModelCoupler;
    VisurVar globalVar = ems.getGlobalVar((String)opInfo);
    ems.putOnExecutionDataStack(globalVar.getInt());
  }

}
