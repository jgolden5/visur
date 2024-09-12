package com.ple.visur;

import java.util.ArrayList;

public class LineQuantum extends Quantum {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public int[] getBoundaries(int ca, ArrayList<Integer> nl, int span, boolean includeTail) {
    int[] bounds = new int[]{ca, ca};
    int cy = emc.getCY();
    if(span > 0) {
      bounds[0] = cy > 0 ? nl.get(cy - 1) : 0;;
      bounds[1] = cy == nl.size() - 1 ? nl.get(cy) : nl.get(cy) - 1;;
    }
    return bounds;
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv) {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    int span = emc.getSpan();
    if(span > 0) {
      mv.dy += mv.dx;
      mv.dx = 0;
    }
    while(mv.dy != 0) {
      if(mv.dy > 0) {
        ca = moveDown(newlineIndices);
        mv.dy--;
      } else {
        ca = moveUp(newlineIndices);
        mv.dy++;
      }
    }
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        ca = zeroQuantumMoveRight(ca, editorContent, newlineIndices);
        mv.dx--;
      } else {
        ca = zeroQuantumMoveLeft(ca, newlineIndices);
        mv.dx++;
      }
      emc.putCA(ca);
      emc.putVirtualCX(emc.getCX());
    }
    return ca;
  }

  private int moveDown(ArrayList<Integer> newlineIndices) {
    int cy = emc.getCY();
    if(cy < newlineIndices.size() - 1) {
      emc.putCY(++cy);
      updateXValuesAfterVerticalMovement(cy, newlineIndices);
    }
    return emc.getCA();
  }

  private int moveUp(ArrayList<Integer> newlineIndices) {
    int cy = emc.getCY();
    if(cy > 0) {
      emc.putCY(--cy);
      updateXValuesAfterVerticalMovement(cy, newlineIndices);
    }
    return emc.getCA();
  }

  private void updateXValuesAfterVerticalMovement(int cy, ArrayList<Integer> newlineIndices) {
    int newLongLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, newlineIndices);
    int vcx = emc.getVirtualCX();
    int cx = Math.min(vcx, newLongLineLength - 1);
    emc.putCX(cx);
  }

  private int zeroQuantumMoveRight(int ca, String editorContent, ArrayList<Integer> nl) {
    int destination = ca;
    boolean startingCharIsNewline = editorContent.charAt(ca) == '\n';
    if (startingCharIsNewline) {
      destination++;
    } else {
      int cy = emc.getCY();
      destination = cy == nl.size() - 1 ? nl.get(cy) : nl.get(cy) - 1;
    }
    return destination;
  }

  private int zeroQuantumMoveLeft(int ca, ArrayList<Integer> nl) {
    int destination = ca;
    if(ca > 0) {
      int cy = emc.getCY();
      boolean previousCharIsNewline = nl.contains(ca) && ca < nl.get(nl.size() - 1);
      if(previousCharIsNewline) {
        destination--;
      } else {
        destination = cy > 0 ? nl.get(cy - 1) : 0;
      }
    }
    return destination;
  }

  @Override
  public String getName() {
    return "line";
  }

}
