package com.ple.visur;

import java.util.ArrayList;

public class CharacterQuantum extends Quantum {
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
  @Override
  public int[] getBoundaries(int ca, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    int lastNewlineIndex = newlineIndices.get(newlineIndices.size() - 1);
    if(ca < lastNewlineIndex) {
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
    realCA = moveDownOrUpIfPossible(realCA, mv, newlineIndices, span, canvasWidth);
    return realCA;
  }

  private int moveRightOrLeftIfPossible(int realCA, MovementVector mv, ArrayList<Integer> newlineIndices, int span) {
    int destinationCA = realCA;
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        boolean canMoveRight = checkCanMoveRight(realCA, newlineIndices, span);
        if(canMoveRight) {
          destinationCA = moveRight();
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

  private int moveDownOrUpIfPossible(int realCA, MovementVector mv, ArrayList<Integer> newlineIndices, int span, int canvasWidth) {
    int destinationCA = realCA;
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        boolean canMoveDown = checkCanMoveDown(newlineIndices, span, canvasWidth);
        if(canMoveDown) {
          destinationCA = moveDown(newlineIndices, span, canvasWidth);
        }
        mv.dy--;
      } else {
        boolean canMoveUp = checkCanMoveUp();
        if(canMoveUp) {
          destinationCA = moveUp(newlineIndices);
        }
        mv.dy++;
      }
    }
    return destinationCA;
  }

  private boolean checkCanMoveRight(int realCA, ArrayList<Integer> newlineIndices, int span) {
    int editorContentEnd = newlineIndices.get(newlineIndices.size() - 1);
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

  private boolean checkCanMoveDown(ArrayList<Integer> newlineIndices, int span, int canvasWidth) {
    boolean canMoveDown = true;
    int realLongCX = emc.getRealLongCX();
    int realLongCY = emc.getRealLongCY();
    if(realLongCY >= newlineIndices.size() - 1) {
      int lastNewlineIndex = newlineIndices.get(newlineIndices.size() - 1);
      int lastValidCX = span > 0 ? lastNewlineIndex - 1 : lastNewlineIndex;
      if(realLongCX + canvasWidth > lastValidCX) {
        canMoveDown = false;
      }
    }
    return canMoveDown;
  }

  private boolean checkCanMoveUp() {
    return emc.getVirtualShortCY() > 0;
  }

  private int moveRight() {
    int realCA = emc.getRealCA();
    emc.putVirtualCA(++realCA);
    return realCA;
  }

  private int moveLeft() {
    int realCA = emc.getRealCA();
    emc.putVirtualCA(--realCA);
    return realCA;
  }

  private int moveDown(ArrayList<Integer> newlineIndices, int span, int canvasWidth) {
    
    //1 get virtualLongCY
    int virtualLongCY = emc.getVirtualLongCY();
    //2 set currentLongLineEnd = newlineIndices.get(virtualLongCY)
    int currentLongLineEnd = newlineIndices.get(virtualLongCY);
    //3 get virtualShortCY
    int virtualShortCY = emc.getVirtualShortCY();
    //4 increment virtualShortCY
    virtualShortCY++;
    //5 set incremented virtualShortCY to globalVar (test with emc.getVirtualShortCX)
    emc.putVirtualShortCY(virtualShortCY);
    //6 if new virtualLongCX > currentLongLineEnd, set virtualLongCX to currentNewlineIndexLimit
    int virtualLongCX = emc.getVirtualLongCX();
    if(virtualLongCX > currentLongLineEnd) {
      emc.putVirtualLongCX(currentLongLineEnd);
    }
    //7 get calculated virtualLongCY from globalVar
    //8 set currentLineStart = virtualLongCY > 0 ? newlineIndices.get(virtualLongCY) : 0
    //9 set currentLongLineEnd = virtualLongCY > 0 ? newlineIndices.get(virtualLongCY) : 0
    //10 set lengthOfLongLineBounds = currentLongLineEnd - currentLineStart
    //11 realShortCX = getRealShortCXFromVirtualShortCX(newlineIndices, lengthOfLongLineBounds, span, canvasWidth)
    //12 set realShortCX to globalVar
    //13 set realShortCY to virtualShortCY
    //14 return calculated realCA from globalVar
    return emc.getRealCA();
  }

  private int getRealShortCXFromVirtualShortCX(ArrayList<Integer> newlineIndices, int lengthOfLongLineBounds, int span, int canvasWidth) {
    int virtualLongCX = emc.getVirtualLongCX();
    int virtualLongCY = emc.getVirtualLongCY();
    int realLongCX = Math.min(virtualLongCX, lengthOfLongLineBounds);
    int realShortCX = realLongCX % canvasWidth;
    if(span > 0 && virtualLongCY >= newlineIndices.size() - 1) {
      realShortCX--;
    }
    return realShortCX;
  }

  private int moveUp(ArrayList<Integer> newlineIndices) {
    int shortCX = emc.getRealShortCX();
    int shortCY = emc.getRealShortCY();
    emc.putRealShortCY(--shortCY);
//    int[] longBounds = emc.calcLongLineBoundaries(newlineIndices, emc.getSpan(), false);
//    emc.putRealShortCX(Math.min(shortCX, longBounds[1]));
    int ca = emc.getRealCA();
    return ca;
  }

  @Override
  public String getName() {
    return "character";
  }

}
