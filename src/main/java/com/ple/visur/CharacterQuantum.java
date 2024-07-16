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
        destination = moveDown(editorContent, newlineIndices, canvasWidth);
        mv.dy--;
      } else {
        destination = moveUp(editorContent, newlineIndices);
        mv.dy++;
      }
    }
    return destination;
  }

  private int moveRight() {
    BrickVisurVar realCABVV = (BrickVisurVar)emc.getGlobalVar("realCA");
    int realCA = (int)realCABVV.getVal();
    int destination = realCA + 1;

    emc.putRealCA(destination);
    int cx = emc.getRealLongCX();
    int canvasWidth = emc.getCanvasWidth();
    emc.putVirtualLongCX(cx % canvasWidth);

    return destination;
  }

  private int moveLeft() {
    int realCA = emc.getRealCA();
    if(realCA > 0) {
      emc.putRealCA(--realCA);
    }
    int cx = emc.getRealLongCX();
    int canvasWidth = emc.getCanvasWidth();
    emc.putVirtualLongCX(cx % canvasWidth);

    return realCA;
  }

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices, int canvasWidth) {
    int shortCX = emc.getRealShortCX();
    int shortCY = emc.getRealShortCY();
    emc.putRealShortCY(++shortCY);
    int[] longBounds = emc.calcLongLineBoundaries(editorContent, newlineIndices, emc.getSpan(), false);
    emc.putRealShortCX(Math.min(shortCX, longBounds[1] % canvasWidth));
    int ca = emc.getRealCA();
    return ca;
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
