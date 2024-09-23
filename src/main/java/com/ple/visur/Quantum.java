package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;

public abstract class Quantum implements Shareable {
  abstract int[] getBoundaries(int ca, ArrayList<Integer> newlineIndices, int span, boolean includeTail); //newlineIndices are only needed in CustomQuantums
  abstract int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m);
  abstract String getName();
  void quantumStart() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String otherQuantumName = (String)eds.pop();
    QuantumNameToQuantum quantumNameToQuantum = emc.getQuantumNameToQuantum();
    Quantum otherQuantum = quantumNameToQuantum.get(otherQuantumName);
    int spanMinimumOne = Math.max(emc.getSpan(), 1);
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
    int span = emc.getSpan();
    int distanceOfCurrentQuantumBounds = getQuantumBoundsLengthAtSpan(Math.max(span, 1));
    int distanceOfOtherQuantumBounds = quantumFromStack.getQuantumBoundsLengthAtSpan(Math.max(span, 1));
    Quantum scopeQuantum;
    Quantum cursorQuantum;
    if(distanceOfCurrentQuantumBounds > distanceOfOtherQuantumBounds) {
      scopeQuantum = this;
      cursorQuantum = quantumFromStack;
    } else {
      scopeQuantum = quantumFromStack;
      cursorQuantum = this;
    }
    int ca = emc.getCA();
    int[] scopeBounds = scopeQuantum.getBoundaries(ca, emc.getNextLineIndices(), emc.getSpan(), false);
    ca = scopeBounds[1] - 1;
    emc.putCA(ca);
    emc.putVirtualCX(emc.getCX());
    int[] newCursorBounds = cursorQuantum.getBoundaries(ca, emc.getNextLineIndices(), emc.getSpan(), false);
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
