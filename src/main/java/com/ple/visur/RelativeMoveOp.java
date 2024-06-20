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
    Quantum cursorQuantum = emc.getCursorQuantum();
    MovementVector movementVector = new MovementVector(dx, dy);
    int newCA = cursorQuantum.move(editorContent, newlineIndices, movementVector);
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    caBVV.putVal(newCA);
    emc.putGlobalVar("ca", caBVV);
    int[] newBounds;
    if(newCA == 0 && editorContent.length() == 0) {
      newBounds = new int[]{newCA, newCA};
      emc.putSpan(0);
    } else {
      newBounds = cursorQuantum.getBoundaries(editorContent, newlineIndices, emc.getSpan(), false);
    }
    emc.putCursorQuantumStart(newBounds[0]);
    emc.putCursorQuantumEnd(newBounds[1]);
  }
}
