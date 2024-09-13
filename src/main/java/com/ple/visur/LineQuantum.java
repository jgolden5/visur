package com.ple.visur;

import java.util.ArrayList;

public class LineQuantum extends Quantum {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public int[] getBoundaries(int ca, ArrayList<Integer> nl, int span, boolean includeTail) {
    int[] bounds = new int[]{ca, ca};
    int cy = emc.getCY();
    bounds[0] = cy > 0 ? nl.get(cy - 1) : 0;;
    if(span > 0) {
      bounds[1] = cy == nl.size() - 1 ? nl.get(cy) : nl.get(cy) - 1;;
    } else if(isInMiddleOfQuantum(ca)) {
      bounds[1] = bounds[0];
    } else {
      bounds[0] = ca;
      bounds[1] = ca;
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

  private int moveDown(ArrayList<Integer> nl) {
    int cy = emc.getCY();
    if(cy < nl.size() - 1) {
      emc.putCY(++cy);
      updateXValuesAfterVerticalMovement(cy, nl);
    }
    int ca = emc.getCA();
    int canvasEnd = emc.getCanvasEnd();
    if(ca > canvasEnd) {
      int lineStart = cy > 0 ? nl.get(cy - 1) : 0;
      int lineEnd = cy < nl.size() - 1 ? nl.get(cy) - 1 : nl.get(cy);
      double lineLength = lineEnd - lineStart;
      double canvasWidth = emc.getCanvasWidth();
      int numberOfTimesToIncrementCanvasStart = (int)Math.ceil(lineLength / canvasWidth); //equals number of short lines in current long
      if(numberOfTimesToIncrementCanvasStart == 0) numberOfTimesToIncrementCanvasStart = 1;
      while(numberOfTimesToIncrementCanvasStart > 0) {
        emc.incrementCanvasStart();
        numberOfTimesToIncrementCanvasStart--;
      }
    }
    return ca;
  }

  private int moveUp(ArrayList<Integer> nl) {
    int cy = emc.getCY();
    if(cy > 0) {
      emc.putCY(--cy);
      updateXValuesAfterVerticalMovement(cy, nl);
    }
    int ca = emc.getCA();
    int canvasStart = emc.getCanvasStart();
    if(ca < canvasStart) {
      int prevLineStart = cy > 0 ? nl.get(cy - 1) : 0;
      int relevantPrevLineEnd = canvasStart - 1;
      double prevLineLength = relevantPrevLineEnd - prevLineStart;
      double canvasWidth = emc.getCanvasWidth();
      int numberOfTimesToDecrementCanvasStart = (int)Math.ceil(prevLineLength / canvasWidth); //equals number of short lines in previous long
      if(numberOfTimesToDecrementCanvasStart == 0) numberOfTimesToDecrementCanvasStart = 1;
      while(numberOfTimesToDecrementCanvasStart > 0) {
        emc.decrementCanvasStart();
        numberOfTimesToDecrementCanvasStart--;
      }
    }
    return ca;
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

  private void updateXValuesAfterVerticalMovement(int cy, ArrayList<Integer> nl) {
    int newLongLineLength = RelativeLineBoundCalculator.getLongLineLength(cy, nl);
    int vcx = emc.getVirtualCX();
    int lineEndLimit = cy == nl.size() - 1 ? newLongLineLength : newLongLineLength - 1;
    int cx = Math.min(vcx, lineEndLimit);
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
