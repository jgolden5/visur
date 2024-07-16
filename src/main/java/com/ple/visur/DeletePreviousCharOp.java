package com.ple.visur;

public class DeletePreviousCharOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    BrickVisurVar realCABVV = (BrickVisurVar) emc.getGlobalVar("realCA");
    int realCA = (int)realCABVV.getVal();
    String editorContent = emc.getEditorContent();
    String editorContentBeforeChar = realCA > 0 ? editorContent.substring(0, realCA - 1) : "";
    String editorContentAfterChar = editorContent.substring(realCA, editorContent.length());
    String resultingEditorContent = editorContentBeforeChar + editorContentAfterChar;
    emc.putEditorContent(resultingEditorContent);
    emc.putGlobalVar("realCA", realCABVV);
  }
}
