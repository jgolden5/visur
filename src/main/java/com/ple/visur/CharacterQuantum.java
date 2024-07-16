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
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv) {
    int canvasWidth = emc.getCanvasWidth();
    int destination = emc.getRealCA();
    int span = emc.getSpan();
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        destination = moveRight(newlineIndices, span);
        mv.dx--;
      } else {
        destination = moveLeft();
        mv.dx++;
      }
    }
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        destination = moveDown(editorContent, newlineIndices, canvasWidth);
        mv.dy--;
      } else {
        destination = moveUp(editorContent, newlineIndices);
        mv.dy++;
      }
    }
    return destination;
  }

  private int moveRight(ArrayList<Integer> newlineIndices, int span) {
    int realCA = emc.getRealCA();
    int editorContentEnd = newlineIndices.get(newlineIndices.size() - 1);
    boolean canMoveRight = span > 0 ? realCA + 1 < editorContentEnd : realCA + 1 <= editorContentEnd;
    if(canMoveRight) {
      realCA++;
      emc.putVirtualCA(realCA);
    }
    return realCA;
  }

  private int moveLeft() {
    int realCA = emc.getRealCA();
    boolean canMoveLeft = realCA - 1 >= 0;
    if(canMoveLeft) {
      realCA--;
      emc.putVirtualCA(realCA);
    }
    return realCA;
  }

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices, int canvasWidth) {
//    int virtualShortCY = emc.getVirtualShortCY();
//    emc.putVirtualShortCX(0);
//    emc.putVirtualShortCY(++virtualShortCY);
//    int virtualCAAfterMovement = emc.getVirtualCA();
//    int span = emc.getSpan();
//    int lastNewlineIndex = newlineIndices.get(newlineIndices.size() - 1);
//    int contentEnd = span > 0 ? lastNewlineIndex - 1 : lastNewlineIndex;
//    boolean canGoDown = virtualCAAfterMovement <= contentEnd;
//    if(canGoDown) {
//      int[] longBounds = emc.calcLongLineBoundaries(editorContent, newlineIndices, span, false);
//      emc.putRealShortCX(Math.min(emc.getRealShortCX(), longBounds[1] % canvasWidth));
//      emc.putRealShortCY(emc.getVirtualShortCY());
//    }
    return emc.getRealCA();
  }

  private int moveUp(String editorContent, ArrayList<Integer> newlineIndices) {
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
