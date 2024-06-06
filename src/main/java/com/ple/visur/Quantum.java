package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;

public abstract class Quantum implements Shareable {
  abstract int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail); //newlineIndices are only needed in CustomQuantums
  abstract int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m, int[] bounds);
  abstract String getName();
  void quantumStart() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String otherQuantumName = (String)eds.pop();
    String otherQuantumNameWithoutQuotes = otherQuantumName.substring(1, otherQuantumName.length() - 1);
    QuantumNameToQuantum quantumNameToQuantum = emc.getQuantumNameToQuantum();
    Quantum otherQuantum = quantumNameToQuantum.get(otherQuantumNameWithoutQuotes);
    int distanceOfCurrentQuantumBounds = getQuantumBoundsLength();
    int distanceOfOtherQuantumBounds = otherQuantum.getQuantumBoundsLength();
    Quantum longerQuantum;
    Quantum shorterQuantum;
    if(distanceOfCurrentQuantumBounds > distanceOfOtherQuantumBounds) {
      longerQuantum = this;
      shorterQuantum = otherQuantum;
    } else {
      longerQuantum = otherQuantum;
      shorterQuantum = this;
    }
    int[] longerBounds = longerQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), false);
    int ca = longerBounds[0];
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(ca);
    int[] newShorterBounds = shorterQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), false);
    emc.putQuantumStart(newShorterBounds[0]);
    emc.putQuantumEnd(newShorterBounds[1]);
    System.out.println("quantumStart called. CurrentQ = " + getName() + ". OtherQ = " + otherQuantumNameWithoutQuotes);
    System.out.println("longer quantum = " + longerQuantum.getName() + "; shorter quantum = " + shorterQuantum.getName());
    if(otherQuantum.getName().equals(shorterQuantum.getName())) {
      emc.putCurrentQuantum(otherQuantum);
      System.out.println("quantum changed from " + getName() + " to " + otherQuantum.getName());
    }
  }

  void quantumEnd() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String otherQuantumName = (String)eds.pop();
    String otherQuantumNameWithoutQuotes = otherQuantumName.substring(1, otherQuantumName.length() - 1);
    QuantumNameToQuantum quantumNameToQuantum = emc.getQuantumNameToQuantum();
    Quantum otherQuantum = quantumNameToQuantum.get(otherQuantumNameWithoutQuotes);
    int distanceOfCurrentQuantumBounds = getQuantumBoundsLength();
    int distanceOfOtherQuantumBounds = otherQuantum.getQuantumBoundsLength();
    Quantum longerQuantum;
    Quantum shorterQuantum;
    if(distanceOfCurrentQuantumBounds > distanceOfOtherQuantumBounds) {
      longerQuantum = this;
      shorterQuantum = otherQuantum;
    } else {
      longerQuantum = otherQuantum;
      shorterQuantum = this;
    }
    int[] longerBounds = longerQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), false);
    int ca = longerBounds[1] - 1;
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(ca);
    int[] newShorterBounds = shorterQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), false);
    emc.putQuantumStart(newShorterBounds[0]);
    emc.putQuantumEnd(newShorterBounds[1]);
    System.out.println("quantumStart called. CurrentQ = " + getName() + ". OtherQ = " + otherQuantumNameWithoutQuotes);
    System.out.println("longer quantum = " + longerQuantum.getName() + "; shorter quantum = " + shorterQuantum.getName());
    if(otherQuantum.getName().equals(shorterQuantum.getName())) {
      emc.putCurrentQuantum(otherQuantum);
      System.out.println("quantum changed from " + getName() + " to " + otherQuantum.getName());
    }
  }

  int getQuantumBoundsLength() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int[] bounds = getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), false);
    return bounds[1] - bounds[0];
  }

}
