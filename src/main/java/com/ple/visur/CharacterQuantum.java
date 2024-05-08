package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum implements Quantum {
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int x, int y, boolean includeTail) {
    return new int[]{x, x + 1};
  }

  @Override
  public Coordinate move(String editorContent, ArrayList<Integer> newlineIndices, Coordinate startingPos, MovementVector mv, int[] bounds) {
    Coordinate destination = startingPos;
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        destination = moveRight(editorContent, newlineIndices, startingPos);
        mv.dx--;
      } else {
        destination = moveLeft(editorContent, newlineIndices, startingPos);
        mv.dx++;
      }
    }
    return destination;
  }

  private Coordinate moveRight(String editorContent, ArrayList<Integer> newlineIndices, Coordinate startingPos) {
    Coordinate destination = startingPos;
    if (destination.x < editorContent.length() - 1) {
      destination.x++;
    }
    return destination;
  }

  private Coordinate moveLeft(String editorContent, ArrayList<Integer> newlineIndices, Coordinate startingPos) {
    Coordinate destination = startingPos;
    if (destination.x > 0) {
      destination.x--;
    }
    return destination;
  }

  @Override
  public String getName() {
    return "character";
  }
}
