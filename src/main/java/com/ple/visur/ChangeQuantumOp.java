package com.ple.visur;

public class ChangeQuantumOp implements Operator {

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    String quantumName = (String)emc.getExecutionDataStack().pop();
    String quantumNameWithoutQuotes = quantumName.substring(1, quantumName.length() - 1);
    Quantum targetQuantum = emc.getQuantumNameToQuantum().get(quantumNameWithoutQuotes);
    int[] bounds = targetQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), emc.getSpan(), false);
    emc.putCursorQuantumStart(bounds[0]);
    emc.putCursorQuantumEnd(bounds[1]);
    emc.putCursorQuantum(targetQuantum);
  }

}
