package com.ple.visur;

import io.vertx.core.shareddata.Shareable;

import java.util.ArrayList;

public abstract class Quantum implements Shareable {
  abstract int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail); //newlineIndices are only needed in CustomQuantums
  abstract int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector m, int[] bounds);
  abstract String getName();
  int quantumStart() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    Quantum currentQuantum = emc.getCurrentQuantum();
    String currentQuantumName = currentQuantum.getName();
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String otherQuantumName = (String)eds.pop();
    System.out.println("quantumStart called. CurrentQ = " + currentQuantumName + ". OtherQ = " + otherQuantumName);
    return 0;
  }
  int quantumEnd() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    Quantum currentQuantum = emc.getCurrentQuantum();
    String currentQuantumName = currentQuantum.getName();
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String otherQuantumName = (String)eds.pop();
    System.out.println("quantumEnd called. CurrentQ = " + currentQuantumName + ". OtherQ = " + otherQuantumName);
    return 0;
  }
}
