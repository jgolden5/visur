package com.ple.visur;

import java.util.ArrayList;

public class WrappedLineQuantum implements Quantum {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    return emc.getCurrentLineBoundaries(editorContent, newlineIndices, includeTail);
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv, int[] bounds) {
    mv.dy += mv.dx;
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    int cy = (int)cyBVV.getVal();
    boolean lastCharIsNewline = editorContent.charAt(editorContent.length() - 1) == '\n';
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        boolean canGoDown = lastCharIsNewline ? cy < newlineIndices.size() - 1 : cy < newlineIndices.size();
        if(canGoDown) {
          cy++;
        }
        mv.dy--;
      } else {
        boolean canGoUp = cy > 0;
        if(canGoUp) {
          cy--;
        }
        mv.dy++;
      }
    }
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    return (int) caBVV.getVal();
  }

  @Override
  public String getName() {
    return "wrappedLine";
  }

}
