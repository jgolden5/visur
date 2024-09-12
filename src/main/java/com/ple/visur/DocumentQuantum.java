package com.ple.visur;

import java.util.ArrayList;

public class DocumentQuantum extends Quantum {
  @Override
  int[] getBoundaries(int realCA, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int lowerBound = 0;
    int lastNewlineIndex = newlineIndices.get(newlineIndices.size() - 1);
    int upperBound = lastNewlineIndex;
    if(lowerBound == upperBound) {
      emc.putSpan(0);
    }
    if(span > 0) {
      return new int[]{0, lastNewlineIndex};
    } else {
      if(realCA == lastNewlineIndex) {
        return new int[]{realCA, realCA};
      } else {
        emc.putCA(0);
        return new int[]{0, 0};
      }
    }
  }

  @Override
  int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m) {
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
