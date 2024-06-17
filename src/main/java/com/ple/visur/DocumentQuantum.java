package com.ple.visur;

import java.util.ArrayList;

public class DocumentQuantum extends Quantum {
  @Override
  int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    int lowerBound = 0;
    int upperBound = editorContent.length();
    if(lowerBound == upperBound) {
      ServiceHolder.editorModelCoupler.putSpan(0);
    }
    return new int[]{0, editorContent.length()};
  }

  @Override
  int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m) {
    BrickVisurVar caBVV = (BrickVisurVar) ServiceHolder.editorModelCoupler.getGlobalVar("ca");
    return (int)caBVV.getVal();
  }

  @Override
  String getName() {
    return "document";
  }

}
