package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum extends Quantum {
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    BrickVisurVar realCABVV = (BrickVisurVar) emc.getGlobalVar("realCA");
    int ca = (int)realCABVV.getVal();
    if(ca < editorContent.length()) {
      return new int[]{ca, ca + 1};
    } else {
      return new int[]{ca, ca};
    }
  }

  @Override
  public int moveIfPossible(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv) {
    int canvasWidth = emc.getCanvasWidth();
    int realCA = emc.getRealCA();
    int span = emc.getSpan();
    realCA = moveRightOrLeftIfPossible(realCA, mv, newlineIndices, span);
    realCA = moveDownOrUpIfPossible(realCA, mv, newlineIndices, span);
    return realCA;
  }

  private int moveRightOrLeftIfPossible(int realCA, MovementVector mv, ArrayList<Integer> newlineIndices, int span) {
    int destinationCA = realCA;
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        int editorContentEnd = newlineIndices.get(newlineIndices.size() - 1);
        boolean canMoveRight = span > 0 ? realCA + 1 < editorContentEnd : realCA + 1 <= editorContentEnd;
        if(canMoveRight) {
          destinationCA = moveRight(newlineIndices, span);
        }
        mv.dx--;
      } else {
        boolean canMoveLeft = realCA - 1 >= 0;
        if(canMoveLeft) {
          destinationCA = moveLeft();
        }
        mv.dx++;
      }
    }
    return destinationCA;
  }

  private int moveDownOrUpIfPossible(int realCA, MovementVector mv, ArrayList<Integer> newlineIndices, int span) {
    int destinationCA = realCA;
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        boolean canMoveDown;
        if(canMoveDown) {
          destinationCA = moveDown(newlineIndices);
        }
        mv.dy--;
      } else {
        boolean canMoveUp;
        if(canMoveUp) {
          destinationCA = moveUp(newlineIndices);
        }
        mv.dy++;
      }
    }
    return destinationCA;
  }

  private int moveRight(ArrayList<Integer> newlineIndices, int span) {
    int realCA = emc.getRealCA();
    emc.putVirtualCA(++realCA);
    return realCA;
  }

  private int moveLeft() {
    int realCA = emc.getRealCA();
    emc.putVirtualCA(--realCA);
    return realCA;
  }

  private int moveDown(ArrayList<Integer> newlineIndices, int canvasWidth) {
    int realCA;
    return realCA;
  }

  private int moveUp(ArrayList<Integer> newlineIndices) {
    int shortCX = emc.getRealShortCX();
    int shortCY = emc.getRealShortCY();
    emc.putRealShortCY(--shortCY);
    int[] longBounds = emc.calcLongLineBoundaries(editorContent, newlineIndices, emc.getSpan(), false);
    emc.putRealShortCX(Math.min(shortCX, longBounds[1]));
    int ca = emc.getRealCA();
    return ca;
  }

  @Override
  public String getName() {
    return "character";
  }

}
