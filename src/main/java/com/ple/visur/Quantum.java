package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;

public abstract class Quantum implements Shareable {
  abstract int[] getBoundaries(int ca, ArrayList<Integer> newlineIndices, int span, boolean includeTail); //newlineIndices are only needed in CustomQuantums
  abstract int moveIfPossible(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m);
  abstract String getName();
  void quantumStart() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String otherQuantumName = (String)eds.pop();
    QuantumNameToQuantum quantumNameToQuantum = emc.getQuantumNameToQuantum();
    Quantum otherQuantum = quantumNameToQuantum.get(otherQuantumName);
    int distanceOfCurrentQuantumBounds = getQuantumBoundsLength();
    int distanceOfOtherQuantumBounds = otherQuantum.getQuantumBoundsLength();
    Quantum scopeQuantum;
    Quantum cursorQuantum;
    if(distanceOfCurrentQuantumBounds > distanceOfOtherQuantumBounds) {
      scopeQuantum = this;
      cursorQuantum = otherQuantum;
    } else {
      scopeQuantum = otherQuantum;
      cursorQuantum = this;
    }
    int realCA = emc.getCA();
    int[] scopeBounds = scopeQuantum.getBoundaries(realCA, emc.getNextLineIndices(), emc.getSpan(), false);
    realCA = scopeBounds[0];
    emc.putCA(realCA);
    int[] newCursorBounds = cursorQuantum.getBoundaries(realCA, emc.getNextLineIndices(), emc.getSpan(), false);
    emc.putCursorQuantumStart(newCursorBounds[0]);
    emc.putCursorQuantumEnd(newCursorBounds[1]);
    if(otherQuantum.getName().equals(cursorQuantum.getName())) {
      emc.putCursorQuantum(otherQuantum);
      System.out.println("quantum changed from " + getName() + " to " + otherQuantum.getName());
    }
  }

  void quantumEnd() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String otherQuantumName = (String)eds.pop();
    QuantumNameToQuantum quantumNameToQuantum = emc.getQuantumNameToQuantum();
    Quantum quantumFromStack = quantumNameToQuantum.get(otherQuantumName);
    int distanceOfCurrentQuantumBounds = getQuantumBoundsLength();
    int distanceOfOtherQuantumBounds = quantumFromStack.getQuantumBoundsLength();
    Quantum scopeQuantum;
    Quantum cursorQuantum;
    if(distanceOfCurrentQuantumBounds > distanceOfOtherQuantumBounds) {
      scopeQuantum = this;
      cursorQuantum = quantumFromStack;
    } else {
      scopeQuantum = quantumFromStack;
      cursorQuantum = this;
    }
    int realCA = emc.getCA();
    int[] scopeBounds = scopeQuantum.getBoundaries(realCA, emc.getNextLineIndices(), emc.getSpan(), false);
    realCA = scopeBounds[1] - 1;
    emc.putCA(realCA);
    int[] newCursorBounds = cursorQuantum.getBoundaries(realCA, emc.getNextLineIndices(), emc.getSpan(), false);
    emc.putCursorQuantumStart(newCursorBounds[0]);
    emc.putCursorQuantumEnd(newCursorBounds[1]);
    if(quantumFromStack.getName().equals(cursorQuantum.getName())) {
      emc.putCursorQuantum(quantumFromStack);
      System.out.println("quantum changed from " + getName() + " to " + quantumFromStack.getName());
    }
  }

  int getQuantumBoundsLength() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int[] bounds = getBoundaries(emc.getCA(), emc.getNextLineIndices(), emc.getSpan(), false);
    return bounds[1] - bounds[0];
  }

}
