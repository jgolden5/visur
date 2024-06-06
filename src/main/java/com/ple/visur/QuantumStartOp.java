package com.ple.visur;

public class QuantumStartOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    Quantum currentQuantum = emc.getCurrentQuantum();
    int ca = currentQuantum.quantumStart();
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(ca);
  }
}
