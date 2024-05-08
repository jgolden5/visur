package com.ple.visur;

import java.util.ArrayList;

public class WrappedLineQuantum implements Quantum {

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    int y = ServiceHolder.editorModelCoupler.getCY();
    int lowerBound = 0;
    int upperBound = 0;
    if(editorContent.length() > 0) {
      if (y > 0) {
        if(y < newlineIndices.size()) {
          lowerBound = newlineIndices.get(y - 1) + 1;
          upperBound = newlineIndices.get(y);
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
    int cx = ServiceHolder.editorModelCoupler.getCX();
    int cy = ServiceHolder.editorModelCoupler.getCY();
    Coordinate destinationAsCoordinate = Coordinate.make(cx, cy);

    return ServiceHolder.editorModelCoupler.getCA();
  }

  @Override
  public String getName() {
    return "wrappedLine";
  }

}
