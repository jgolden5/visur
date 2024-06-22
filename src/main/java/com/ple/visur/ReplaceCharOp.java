package com.ple.visur;

public class ReplaceCharOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String editorContent = emc.getEditorContent();
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    String contentBeforeChar = editorContent.substring(0, ca);
    String replacingChar = (String) eds.pop();
    String contentAfterChar = editorContent.substring(ca + 1, editorContent.length());
    String resultingEditorContent = contentBeforeChar + replacingChar + contentAfterChar;
    emc.putEditorContent(resultingEditorContent);
  }
}
