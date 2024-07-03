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
      longBounds = emc.calcLongLineBoundaries(editorContent, newlineIndices, 1, false);
      cx = virtualCX < longBounds[1] - longBounds[0] ? virtualCX : longBounds[1] - longBounds[0];
    } else if(!isAtEndOfEditorContent) {
      longBounds = emc.calcLongLineBoundaries(editorContent, newlineIndices, 1, false);
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
    int cx = emc.getCX();
    int cy = emc.getCY();
    int canvasWidth = emc.getCanvasWidth();
    int[] shortBounds = emc.calcShortLineBoundaries();
    boolean isOnFirstShortLineInLongLine = shortBounds[0] == 0;
    boolean isAtBeginningOfEditorContent = cy == 0 && cx == 0;
    boolean shouldDecrementCY = isOnFirstShortLineInLongLine && !isAtBeginningOfEditorContent;
    int virtualCX = emc.getVirtualCX();
    int[] longBounds;
    if(shouldDecrementCY) {
      emc.putCY(--cy);
      emc.putCX(0); //default for testing getLongLineBoundaries so old cx does not mismatch ca with new cy
      longBounds = emc.calcLongLineBoundaries(editorContent, newlineIndices, 1, false);
      int numberOfTrailingCharsAtEndOfLongLine = longBounds[1] - longBounds[1] % canvasWidth;
      int numberOfShortLinesInLongLine = numberOfTrailingCharsAtEndOfLongLine > 0 ? (int)Math.ceil(longBounds[1] / (numberOfTrailingCharsAtEndOfLongLine)) : 1;
      cx = virtualCX < longBounds[1] ? virtualCX * numberOfShortLinesInLongLine : longBounds[1];
    } else if(!isAtBeginningOfEditorContent) {
      cx = cx < canvasWidth ? 0 : cx - canvasWidth;
    }
    emc.putCX(cx);
    int ca = emc.getCA();
    return ca;
  }

  @Override
  public String getName() {
    return "character";
  }

}
