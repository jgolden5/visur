package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum implements Quantum {
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    return new int[]{ca, ca + 1};
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv, int[] bounds) {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int destination = (int)caBVV.getVal();
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        destination = moveRight(editorContent, newlineIndices);
        mv.dx--;
      } else {
        destination = moveLeft(editorContent, newlineIndices);
        mv.dx++;
      }
    }
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        destination = moveDown(editorContent, newlineIndices);
        mv.dy--;
      } else {
        destination = moveUp(editorContent, newlineIndices);
        mv.dy++;
      }

    }
    return destination;
  }

  private int moveRight(String editorContent, ArrayList<Integer> newlineIndices) {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int destination = (int)caBVV.getVal();
    if (destination < editorContent.length() - 1) {
      destination++;
    }
    return destination;
  }

  private int moveLeft(String editorContent, ArrayList<Integer> newlineIndices) {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int destination = (int)caBVV.getVal();
    if (destination > 0) {
      destination--;
    }
    return destination;
  }

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices) {
    BrickVisurVar cxBVV = (BrickVisurVar)emc.getGlobalVar("cx");
    int cx = (int)cxBVV.getVal();
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    int cy = (int)cyBVV.getVal();
    Coordinate destinationAsCoordinate = Coordinate.make(cx, cy);

    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    return (int) caBVV.getVal();
  }

  private int moveUp(String editorContent, ArrayList<Integer> newlineIndices) {
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
    return "character";
  }
}
