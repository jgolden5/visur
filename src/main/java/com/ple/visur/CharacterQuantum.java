package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum extends Quantum {
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
  @Override
  public int[] getBoundaries(int ca, ArrayList<Integer> nextLineIndices, int span, boolean includeTail) {
    int lastNewlineIndex = nextLineIndices.get(nextLineIndices.size() - 1);
    if(ca < lastNewlineIndex) {
      return new int[]{ca, ca + 1};
    } else {
      return new int[]{ca, ca};
    }
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> nextLineIndices, MovementVector mv) {
    int realCA = emc.getCA();
    int span = emc.getSpan();
    if(mv.dx != 0) {
      if(mv.dx > 0) {
        realCA = moveRight(span);
      } else {
        realCA = moveLeft();
      }
    }
    if(mv.dy != 0) {
      if(mv.dy > 0) {
        realCA = moveDown(editorContent, nextLineIndices, span);
      } else {
        realCA = moveUp(editorContent, nextLineIndices, span);
      }
    }
    return realCA;
  }

  private int moveRight(int span) {
    int ca = emc.getCA();
    ArrayList<Integer> nl = emc.getNextLineIndices();
    int contentEndLimit = span > 0 ? nl.get(nl.size() - 1) - 1 : nl.get(nl.size() - 1);
    if(ca < contentEndLimit) {
      ca++;
    }
    return ca;
  }

  private int moveLeft() {
    int ca = emc.getCA();
    if(ca > 0) {
      ca--;
    }
    return ca;
  }

  private int moveDown(String editorContent, ArrayList<Integer> nextLineIndices, int span) {
    int cx = emc.getCX();
    int cy = emc.getCY();
    int canvasWidth = emc.getCanvasWidth();
    int[] shortBounds = RelativeLineBoundCalculator.getShort(cx, cy, nextLineIndices);
    int vcx = emc.getVirtualCX();
    boolean isOnLastShortLineInLongLine = shortBounds[1] - shortBounds[0] < canvasWidth;
    boolean isAtEndOfEditorContent = cy == nextLineIndices.size() - 1 && isOnLastShortLineInLongLine;
    boolean shouldIncrementCY = isOnLastShortLineInLongLine && !isAtEndOfEditorContent;
    if (shouldIncrementCY) {
      emc.putCY(++cy);
      vcx = vcx % canvasWidth;

    } else if (!isAtEndOfEditorContent) {
      vcx += canvasWidth;
    }
    int longLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, nextLineIndices);
    int longLineLimit = longLineLength;
    cx = Math.min(vcx, longLineLimit);
    emc.putCX(cx);
    emc.putVirtualCX(vcx);
    int ca = emc.getCA();
    return span > 0 && ca == editorContent.length() ? --ca : ca;
  }

  private int moveUp(String editorContent, ArrayList<Integer> nextLineIndices, int span) {
    int cx = emc.getCX();
    int cy = emc.getCY();
    int canvasWidth = emc.getCanvasWidth();
    int vcx = emc.getVirtualCX();
    boolean isOnFirstLongLine = cy == 0;
    boolean isOnFirstShortLine = cx < canvasWidth;
    boolean shouldDecrementCY = !isOnFirstLongLine && isOnFirstShortLine;
    if(shouldDecrementCY) {
      emc.putCY(--cy);
      int previousLongLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, nextLineIndices);
      int[] shortBoundsOfLastShortInPreviousLine = RelativeLineBoundCalculator.getShort(previousLongLineLength, cy, nextLineIndices);
      vcx = vcx % canvasWidth + shortBoundsOfLastShortInPreviousLine[0];
      cx = Math.min(vcx, previousLongLineLength);
    } else if(!isOnFirstShortLine) {
      vcx -= canvasWidth;
      cx = vcx;
    }
    emc.putCX(cx);
    emc.putVirtualCX(vcx);
    int ca = emc.getCA();
    return span > 0 && ca == editorContent.length() ? --ca : ca;
  }

  @Override
  public String getName() {
    return "character";
  }

}
