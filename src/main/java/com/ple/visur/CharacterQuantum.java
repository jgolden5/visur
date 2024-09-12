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
          destinationCA = moveRight(span);
        }
        mv.dx--;
      } else {
        boolean canMoveLeft = checkCanMoveLeft(realCA);
        if(canMoveLeft) {
          destinationCA = moveLeft();
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

  private int moveDown(ArrayList<Integer> nextLineIndices, int span, int canvasWidth) {
    //logic here
    return emc.getCA();
  }

  private int moveUp(ArrayList<Integer> nextLineIndices) {
    //logic here
    return emc.getCA();
  }

  @Override
  public String getName() {
    return "character";
  }

}
