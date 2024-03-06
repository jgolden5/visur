package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum implements Quantum {
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int x, int y) {
    return new int[]{x, x + 1};
  }

  @Override
  public CursorPosition move(String editorContent, ArrayList<Integer> newlineIndices, CursorPosition startingPos, MovementVector m, int[] bounds) {
    return null;
  }

  @Override
  public String getName() {
    return "character";
  }
}
