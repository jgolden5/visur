package com.ple.visur;

public class QuantumEndOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    Quantum currentQuantum = emc.getCurrentQuantum();
    int ca = currentQuantum.quantumEnd();
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(ca);
  }
}
