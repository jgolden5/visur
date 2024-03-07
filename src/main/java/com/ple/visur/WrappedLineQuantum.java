package com.ple.visur;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class WrappedLineQuantum implements Quantum {

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int x, int y, boolean includeTail) {
    boolean lowerBoundFound = false; //check for lowerBound first
    boolean upperBoundFound = false; //if lowerBoundFound, check for upperBound
    int[] bounds = new int[]{x, x};
    boolean lowerAndUpperBoundsFound = false;
    while (!lowerAndUpperBoundsFound) {
      if(!lowerBoundFound) {
        if (x <= 0 || editorContent.charAt(x - 1) == '\n') {
          lowerBoundFound = true;
        } else {
          bounds[x]--;
        }
      } else {
        if(x >= editorContent.length() - 1 && editorContent.charAt(x + 1) != '\n') {
          bounds[x]++;
        } else {
          upperBoundFound = true;
        }
      }
      lowerAndUpperBoundsFound = lowerBoundFound && upperBoundFound;
    }
    return bounds;
  }

  @Override
  public CursorPosition move(String editorContent, ArrayList<Integer> newlineIndices, CursorPosition startingPos, MovementVector m, int[] bounds) {
    CursorPosition destination = startingPos;
    return destination;
  }

  @Override
  public String getName() {
    return "wrappedLine";
  }

}
