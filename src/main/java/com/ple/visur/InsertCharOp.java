package com.ple.visur;

public class InsertCharOp implements Operator {

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String charToBeInserted = (String) eds.pop();
    String editorContent = emc.getEditorContent();
    BrickVisurVar realCABVV = (BrickVisurVar) emc.getGlobalVar("realCA");
    int realCA = (int)realCABVV.getVal();
    String contentBeforeChar = editorContent.substring(0, realCA);
    String contentAfterChar = editorContent.substring(realCA, editorContent.length());
    String resultingEditorContent = contentBeforeChar + charToBeInserted + contentAfterChar;
    emc.putEditorContent(resultingEditorContent);
  }

}
