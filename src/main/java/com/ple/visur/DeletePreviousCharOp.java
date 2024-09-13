package com.ple.visur;

public class DeletePreviousCharOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int ca = emc.getCA();
    String editorContent = emc.getEditorContent();
    String editorContentBeforeChar = ca > 0 ? editorContent.substring(0, ca - 1) : "";
    String editorContentAfterChar = editorContent.substring(ca, editorContent.length());
    String resultingEditorContent = editorContentBeforeChar + editorContentAfterChar;
    emc.putEditorContent(resultingEditorContent);
    emc.putCA(ca);
  }
}
