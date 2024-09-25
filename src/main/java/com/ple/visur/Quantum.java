package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;

public abstract class Quantum implements Shareable {
  abstract int[] getBoundaries(int ca, ArrayList<Integer> newlineIndices, int span, boolean includeTail); //newlineIndices are only needed in CustomQuantums
  abstract int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m);
  abstract String getName();
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
  void quantumStart() {
    int spanMinimumOne = Math.max(emc.getSpan(), 1);
    Quantum[] scopeAndCursorQuantums = getScopeAndCursorQuantums(spanMinimumOne);
    Quantum scopeQuantum = scopeAndCursorQuantums[0];
    Quantum cursorQuantum = scopeAndCursorQuantums[1];
    int ca = emc.getCA();
    int[] scopeBounds = scopeQuantum.getBoundaries(ca, emc.getNextLineIndices(), spanMinimumOne, false);
    ca = scopeBounds[0];
    emc.putCA(ca);
    emc.putVirtualCX(emc.getCX());
    int[] newCursorBounds = cursorQuantum.getBoundaries(ca, emc.getNextLineIndices(), spanMinimumOne, false);
    int actualSpan = emc.getSpan();
    if(actualSpan == 0) {
      newCursorBounds[1] = newCursorBounds[0];
    }
    emc.putCursorQuantumStartAndScroll(newCursorBounds[0]);
    emc.putCursorQuantumEndAndScroll(newCursorBounds[1]);
    if(cursorQuantum.equals(emc.getCursorQuantum())) {
      emc.putCursorQuantum(cursorQuantum);
    }
  }

  private Quantum[] getScopeAndCursorQuantums(int spanMinimumOne) {
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String otherQuantumName = (String)eds.pop();
    QuantumNameToQuantum quantumNameToQuantum = emc.getQuantumNameToQuantum();
    Quantum otherQuantum = quantumNameToQuantum.get(otherQuantumName);
    int distanceOfCurrentQuantumBounds = getQuantumBoundsLengthAtSpan(spanMinimumOne);
    int distanceOfOtherQuantumBounds = otherQuantum.getQuantumBoundsLengthAtSpan(spanMinimumOne);
    Quantum scopeQuantum;
    Quantum cursorQuantum;
    if(distanceOfCurrentQuantumBounds > distanceOfOtherQuantumBounds) {
      scopeQuantum = this;
      cursorQuantum = otherQuantum;
    } else {
      scopeQuantum = otherQuantum;
      cursorQuantum = this;
    }
    return new Quantum[]{scopeQuantum, cursorQuantum};
  }

  void quantumEnd() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String otherQuantumName = (String)eds.pop();
    QuantumNameToQuantum quantumNameToQuantum = emc.getQuantumNameToQuantum();
    Quantum quantumFromStack = quantumNameToQuantum.get(otherQuantumName);
    int spanMinimumOne = Math.max(emc.getSpan(), 1);
    int actualSpan = emc.getSpan();
    int distanceOfCurrentQuantumBounds = getQuantumBoundsLengthAtSpan(spanMinimumOne);
    int distanceOfOtherQuantumBounds = quantumFromStack.getQuantumBoundsLengthAtSpan(spanMinimumOne);
    Quantum scopeQuantum;
    Quantum cursorQuantum;
    if(distanceOfCurrentQuantumBounds > distanceOfOtherQuantumBounds) {
      scopeQuantum = this;
      cursorQuantum = quantumFromStack;
    } else {
      scopeQuantum = quantumFromStack;
      cursorQuantum = actualSpan == 0 ? quantumNameToQuantum.get("character") : this;
    }
    int ca = emc.getCA();
    int[] scopeBounds = scopeQuantum.getBoundaries(ca, emc.getNextLineIndices(), spanMinimumOne, false);
    ca = actualSpan > 0 ? scopeBounds[1] - 1 : scopeBounds[1];
    emc.putCA(ca);
    emc.putVirtualCX(emc.getCX());
    int[] newCursorBounds = cursorQuantum.getBoundaries(ca, emc.getNextLineIndices(), spanMinimumOne, false);
    emc.putCursorQuantumStartAndScroll(newCursorBounds[0]);
    emc.putCursorQuantumEndAndScroll(newCursorBounds[1]);
    if(quantumFromStack.getName().equals(cursorQuantum.getName())) {
      emc.putCursorQuantum(quantumFromStack);
      System.out.println("quantum changed from " + getName() + " to " + quantumFromStack.getName());
    }
  }

  int getQuantumBoundsLengthAtSpan(int span) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int[] bounds = getBoundaries(emc.getCA(), emc.getNextLineIndices(), span, false);
    return bounds[1] - bounds[0];
  }

}
