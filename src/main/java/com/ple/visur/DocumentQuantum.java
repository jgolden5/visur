package com.ple.visur;

import java.util.ArrayList;

public class DocumentQuantum extends Quantum {
  @Override
  int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int lowerBound = 0;
    int upperBound = editorContent.length();
    if(lowerBound == upperBound) {
      emc.putSpan(0);
    }
    if(span > 0) {
      return new int[]{0, editorContent.length()};
    } else {
      BrickVisurVar realCABVV = (BrickVisurVar) emc.getGlobalVar("realCA");
      int realCA = (int)realCABVV.getVal();
      if(realCA == editorContent.length()) {
        return new int[]{realCA, realCA};
      } else {
        emc.putRealCA(0);
        return new int[]{0, 0};
      }
    }
  }

  @Override
  int moveIfPossible(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int span = emc.getSpan();
    BrickVisurVar realCABVV = (BrickVisurVar) emc.getGlobalVar("realCA");
    int realCA = (int)realCABVV.getVal();
    if(span == 0) {
      if(realCA < editorContent.length() && m.dx > 0) {
        realCA = editorContent.length();
      } else if(realCA == editorContent.length() && m.dx < 0) {
        realCA = 0;
      }
    }
    return realCA;
  }

  @Override
  String getName() {
    return "document";
  }

}
