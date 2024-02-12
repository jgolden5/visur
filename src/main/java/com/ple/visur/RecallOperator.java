package com.ple.visur;

public class RecallOperator implements Operator {
  public static RecallOperator make() {
    return new RecallOperator();
  }

  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    VisurVar globalVar = ems.getGlobalVar((String)opInfo);
    ems.putOnExecutionDataStack(globalVar.getInt());
  }

}
