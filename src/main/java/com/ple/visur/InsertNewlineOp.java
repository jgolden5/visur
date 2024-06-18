package com.ple.visur;

public class InsertNewlineOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    String editorContent = emc.getEditorContent();
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    String contentBeforeChar = editorContent.substring(0, ca);
    String contentAfterChar = editorContent.substring(ca, editorContent.length());
    String resultingEditorContent = contentBeforeChar + "\n" + contentAfterChar;
    emc.putEditorContent(resultingEditorContent);
    emc.updateNewlineIndices();
    BrickVisurVar cxBVV = (BrickVisurVar) emc.getGlobalVar("cx");
    BrickVisurVar cyBVV = (BrickVisurVar) emc.getGlobalVar("cy");
    int cy = (int)cyBVV.getVal();
    int cx = 0;
    cy++;
    cxBVV.putVal(cx);
    cyBVV.putVal(cy);
    emc.putGlobalVar("cx", cxBVV);
    emc.putGlobalVar("cy", cyBVV);
  }
}
