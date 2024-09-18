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
  public int move(String editorContent, ArrayList<Integer> nl, MovementVector mv) {
    int ca = emc.getCA();
    int span = emc.getSpan();
    if(mv.dx != 0) {
      if(mv.dx > 0) {
        ca = moveRight(nl, span);
      } else {
        ca = moveLeft();
      }
    }
    if(mv.dy != 0) {
      if(mv.dy > 0) {
        ca = moveDown(nl, span);
      } else {
        ca = moveUp(nl);
      }
    }
    return ca;
  }

  private int moveRight(ArrayList<Integer> nl, int span) {
    int ca = emc.getCA();
    int contentEnd = nl.get(nl.size() - 1);
    int endLimit = span > 0 ? contentEnd - 1 : contentEnd;
    if(ca + 1 <= endLimit) {
      emc.putCA(++ca);
      emc.putVirtualCX(emc.getCX());
    }
    emc.checkThenScrollDownSingleLine(ca);
    return ca;
  }

  private int moveLeft() {
    int ca = emc.getCA();
    if(ca > 0) {
      emc.putCA(--ca);
      emc.putVirtualCX(emc.getCX());
    }
    emc.checkThenScrollUpSingleLine(ca);
    return ca;
  }

  private int moveDown(ArrayList<Integer> nl, int span) {
    int cx = emc.getCX();
    int vcx = emc.getVirtualCX();
    int cy = emc.getCY();
    int canvasWidth = emc.getCanvasWidth();
    int[] shortBounds = RelativeLineBoundCalculator.getShort(cx, cy, nl);
    boolean isOnLastShortLineInLongLine = shortBounds[1] - shortBounds[0] < canvasWidth;
    boolean isAtEndOfEditorContent = cy == nl.size() - 1 && isOnLastShortLineInLongLine;
    boolean shouldIncrementCY = isOnLastShortLineInLongLine && !isAtEndOfEditorContent;
    if (shouldIncrementCY) {
      emc.putCY(++cy);
      vcx = vcx % canvasWidth;
    } else if (!isAtEndOfEditorContent) {
      vcx += canvasWidth;
    }
    int longLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, nl);
    int longLineLimit = longLineLength > 0 ? longLineLength - 1 : 0;
    cx = Math.min(vcx, longLineLimit);
    emc.putCX(cx);
    emc.putVirtualCX(vcx);
    int ca = emc.getCA();
    emc.checkThenScrollDownSingleLine(ca);
    return ca;
  }

  private int moveUp(ArrayList<Integer> nextLineIndices) {
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
      cx = Math.min(vcx, previousLongLineLength - 1);
    } else if(!isOnFirstShortLine) {
      vcx -= canvasWidth;
      cx = vcx;
    }
    emc.putCX(cx);
    emc.putVirtualCX(vcx);
    int ca = emc.getCA();
    if(ca < emc.getCanvasStart()) {
      emc.decrementCanvasStart();
    }
    return ca;
  }

  @Override
  public String getName() {
    return "character";
  }

}
