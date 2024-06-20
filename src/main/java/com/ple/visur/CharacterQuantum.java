package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum extends Quantum {
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    if(ca < editorContent.length()) {
      return new int[]{ca, ca + 1};
    } else {
      return new int[]{ca, ca};
    }
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv) {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int destination = (int)caBVV.getVal();
    int span = emc.getSpan();
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        int endLimit = span > 0 ? editorContent.length() - 1 : editorContent.length();
        if(destination < endLimit) {
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
//    int cx = emc.getCX();
    int destination = (int)caBVV.getVal();
    destination++;
    return destination;
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
    BrickVisurVar cyBVV = (BrickVisurVar) emc.getGlobalVar("cy");
    int cy = (int)cyBVV.getVal();
    boolean canMoveDown = cy < newlineIndices.size();
    if(canMoveDown) {
      cy++;
      cyBVV.putVal(cy);
      emc.putGlobalVar("cy", cyBVV);
      int[] currentLineBounds = emc.getCurrentLineBoundaries(editorContent, newlineIndices, false);
      int currentLineLength = currentLineBounds[1] - currentLineBounds[0];
      BrickVisurVar cxBVV = (BrickVisurVar) emc.getGlobalVar("cx");
      int cx = (int)cxBVV.getVal();
      boolean cxIsTooLong = cx > currentLineLength;
      if(cxIsTooLong) {
        cx = currentLineLength;
        cxBVV.putVal(cx);
        emc.putGlobalVar("cx", cxBVV);
      }
    }
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    return ca;
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

}
