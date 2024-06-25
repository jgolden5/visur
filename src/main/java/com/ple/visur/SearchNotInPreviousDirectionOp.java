package com.ple.visur;

public class SearchNotInPreviousDirectionOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    boolean previousDirectionWasForward = emc.getPreviousSearchDirectionWasForward();
    if(!previousDirectionWasForward) {
      SearchForwardOp searchForwardOp = new SearchForwardOp();
      searchForwardOp.execute(opInfo);
      emc.putPreviousSearchDirectionWasForward(false);
    } else {
      SearchBackwardOp searchBackwardOp = new SearchBackwardOp();
      searchBackwardOp.execute(opInfo);
      emc.putPreviousSearchDirectionWasForward(true);
    }
  }
}
