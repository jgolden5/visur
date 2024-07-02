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
    int ca = (int)caBVV.getVal();
    int destination = ca + 1;

    emc.putCA(destination);
    int cx = emc.getCX();
    int canvasWidth = emc.getCanvasWidth();
    emc.putVirtualCX(cx % canvasWidth);

    return destination;
  }

  private int moveLeft() {
    int ca = emc.getCA();
    if(ca > 0) {
      emc.putCA(--ca);
    }
    int cx = emc.getCX();
    int canvasWidth = emc.getCanvasWidth();
    emc.putVirtualCX(cx % canvasWidth);

    return ca;
  }

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices) {
    int cx = emc.getCX();
    int cy = emc.getCY();
    int canvasWidth = emc.getCanvasWidth();
    int[] shortBounds = emc.calcShortLineBoundaries();
    boolean isOnLastShortLineInLongLine = shortBounds[1] - shortBounds[0] < canvasWidth;
    boolean isAtEndOfEditorContent = cy == newlineIndices.size() && isOnLastShortLineInLongLine;
    boolean shouldIncrementCY = isOnLastShortLineInLongLine && !isAtEndOfEditorContent;
    int virtualCX = emc.getVirtualCX();
    int[] longBounds;
    if(shouldIncrementCY) {
      emc.putCY(++cy);
      emc.putCX(0); //default for testing getLongLineBoundaries so old cx does not mismatch ca with new cy
      longBounds = emc.getLongLineBoundaries(editorContent, newlineIndices, false);
      cx = virtualCX < longBounds[1] - longBounds[0] ? virtualCX : longBounds[1] - longBounds[0];
    } else if(!isAtEndOfEditorContent) {
      longBounds = emc.getLongLineBoundaries(editorContent, newlineIndices, false);
      if(!isAtEndOfEditorContent) {
        cx = cx + canvasWidth < longBounds[1] - longBounds[0] ? cx + canvasWidth : longBounds[1] - longBounds[0];
      } else {
        cx = longBounds[1] - longBounds[0];
      }
    }
    emc.putCX(cx);
    int span = emc.getSpan();
    int ca = emc.getCA();
    return span > 0 && ca == editorContent.length() ? --ca : ca;
  }

  private int moveUp(String editorContent, ArrayList<Integer> newlineIndices) {
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    Integer cy = (Integer)cyBVV.getVal();
    boolean canDecrementCY = cy > 0;
    if(canDecrementCY) {
      cy--;
      cyBVV.putVal(cy);
      boolean editorContentContainsNewlineChar = newlineIndices.size() > 0;
      int lineStartBound, lineEndBound;
      if(editorContentContainsNewlineChar) {
        lineStartBound = cy > 0 ? newlineIndices.get(cy - 1) + 1 : 0;
        lineEndBound = newlineIndices.get(cy);
      } else {
        lineStartBound = 0;
        lineEndBound = editorContent.length();
      }
      int currentLineLength = lineEndBound - lineStartBound;
      BrickVisurVar cxBVV = (BrickVisurVar) emc.getGlobalVar("cx");
      int cx;
      int virtualCX = emc.getVirtualCX();
      boolean virtualCXIsTooLong = virtualCX > currentLineLength;
      if(virtualCXIsTooLong) {
        cx = currentLineLength;
      } else {
        cx = virtualCX;
      }
      cxBVV.putVal(cx);
      emc.putGlobalVar("cx", cxBVV);
    }
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    return (int) caBVV.getVal();
  }

  @Override
  public String getName() {
    return "character";
  }

}
