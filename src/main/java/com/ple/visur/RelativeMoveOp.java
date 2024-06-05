package com.ple.visur;

import java.util.ArrayList;

public class RelativeMoveOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String editorContent = emc.getEditorContent();
    ArrayList<Integer> newlineIndices = emc.getNewlineIndices();
    int dy = (int)eds.pop();
    int dx = (int)eds.pop();
    Quantum currentQuantum = emc.getCurrentQuantum();
    MovementVector movementVector = new MovementVector(dx, dy);
    int[] bounds = currentQuantum.getBoundaries(editorContent, newlineIndices, false);
    int newCA = currentQuantum.move(editorContent, newlineIndices, movementVector, bounds);
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(newCA);
    emc.putGlobalVar("ca", caBVV);
    int[] newBounds = currentQuantum.getBoundaries(editorContent, newlineIndices, false);
    emc.putQuantumStart(newBounds[0]);
    emc.putQuantumEnd(newBounds[1]);
  }
}
