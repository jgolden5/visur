package com.ple.visur;

import java.util.ArrayList;

public class WrappedLineQuantum implements Quantum {

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int x, int y, boolean includeTail) {
    int lowerBound = 0;
    int upperBound = 0;
    if(editorContent.length() > 0) {
      if (y > 0) {
        lowerBound = newlineIndices.get(y - 1) + 1;
        boolean editorContentEndsWithNewlineChar = newlineIndices.size() < y;
        if (editorContentEndsWithNewlineChar) {
          upperBound = newlineIndices.get(y);
        } else {
          upperBound = editorContent.length() - 1;
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
  public Coordinate move(String editorContent, ArrayList<Integer> newlineIndices, Coordinate startingPos, MovementVector m, int[] bounds) {
    Coordinate destination = startingPos;
    return destination;
  }

  @Override
  public String getName() {
    return "wrappedLine";
  }

}
