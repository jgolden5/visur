package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum implements Quantum {
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    int ca = ServiceHolder.editorModelCoupler.getCA();
    return new int[]{ca, ca + 1};
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
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        destination = moveDown(editorContent, newlineIndices, startingPos);
        mv.dy--;
      } else {
        destination = moveUp(editorContent, newlineIndices, startingPos);
        mv.dy++;
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

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices, int startingPos) {
    return 0;
  }

  private int moveUp(String editorContent, ArrayList<Integer> newlineIndices, int startingPos) {
    return 0;
  }

  @Override
  public String getName() {
    return "character";
  }
}
