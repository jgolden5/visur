package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum implements Quantum {
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int x, int y, boolean includeTail) {
    return new int[]{x, x + 1};
  }

  @Override
  public CursorPosition move(String editorContent, ArrayList<Integer> newlineIndices, CursorPosition startingPos, MovementVector mv, int[] bounds) {
    int x = startingPos.x;
    CursorPosition destination = startingPos;
    int endLimit = editorContent.length() - 1;
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        if (x < endLimit) {
          destination.x++;
        }
        mv.dx--;
      } else {
        if(x > 0) {
          destination.x--;
        }
        mv.dx++;
      }
    }
    return destination;
  }

  @Override
  public String getName() {
    return "character";
  }
}
