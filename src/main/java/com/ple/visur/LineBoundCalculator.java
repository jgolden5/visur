package com.ple.visur;

import java.util.ArrayList;

public class LineBoundCalculator {

  public static int[] getLong(int cy, ArrayList<Integer> ni) {
    int[] longBounds = new int[2];
    longBounds[0] = cy > 0 ? ni.get(cy - 1) : 0;
    longBounds[1] = cy < ni.size() ? ni.get(cy) : ni.get(ni.size() - 1);
    return longBounds;
  }

  public static int[] getShort(int cx, int cy, ArrayList<Integer> ni) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int[] shortBounds = new int[2];
    int canvasWidth = emc.getCanvasWidth();
    shortBounds[0] = getShortLineStart(canvasWidth, cx);
    shortBounds[1] = getShortLineEnd(canvasWidth, cx, cy, ni);
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

  private static int getShortLineEnd(int cw, int cx, int cy, ArrayList<Integer> ni) {
    int currentLineLength = cy < ni.size() ? ni.get(cy) : ni.get(ni.size() - 1);
    int shortLineEnd = currentLineLength;
    for(int i = cx > 0 ? cx : cx + 1; i < currentLineLength; i++) {
      if(i % cw == 0 || i == currentLineLength) {
        shortLineEnd = i;
        break;
      }
    }
    return shortLineEnd;
  }

}
