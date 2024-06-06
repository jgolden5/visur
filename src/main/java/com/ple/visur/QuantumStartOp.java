package com.ple.visur;

public class QuantumStartOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    Quantum currentQuantum = emc.getCurrentQuantum();
    currentQuantum.quantumStart(); //internally sets ca and quantum bounds
  }
}
