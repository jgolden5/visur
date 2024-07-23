package com.ple.visur;

public class ChangeCursorQuantumOp implements Operator {

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    String cursorQuantumName = getQuantumName(emc);
    if(cursorQuantumName != null) {
      Quantum targetQuantum = emc.getQuantumNameToQuantum().get(cursorQuantumName);
      int[] bounds = targetQuantum.getBoundaries(emc.getCA(), emc.getNewlineIndices(), emc.getSpan(), false);
      emc.putCursorQuantumStart(bounds[0]);
      emc.putCursorQuantumEnd(bounds[1]);
      if (bounds[0] == bounds[1]) {
        emc.putSpan(0);
      }
      emc.putCursorQuantum(targetQuantum);
    }
  }

  public String getQuantumName(EditorModelCoupler emc) {
    String topElement = (String)emc.getExecutionDataStack().peek();
    String qName;
    boolean topElementIsInvalidQuantumName = emc.getQuantumNameToQuantum().get(topElement) == null;
    if(topElementIsInvalidQuantumName) {
      boolean quantumGlobalVarExistsWithTopElementName = emc.getGlobalVar(topElement).getVal() instanceof Quantum;
      if(quantumGlobalVarExistsWithTopElementName) {
        ObjectVisurVar qOVV = (ObjectVisurVar) emc.getGlobalVar(topElement);
        Quantum q = (Quantum)qOVV.getVal();
        qName = q.getName();
      } else {
        return null;
      }
    } else {
      qName = (String) emc.getExecutionDataStack().pop();;
    }
    return qName;
  }

}
