package com.ple.visur;

public class ChangeModeOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String editorModeAsString = (String)eds.peek();
    String editorModeAsStringWithoutQuotes = editorModeAsString.substring(1, editorModeAsString.length() - 1);
    EditorMode editorMode = EditorMode.getModeByString(editorModeAsStringWithoutQuotes);
    if(editorMode != null) {
      emc.putEditorMode(editorMode);
    }
    PushSubmodeOp pushSubmodeOp = new PushSubmodeOp();
    pushSubmodeOp.execute(opInfo);
  }
}
