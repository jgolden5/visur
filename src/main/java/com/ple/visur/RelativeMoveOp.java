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
    int cxDCB = emc.getCX();
    int contentX = emc.getCY();
    int contentY = emc.getGlobalVar("contentY").getInt();
    Coordinate pos = Coordinate.make(contentX, contentY);
    int[] bounds = currentQuantum.getBoundaries(editorContent, newlineIndices, contentX, contentY, false);
    Coordinate newCoordinate = currentQuantum.move(editorContent, newlineIndices, pos, movementVector, bounds);
    if(newCoordinate.x < editorContent.length()) {
      emc.putGlobalVar("ca", VisurVar.make(null, ));
    }
    emc.putGlobalVar("contentY", new IntVisurVar(newCoordinate.y));
    int[] newBounds = currentQuantum.getBoundaries(editorContent, newlineIndices, newCoordinate.x, newCoordinate.y, false);
    emc.putQuantumStart(newBounds[0]);
    emc.putQuantumEnd(newBounds[1]);
  }
}
