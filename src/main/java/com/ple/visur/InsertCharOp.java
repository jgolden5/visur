package com.ple.visur;

public class InsertCharOp implements Operator {

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String charToBeInsertedWithQuotes = (String) eds.pop();
    String charToBeInserted = charToBeInsertedWithQuotes.substring(1, charToBeInsertedWithQuotes.length() - 1);
    System.out.println("insert char " + charToBeInserted);
    String editorContent = emc.getEditorContent();
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    String contentBeforeChar = editorContent.substring(0, ca);
    String contentAfterChar = editorContent.substring(ca, editorContent.length());
    String resultingEditorContent = contentBeforeChar + charToBeInserted + contentAfterChar;
    emc.putEditorContent(resultingEditorContent);
    emc.updateNewlineIndices();
  }

}
