package com.ple.visur;

public class ChangeQuantumOp implements Operator {

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    String quantumName = (String)emc.getExecutionDataStack().pop();
    Quantum targetQuantum = emc.getQuantumNameToQuantum().get(quantumName);
    int[] bounds = targetQuantum.getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), emc.getSpan(), false);
    emc.putCursorQuantumStart(bounds[0]);
    emc.putCursorQuantumEnd(bounds[1]);
    if(bounds[0] == bounds[1]) {
      emc.putSpan(0);
    }
    emc.putCursorQuantum(targetQuantum);
  }

}
