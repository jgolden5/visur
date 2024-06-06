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
        if(destination < editorContent.length() - 1) {
          destination = moveRight();
        }
        mv.dx--;
      } else {
        if(destination > 0) {
          destination = moveLeft();
        }
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

  private int moveRight() {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int destination = (int)caBVV.getVal();
    return ++destination;
  }

  private int moveLeft() {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int destination = (int)caBVV.getVal();
    if (destination > 0) {
      destination--;
    }
    return destination;
  }

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices) {
    BrickVisurVar cxBVV = (BrickVisurVar)emc.getGlobalVar("cx");
    Integer cx = (Integer)cxBVV.getVal();
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    Integer cy = (Integer)cyBVV.getVal();
    boolean lastCharIsNewline = editorContent.charAt(editorContent.length() - 1) == '\n';
    boolean canIncrementCY;
    if(lastCharIsNewline) {
      canIncrementCY = cy < newlineIndices.size() - 1;
    } else {
      canIncrementCY = cy < newlineIndices.size();
    }
    if(canIncrementCY) {
      cy++;
      cyBVV.putVal(cy);
      int[] currentLineBounds = emc.getCurrentLineBoundaries(editorContent, newlineIndices, false);
      int lengthOfCurrentLine = currentLineBounds[1] - currentLineBounds[0];
      if(cx >= lengthOfCurrentLine) {
        cxBVV.putVal(lengthOfCurrentLine);
      }
    }
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    return (int) caBVV.getVal();
  }

  private int moveUp(String editorContent, ArrayList<Integer> newlineIndices) {
    BrickVisurVar cxBVV = (BrickVisurVar)emc.getGlobalVar("cx");
    Integer cx = (Integer)cxBVV.getVal();
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    Integer cy = (Integer)cyBVV.getVal();
    boolean canDecrementCY = cy > 0;
    if(canDecrementCY) {
      cy--;
      cyBVV.putVal(cy);
      int[] currentLineBounds = emc.getCurrentLineBoundaries(editorContent, newlineIndices, false);
      int lengthOfCurrentLine = currentLineBounds[1] - currentLineBounds[0];
      if(cx >= lengthOfCurrentLine) {
        cxBVV.putVal(lengthOfCurrentLine);
      }
    }
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    return (int) caBVV.getVal();
  }

  @Override
  public String getName() {
    return "character";
  }

  @Override
  public int quantumStart() {
    return 0;
  }

  @Override
  public int quantumEnd() {
    return 0;
  }
}
