package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;

public abstract class Quantum implements Shareable {
  abstract int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int span, boolean includeTail); //newlineIndices are only needed in CustomQuantums
  abstract int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m);
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
    int[] scopeBounds = scopeQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), emc.getSpan(), false);
    int ca = scopeBounds[0];
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(ca);
    emc.putVirtualCX(emc.getRealLongCX());
    int[] newCursorBounds = cursorQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), emc.getSpan(), false);
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
    int[] scopeBounds = scopeQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), emc.getSpan(), false);
    int ca = scopeBounds[1] - 1;
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(ca);
    emc.putVirtualCX(emc.getRealLongCX());
    int[] newCursorBounds = cursorQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), emc.getSpan(), false);
    emc.putCursorQuantumStart(newCursorBounds[0]);
    emc.putCursorQuantumEnd(newCursorBounds[1]);
    if(quantumFromStack.getName().equals(cursorQuantum.getName())) {
      emc.putCursorQuantum(quantumFromStack);
      System.out.println("quantum changed from " + getName() + " to " + quantumFromStack.getName());
    }
  }

  int getQuantumBoundsLength() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int[] bounds = getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), emc.getSpan(), false);
    return bounds[1] - bounds[0];
  }

}
