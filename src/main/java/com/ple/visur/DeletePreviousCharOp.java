package com.ple.visur;

public class DeletePreviousCharOp implements Operator {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public void execute(Object opInfo) {
    int ca = emc.getCA();
    String editorContent = emc.getEditorContent();
    String editorContentBeforeChar = ca > 0 ? editorContent.substring(0, ca - 1) : "";
    String editorContentAfterChar = editorContent.substring(ca);
    String resultingEditorContent = editorContentBeforeChar + editorContentAfterChar;
    adjustCanvas(editorContent, resultingEditorContent, ca);
    emc.putEditorContent(resultingEditorContent);
    emc.putCA(ca);
  }

  private void adjustCanvas(String originalEditorContent, String resultingEditorContent, int ca) {
    if(ca >= originalEditorContent.length()) {
      int cx = emc.getCX();
      if (!resultingEditorContent.equals(originalEditorContent) && cx % emc.getCanvasWidth() == 0) {
        emc.decrementCanvasStart();
      }
    }
  }

}
