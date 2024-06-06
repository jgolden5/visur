package com.ple.visur;

public class ChangeSubmodeOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String editorSubmodeAsString = (String)eds.pop();
    String editorSubmodeAsStringWithoutQuotes = editorSubmodeAsString.substring(1, editorSubmodeAsString.length() - 1);
    EditorSubmode editorSubmode = EditorSubmode.getSubmodeByString(editorSubmodeAsStringWithoutQuotes);
    if(editorSubmode != null) {
      emc.putEditorSubmode(editorSubmode);
    }
  }
}
