package com.ple.visur;

public class RelativeMoveOperator implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionDataStack eds = ems.getExecutionData();
    int dy = (int)eds.pop();
    int dx = (int)eds.pop();
    Quantum currentQuantum = ems.getCurrentQuantum();
    int contentX = ems.getGlobalVar("contentX").getInt();
    int contentY = ems.getGlobalVar("contentY").getInt();
    CursorPosition cursorPosition = new CursorPosition(contentX, contentY);
    MovementVector movementVector = new MovementVector(dx, dy);
    CursorPosition newCursorPosition = currentQuantum.move(ems.getEditorContentLines(), cursorPosition, movementVector);
    ems.putGlobalVar("contentX", new IntVisurVar(newCursorPosition.x));
    ems.putGlobalVar("contentY", new IntVisurVar(newCursorPosition.y));
  }
}
