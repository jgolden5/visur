package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum extends Quantum {
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
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
    BrickVisurVar cxBVV = (BrickVisurVar)emc.getGlobalVar("cx");
    Integer cx = (Integer)cxBVV.getVal();
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    Integer cy = (Integer)cyBVV.getVal();
    boolean lastCharIsNewline = editorContent.charAt(editorContent.length() - 1) == '\n';
    int endLimit;
    if(lastCharIsNewline) {
      endLimit = newlineIndices.size() - 1;
    } else {
      endLimit = newlineIndices.size();
    }
    boolean canIncrementCY;
    int span = emc.getSpan();
    if(span > 0) {
      canIncrementCY = cy < endLimit;
    } else {
      canIncrementCY = cy < endLimit + 1;
    }
    if(canIncrementCY) {
      cy++;
      cyBVV.putVal(cy);
      int[] lineBounds = emc.getCurrentLineBoundaries(editorContent, newlineIndices, false);
      int lengthOfLineBounds = lineBounds[1] - lineBounds[0];
      if(cx >= lengthOfLineBounds) {
        cxBVV.putVal(lengthOfLineBounds);
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

}
