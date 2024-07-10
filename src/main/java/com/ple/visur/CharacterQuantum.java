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
    int canvasWidth = emc.getCanvasWidth();
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
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    int destination = ca + 1;

    emc.putCA(destination);
    int cx = emc.getLongCX();
    int canvasWidth = emc.getCanvasWidth();
    emc.putVirtualCX(cx % canvasWidth);

    return destination;
  }

  private int moveLeft() {
    int ca = emc.getCA();
    if(ca > 0) {
      emc.putCA(--ca);
    }
    int cx = emc.getLongCX();
    int canvasWidth = emc.getCanvasWidth();
    emc.putVirtualCX(cx % canvasWidth);

    return ca;
  }

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices, int canvasWidth) {
    int shortCX = emc.getShortCX();
    int shortCY = emc.getShortCY();
    emc.putShortCY(++shortCY);
    int[] longBounds = emc.calcLongLineBoundaries(editorContent, newlineIndices, emc.getSpan(), false);
    emc.putShortCX(Math.min(shortCX, longBounds[1] % canvasWidth));
    int ca = emc.getCA();
    return ca;
  }

  private int moveUp(String editorContent, ArrayList<Integer> newlineIndices) {
    int shortCX = emc.getShortCX();
    int shortCY = emc.getShortCY();
    emc.putShortCY(--shortCY);
    int[] longBounds = emc.calcLongLineBoundaries(editorContent, newlineIndices, emc.getSpan(), false);
    emc.putShortCX(Math.min(shortCX, longBounds[1]));
    int ca = emc.getCA();
    return ca;
  }

  @Override
  public String getName() {
    return "character";
  }

}
