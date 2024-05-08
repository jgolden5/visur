package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum implements Quantum {
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int x, int y, boolean includeTail) {
    return new int[]{x, x + 1};
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, int startingPos, MovementVector mv, int[] bounds) {
    int destination = startingPos;
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

  private int moveRight(String editorContent, ArrayList<Integer> newlineIndices, int startingPos) {
    int destination = startingPos;
    if (destination < editorContent.length() - 1) {
      destination++;
    }
    return destination;
  }

  private int moveLeft(String editorContent, ArrayList<Integer> newlineIndices, int startingPos) {
    int destination = startingPos;
    if (destination > 0) {
      destination--;
    }
    return destination;
  }

  @Override
  public String getName() {
    return "character";
  }
}
