package com.ple.visur;

public class DeletePreviousCharOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int ca = emc.getCA();
    String editorContent = emc.getEditorContent();
    String editorContentBeforeChar = ca > 0 ? editorContent.substring(0, ca - 1) : "";
    String editorContentAfterChar = editorContent.substring(ca);
    String resultingEditorContent = editorContentBeforeChar + editorContentAfterChar;
    if(ca >= editorContent.length()) {
      int cx = emc.getCX();
      if (!resultingEditorContent.equals(editorContent) && cx % emc.getCanvasWidth() == 0) {
        emc.decrementCanvasStart();
      }
    }
    emc.putEditorContent(resultingEditorContent);
    emc.putCA(ca);
  }
}
