package com.ple.visur;

import java.util.ArrayList;

public class LineQuantum extends Quantum {

  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public int[] getBoundaries(int realCA, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    int[] bounds = new int[2];
    int leftBound = realCA;
    int rightBound = realCA;
    if(span > 0) {
      leftBound = getQuantumStart(realCA);
      rightBound = getQuantumEnd(realCA);
    } else if(isInMiddleOfQuantum(leftBound)) {
      leftBound = getQuantumStart(leftBound);
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
  public int moveIfPossible(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv) {
    BrickVisurVar realCABVV = (BrickVisurVar)emc.getGlobalVar("realCA");
    int realCA = (int)realCABVV.getVal();
    int span = emc.getSpan();
    CharacterQuantum cq = new CharacterQuantum();
    if(span > 0) {
      mv.dy += mv.dx;
      mv.dx = 0;
    }
    while(mv.dy != 0) {
      realCA = cq.moveIfPossible(editorContent, newlineIndices, mv); //incrementing/decrementing dy happens internally here
    }
    while(mv.dx != 0) {
      if(mv.dx > 0) {
        realCA = zeroQuantumMoveRight(realCA, editorContent, newlineIndices);
        mv.dx--;
      } else {
        realCA = zeroQuantumMoveLeft(realCA, editorContent, newlineIndices);
        mv.dx++;
      }
      emc.putCA(realCA);
      emc.putVCX(emc.getRCX());
    }
    return realCA;
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
