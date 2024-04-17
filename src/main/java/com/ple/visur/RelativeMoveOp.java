package com.ple.visur;

import java.util.ArrayList;

public class RelativeMoveOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler ems = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = ems.getExecutionDataStack();
    String editorContent = ems.getEditorContent();
    ArrayList<Integer> newlineIndices = ems.getNewlineIndices();
    int dy = (int)eds.pop();
    int dx = (int)eds.pop();
    Quantum currentQuantum = ems.getCurrentQuantum();
    MovementVector movementVector = new MovementVector(dx, dy);
    int contentX = ems.getGlobalVar("ca").getInt();
    int contentY = ems.getGlobalVar("contentY").getInt();
    Coordinate pos = Coordinate.make(contentX, contentY);
    int[] bounds = currentQuantum.getBoundaries(editorContent, newlineIndices, contentX, contentY, false);
    Coordinate newCoordinate = currentQuantum.move(editorContent, newlineIndices, pos, movementVector, bounds);
    if(newCoordinate.x < editorContent.length()) {
      ems.putGlobalVar("ca", new IntVisurVar(newCoordinate.x));
    }
    ems.putGlobalVar("contentY", new IntVisurVar(newCoordinate.y));
    int[] newBounds = currentQuantum.getBoundaries(editorContent, newlineIndices, newCoordinate.x, newCoordinate.y, false);
    ems.putQuantumStart(newBounds[0]);
    ems.putQuantumEnd(newBounds[1]);
  }
}
