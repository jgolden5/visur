package com.ple.visur;

import java.util.ArrayList;

public class ChangeQuantumOp implements Operator {

  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    String quantumName = (String)ems.getExecutionDataStack().pop();
    String quantumNameWithoutQuotes = quantumName.substring(1, quantumName.length() - 1);
    Quantum targetQuantum = ems.getQuantumMap().get(quantumNameWithoutQuotes);
    int contentX = ems.getGlobalVar("contentX").getInt();
    int contentY = ems.getGlobalVar("contentY").getInt();
    int[] bounds = targetQuantum.getBoundaries(ems.getEditorContent(), ems.getNewlineIndices(), contentX, contentY, false);
    ems.putQuantumStart(bounds[0]);
    ems.putQuantumEnd(bounds[1]);
    ems.putCurrentQuantum(targetQuantum);
  }

}
