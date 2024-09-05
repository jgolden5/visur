package com.ple.visur;

import java.util.ArrayList;

public class LineBoundCalculator {

  public static int[] getLong(int cy, ArrayList<Integer> ni) {
    int[] longBounds = new int[2];
    longBounds[0] = cy > 0 ? ni.get(cy - 1) : 0;
    longBounds[1] = cy < ni.size() ? ni.get(cy) : ni.get(ni.size() - 1);
    return longBounds;
  }

}
