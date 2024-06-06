package com.ple.visur;

import java.util.ArrayList;

public class DocumentQuantum extends Quantum {
  @Override
  int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    return new int[]{0, editorContent.length()};
  }

  @Override
  int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m, int[] bounds) {
    BrickVisurVar caBVV = (BrickVisurVar) ServiceHolder.editorModelCoupler.getGlobalVar("ca");
    return (int)caBVV.getVal();
  }

  @Override
  String getName() {
    return "document";
  }

}
