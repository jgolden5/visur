package com.ple.visur;

public class RelativeMoveOperator implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelService ems = ServiceHolder.editorModelService;
    ExecutionDataStack eds = ems.getExecutionData();
    int dy = (int)eds.pop();
    int dx = (int)eds.pop();
    Quantum q = ems.getQuantum();
    int contentX = ems.getGlobalVar("contentX").getInt();
    int contentY = ems.getGlobalVar("contentY").getInt();
    CursorPosition cursorPosition = new CursorPosition(contentX, contentY);
    MovementVector movementVector = new MovementVector(dx, dy);
    q.move(cursorPosition, ems.getEditorContentLines(), movementVector);
  }
}
