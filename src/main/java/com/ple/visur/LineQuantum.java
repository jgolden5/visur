package com.ple.visur;

import java.util.ArrayList;

public class LineQuantum extends Quantum {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    int[] bounds = new int[2];
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    int leftBound = ca;
    int rightBound = ca;
    if(span > 0) {
      leftBound = getQuantumStart(ca);
      rightBound = getQuantumEnd(ca);
    } else if(isInMiddleOfQuantum(leftBound)) {
      leftBound = getQuantumStart(leftBound);
      emc.putVirtualCX(emc.getCX());
    }
    bounds[0] = leftBound;
    bounds[1] = rightBound;
    return bounds;
  }

  private boolean isInMiddleOfQuantum(int bound) {
    String editorContent = emc.getEditorContent();
    if(bound > 0 && bound < editorContent.length()) {
      boolean matchExistsBefore = editorContent.charAt(bound - 1) != '\n';
      boolean matchExistsAfter = editorContent.charAt(bound) != '\n';
      return matchExistsBefore && matchExistsAfter;
    } else {
      return false;
    }
  }

  private int getQuantumStart(int current) {
    int qStart = current;
    ArrayList<Integer> newlineIndices = emc.getNewlineIndices();
    if(newlineIndices.size() > 0) {
      for (int i = newlineIndices.size() - 1; i >= 0; i--) {
        if (current > newlineIndices.get(i)) {
          qStart = newlineIndices.get(i) + 1;
          break;
        } else if (i == 0) {
          qStart = 0;
        }
      }
    } else {
      qStart = 0;
    }
    return qStart;
  }

  private int getQuantumEnd(int current) {
    int qEnd = current;
    String editorContent = emc.getEditorContent();
    ArrayList<Integer> newlineIndices = emc.getNewlineIndices();
    if(newlineIndices.size() > 0) {
      for (int i = 0; i <= newlineIndices.size() - 1; i++) {
        if (current <= newlineIndices.get(i)) {
          qEnd = newlineIndices.get(i);
          break;
        } else if (i == newlineIndices.size() - 1) {
          qEnd = editorContent.length();
        }
      }
    } else {
      qEnd = editorContent.length();
    }
    return qEnd;
  }

  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv) {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    int span = emc.getSpan();
    CharacterQuantum cq = new CharacterQuantum();
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
