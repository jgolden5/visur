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
        destination = moveDown(editorContent, newlineIndices);
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
    emc.putVirtualCX(emc.getCX());

    return destination;
  }

  private int moveLeft() {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int destination = (int)caBVV.getVal();
    if(destination > 0) {
      destination--;
    }

    String editorContent = emc.getEditorContent();
    if(editorContent.charAt(destination) == '\n') {
      emc.putCA(destination);
      emc.putVirtualCX(emc.getCX());
    } else {
      emc.putVirtualCX(emc.getCX() - 1);
    }

    return destination;
  }

  private int moveDown(String editorContent, ArrayList<Integer> newlineIndices) {
    int cy = emc.getCY();
    boolean canMoveDown;
    int span = emc.getSpan();
    if(span > 0) {
      boolean lastCharInContentIsNewline = editorContent.charAt(editorContent.length() - 1) == '\n';
      canMoveDown = lastCharInContentIsNewline ? cy < newlineIndices.size() - 1 : cy < newlineIndices.size();
    } else {
      canMoveDown = cy < newlineIndices.size();
    }
    if(canMoveDown) {
      cy++;
      emc.putCY(cy);
      boolean editorContentContainsNewlineChar = newlineIndices.size() > 0;
      int lineStartBound, lineEndBound;
      if(editorContentContainsNewlineChar) {
        lineStartBound = cy > 0 ? newlineIndices.get(cy - 1) + 1 : 0;
        if(cy < newlineIndices.size()) {
          lineEndBound = newlineIndices.get(cy);
        } else {
          lineEndBound = editorContent.length();
        }
      } else {
        lineStartBound = 0;
        lineEndBound = editorContent.length();
      }
      int currentLineLength = lineEndBound - lineStartBound;
      int cx;
      int virtualCX = emc.getVirtualCX();
      boolean virtualCXIsTooLong;
      virtualCXIsTooLong = virtualCX > currentLineLength;
      if(virtualCXIsTooLong) {
        cx = currentLineLength;
      } else {
        cx = virtualCX;
      }
      emc.putCX(cx);
    }
    int ca = emc.getCA();
    return ca;
  }

  private int moveUp(String editorContent, ArrayList<Integer> newlineIndices) {
    BrickVisurVar cyBVV = (BrickVisurVar)emc.getGlobalVar("cy");
    Integer cy = (Integer)cyBVV.getVal();
    boolean canDecrementCY = cy > 0;
    if(canDecrementCY) {
      cy--;
      cyBVV.putVal(cy);
      boolean editorContentContainsNewlineChar = newlineIndices.size() > 0;
      int lineStartBound, lineEndBound;
      if(editorContentContainsNewlineChar) {
        lineStartBound = cy > 0 ? newlineIndices.get(cy - 1) + 1 : 0;
        lineEndBound = newlineIndices.get(cy);
      } else {
        lineStartBound = 0;
        lineEndBound = editorContent.length();
      }
      int currentLineLength = lineEndBound - lineStartBound;
      BrickVisurVar cxBVV = (BrickVisurVar) emc.getGlobalVar("cx");
      int cx;
      int virtualCX = emc.getVirtualCX();
      boolean virtualCXIsTooLong = virtualCX > currentLineLength;
      if(virtualCXIsTooLong) {
        cx = currentLineLength;
      } else {
        cx = virtualCX;
      }
      cxBVV.putVal(cx);
      emc.putGlobalVar("cx", cxBVV);
    }
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    return (int) caBVV.getVal();
  }

  @Override
  public String getName() {
    return "character";
  }

}
