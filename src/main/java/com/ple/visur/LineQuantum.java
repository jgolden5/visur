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
      bounds[1] = nl.get(cy) - 1;;
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
        ca = zeroQuantumMoveLeft(ca, editorContent, newlineIndices);
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
    int vcx = emc.getVirtualCX();
    int newLongLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, newlineIndices);
    int cx = Math.min(vcx, newLongLineLength);
    emc.putCX(cx);
  }

  private int zeroQuantumMoveRight(int ca, String editorContent, ArrayList<Integer> newlineIndices) {
    int destination = ca;
    if(ca < editorContent.length()) {
      boolean startingCharIsNewline = editorContent.charAt(ca) == '\n';
      if (startingCharIsNewline) {
        destination++;
      } else if (destination < editorContent.length()) {
        for (int i = 0; i < newlineIndices.size(); i++) {
          if (ca < newlineIndices.get(i)) {
            destination = newlineIndices.get(i);
            break;
          }
        }
        if (destination == ca) {
          destination = editorContent.length();
        }
      }
    }
    return destination;
  }

  private int zeroQuantumMoveLeft(int ca, String editorContent, ArrayList<Integer> newlineIndices) {
    int destination = ca;
    if(ca > 0) {
      boolean previousCharIsNewline = editorContent.charAt(ca - 1) == '\n';
      if(previousCharIsNewline) {
        destination--;
      } else {
        for (int i = newlineIndices.size() - 1; i >= 0; i--) {
          if(ca > newlineIndices.get(i)) {
            destination = newlineIndices.get(i) + 1;
            break;
          }
        }
        if(destination == ca) {
          destination = 0;
        }
      }
    }
    return destination;
  }

  @Override
  public String getName() {
    return "line";
  }

}
