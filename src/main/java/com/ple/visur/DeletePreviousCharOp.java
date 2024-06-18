package com.ple.visur;

public class DeletePreviousCharOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    String editorContent = emc.getEditorContent();
    String editorContentBeforeChar = ca > 0 ? editorContent.substring(0, ca - 1) : "";
    String editorContentAfterChar = editorContent.substring(ca, editorContent.length());
    String resultingEditorContent = editorContentBeforeChar + editorContentAfterChar;
    emc.putEditorContent(resultingEditorContent);
    emc.updateNewlineIndices();
  }
}
