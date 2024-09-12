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
  public int moveIfPossible(String editorContent, ArrayList<Integer> nextLineIndices, MovementVector mv) {
    int canvasWidth = emc.getCanvasWidth();
    int realCA = emc.getCA();
    int span = emc.getSpan();
    if(mv.dx != 0) {
      realCA = moveRightOrLeftIfPossible(realCA, mv, nextLineIndices, span);
    }
    if(mv.dy != 0) {
      realCA = moveDownOrUpIfPossible(realCA, mv, nextLineIndices, span, canvasWidth);
    }
    return realCA;
  }

  private int moveRightOrLeftIfPossible(int realCA, MovementVector mv, ArrayList<Integer> nextLineIndices, int span) {
    int destinationCA = realCA;
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        boolean canMoveRight = checkCanMoveRight(realCA, nextLineIndices, span);
        if(canMoveRight) {
          destinationCA = moveRight();
        }
        mv.dx--;
      } else {
        boolean canMoveLeft = checkCanMoveLeft(realCA);
        if(canMoveLeft) {
          destinationCA = moveLeft(span);
        }
        mv.dx++;
      }
    }
    return destinationCA;
  }

  private int moveDownOrUpIfPossible(int realCA, MovementVector mv, ArrayList<Integer> nextLineIndices, int span, int canvasWidth) {
    int destinationCA = realCA;
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        boolean canMoveDown = checkCanMoveDown(nextLineIndices, span, canvasWidth);
        if(canMoveDown) {
          destinationCA = moveDown(nextLineIndices, span, canvasWidth);
        }
        mv.dy--;
      } else {
        boolean canMoveUp = checkCanMoveUp();
        if(canMoveUp) {
          destinationCA = moveUp(nextLineIndices);
        }
        mv.dy++;
      }
    }
    return destinationCA;
  }

  private boolean checkCanMoveRight(int realCA, ArrayList<Integer> nextLineIndices, int span) {
    int editorContentEnd = nextLineIndices.get(nextLineIndices.size() - 1);
    boolean canMoveRight;
    if(span > 0) {
      canMoveRight = realCA + 1 < editorContentEnd;
    } else {
      canMoveRight = realCA + 1 <= editorContentEnd;
    }
    return canMoveRight;
  }

  private boolean checkCanMoveLeft(int realCA) {
    return realCA > 0;
  }

  private boolean checkCanMoveDown(ArrayList<Integer> nextLineIndices, int span, int canvasWidth) {
    boolean canMoveDown = true;
    int rcx = emc.getCX();
    int cy = emc.getCY();
    if(cy >= nextLineIndices.size() - 1) {
      int lastNewlineIndex = nextLineIndices.get(nextLineIndices.size() - 1);
      int lastValidCX = span > 0 ? lastNewlineIndex - 1 : lastNewlineIndex;
      if(rcx + canvasWidth > lastValidCX) {
        canMoveDown = false;
      }
    }
    return canMoveDown;
  }

  private boolean checkCanMoveUp() {
    return false;
  }

  private int moveRight() {
    int rcx = emc.getCX();
    int ll = emc.getLL();
    if(rcx + 1 > ll) {
      int cy = emc.getCY();
      emc.putCY(++cy);
      emc.putCX(0);
    } else {
      emc.putCX(++rcx);
    }
    return emc.getCA();
  }

  private int moveLeft(int span) {
    int rcx = emc.getCX();
    if(rcx > 0) {
      emc.putCX(--rcx);
    } else {
      int cy = emc.getCY();
      emc.putCY(--cy);
      int lastValidCharOfPreviousLine = span < 1 ? emc.getLL() : emc.getLL() - 1;
      emc.putCX(lastValidCharOfPreviousLine);
    }
    return emc.getCA();
  }

  private int moveDown(ArrayList<Integer> nextLineIndices, int span, int canvasWidth) {
    int vcx = emc.getVCX();
    int cy = emc.getCY();
    int ll = emc.getLL();
    int startOfLastShortInLong = ll > canvasWidth ? ll - (ll % canvasWidth) : 0;
    boolean shouldIncrementCY = vcx >= startOfLastShortInLong;
    if(shouldIncrementCY) {
      cy++;
      vcx = vcx % canvasWidth;
    } else {
      vcx += canvasWidth;
    }
    emc.putVCX(vcx);
    emc.putCY(cy);
    return emc.getCA();
  }

  private int moveUp(ArrayList<Integer> nextLineIndices) {

    int ca = emc.getCA();
    return ca;
  }

  @Override
  public String getName() {
    return "character";
  }

}
