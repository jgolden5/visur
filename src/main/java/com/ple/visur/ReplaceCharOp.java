package com.ple.visur;

public class ReplaceCharOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String editorContent = emc.getEditorContent();
    BrickVisurVar realCABVV = (BrickVisurVar) emc.getGlobalVar("realCA");
    int realCA = (int)realCABVV.getVal();
    if(editorContent.charAt(realCA) != '\n') {
      String contentBeforeChar = editorContent.substring(0, realCA);
      String replacingChar = (String) eds.pop();
      String contentAfterChar = editorContent.substring(realCA + 1, editorContent.length());
      String resultingEditorContent = contentBeforeChar + replacingChar + contentAfterChar;
      emc.putEditorContent(resultingEditorContent);
    }
  }
}
