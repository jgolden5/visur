package com.ple.visur;

import java.util.ArrayList;

public class WrappedLineQuantum implements Quantum {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    int cy = (int)cyBVV.getVal();
    int lowerBound = 0;
    int upperBound = 0;
    if(editorContent.length() > 0) {
      if (cy > 0) {
        if(cy < newlineIndices.size()) {
          lowerBound = newlineIndices.get(cy - 1) + 1;
          upperBound = newlineIndices.get(cy);
        }
      } else {
        if (newlineIndices.size() > 0) {
          if (includeTail) {
            upperBound = newlineIndices.get(0) + 1;
          } else {
            upperBound = newlineIndices.get(0);
          }
        } else {
          upperBound = editorContent.length() - 1;
        }
      }
    }
    return new int[]{lowerBound, upperBound};
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
