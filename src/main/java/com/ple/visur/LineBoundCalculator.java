package com.ple.visur;

import java.util.ArrayList;

public class LineBoundCalculator {

  public static int getLongLineLength(int cy, ArrayList<Integer> ni) {
    int longLineLength = 0;
    if(cy < ni.size()) {
      if(cy > 0) {
        longLineLength = ni.get(cy) - ni.get(cy - 1);
      } else {
        longLineLength = ni.get(cy);
      }
    } else {
      System.out.println("cy value is invalid");;
    }
    return longLineLength;
  }

  public static int[] getShort(int cx, int cy, ArrayList<Integer> ni) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int[] shortBounds = new int[2];
    int canvasWidth = emc.getCanvasWidth();
    shortBounds[0] = getShortLineStart(canvasWidth, cx);
    shortBounds[1] = getShortLineEnd(shortBounds[0], canvasWidth, cx, cy, ni);
    return shortBounds;
  }

  private static int getShortLineStart(int cw, int cx) {
    int shortLineStart = 0;
    for(int i = cx; i > 0; i--) {
      if (i % cw == 0) {
        shortLineStart = i;
        break;
      }
    }
    return shortLineStart;
  }

  private static int getShortLineEnd(int shortLineStart, int cw, int cx, int cy, ArrayList<Integer> ni) {
    final int longLineLength = getLongLineLength(cy, ni);
    int shortLineEnd = longLineLength;
    if(shortLineEnd != shortLineStart) {
      for (int i = cx + 1; i < longLineLength; i++) {
        if (i % cw == 0) {
          shortLineEnd = i;
          break;
        }
      }
    }
    return shortLineEnd;
  }

}
