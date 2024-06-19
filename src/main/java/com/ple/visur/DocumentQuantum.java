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
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int span = emc.getSpan();
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    if(span == 0) {
      if(ca == 0 && m.dx > 0) {
        ca = editorContent.length();
      } else if(ca == editorContent.length() && m.dx < 0) {
        ca = 0;
      }
    }
    return ca;
  }

  @Override
  String getName() {
    return "document";
  }

}
