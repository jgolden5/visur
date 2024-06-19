package com.ple.visur;

import java.util.ArrayList;

public class WrappedLineQuantum extends Quantum {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    return emc.getCurrentLineBoundaries(editorContent, newlineIndices, includeTail);
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv) {
    CharacterQuantum cq = new CharacterQuantum();
    mv.dy += mv.dx;
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    int cy = (int)cyBVV.getVal();
    int span = emc.getSpan();
    boolean lastCharIsNewline = editorContent.charAt(editorContent.length() - 1) == '\n';
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        ca = cq.move(editorContent, newlineIndices, mv);
      } else {
        if(ca == editorContent.length() && editorContent.charAt(ca - 1) != '\n') {
          int[] bounds = getBoundaries(editorContent, newlineIndices, span, false);
          ca = bounds[0];
          mv.dy++;
        } else {
          boolean canGoUp = cy > 0;
          if (canGoUp) {
            ca = cq.move(editorContent, newlineIndices, mv);
          } else {
            mv.dy++;
          }
        }
      }
    }
    return ca;
  }

  @Override
  public String getName() {
    return "wrappedLine";
  }

}
