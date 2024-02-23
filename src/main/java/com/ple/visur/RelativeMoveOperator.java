package com.ple.visur;

public class RelativeMoveOperator implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionDataStack eds = ems.getExecutionDataStack();
    String[] contentLines = ems.getEditorContentLines();
    int dy = (int)eds.pop();
    int dx = (int)eds.pop();
    Quantum currentQuantum = ems.getCurrentQuantum();
    MovementVector movementVector = new MovementVector(dx, dy);
    int contentX = ems.getGlobalVar("contentX").getInt();
    int contentY = ems.getGlobalVar("contentY").getInt();
    int[] quantumBounds = currentQuantum.getBoundaries(contentLines, contentX, contentY);
    CursorPosition newCursorPosition = currentQuantum.move(contentLines, movementVector, quantumBounds);
    ems.putGlobalVar("contentX", new IntVisurVar(newCursorPosition.x));
    ems.putGlobalVar("contentY", new IntVisurVar(newCursorPosition.y));
  }
}
