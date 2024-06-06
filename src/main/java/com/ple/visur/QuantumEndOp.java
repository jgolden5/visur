package com.ple.visur;

public class QuantumEndOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    Quantum currentQuantum = emc.getCurrentQuantum();
    currentQuantum.quantumEnd();
  }
}
