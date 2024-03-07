package com.ple.visur;

import java.util.ArrayList;

public class WrappedLineQuantum implements Quantum {

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int x, int y, boolean includeTail) {
    int startBound = 0;
    int endBound = 0;
    return new int[]{startBound, endBound};
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
