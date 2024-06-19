package com.ple.visur;

public class ChangeModeOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String editorModeAsString = (String)eds.peek();
    EditorMode editorMode = EditorMode.getModeByString(editorModeAsString);
    if(editorMode != null) {
      emc.putEditorMode(editorMode);
    }
    PushSubmodeOp pushSubmodeOp = new PushSubmodeOp();
    pushSubmodeOp.execute(opInfo);
  }
}
