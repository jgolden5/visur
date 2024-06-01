package com.ple.visur;

import java.util.ArrayList;

public class WrappedLineQuantum implements Quantum {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    return emc.getCurrentLineBoundaries(editorContent, newlineIndices, includeTail);
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m, int[] bounds) {
    BrickVisurVar cxBVV = (BrickVisurVar)emc.getGlobalVar("cx");
    int cx = (int)cxBVV.getVal();
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    int cy = (int)cyBVV.getVal();
    Coordinate destinationAsCoordinate = Coordinate.make(cx, cy);

    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    return (int) caBVV.getVal();
  }

  @Override
  public String getName() {
    return "wrappedLine";
  }

}
