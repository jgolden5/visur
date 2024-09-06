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
        destination = moveDown(editorContent, newlineIndices, span);
        mv.dy--;
      } else {
        destination = moveUp(editorContent, newlineIndices, span);
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
    emc.putVirtualCX(emc.getCX());

    return destination;
  }

  private int moveLeft() {
    int ca = emc.getCA();
    if(ca > 0) {
      emc.putCA(--ca);
    }
    emc.putVirtualCX(emc.getCX());

    return ca;
  }

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices, int span) {
    int cx = emc.getCX();
    int cy = emc.getCY();
    int canvasWidth = emc.getCanvasWidth();
    int[] shortBounds = RelativeLineBoundCalculator.getShort(cx, cy, newlineIndices);
    int vcx = emc.getVirtualCX();
    boolean isOnLastShortLineInLongLine = shortBounds[1] - shortBounds[0] < canvasWidth;
    boolean isAtEndOfEditorContent = cy == newlineIndices.size() - 1 && isOnLastShortLineInLongLine;
    boolean shouldIncrementCY = isOnLastShortLineInLongLine && !isAtEndOfEditorContent;
    if(shouldIncrementCY) {
      emc.putCY(++cy);
      vcx = vcx % canvasWidth;
    } else if(!isAtEndOfEditorContent) {
      vcx += canvasWidth;
    }
    int longLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, newlineIndices);
    int longLineLimit = longLineLength;
    cx = Math.min(vcx, longLineLimit);
    emc.putCX(cx);
    emc.putVirtualCX(vcx);
    int ca = emc.getCA();
    return span > 0 && ca == editorContent.length() ? --ca : ca;
  }

  private int moveUp(String editorContent, ArrayList<Integer> newlineIndices, int span) {
    int cx = emc.getCX();
    int cy = emc.getCY();
    int canvasWidth = emc.getCanvasWidth();
    int vcx = emc.getVirtualCX();
    boolean isOnFirstLongLine = cy == 0;
    boolean isOnFirstShortLine = cx < canvasWidth;
    boolean shouldDecrementCY = !isOnFirstLongLine && isOnFirstShortLine;
    if(shouldDecrementCY) {
      emc.putCY(--cy);
      int previousLongLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, newlineIndices);
      int[] shortBoundsOfLastShortInPreviousLine = RelativeLineBoundCalculator.getShort(previousLongLineLength, cy, newlineIndices);
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
