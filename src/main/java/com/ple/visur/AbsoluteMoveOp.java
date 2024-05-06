package com.ple.visur;

import CursorPositionDC.CursorPositionDCHolder;
import DataClass.CompoundDataClassBrick;

import java.awt.*;

public class AbsoluteMoveOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    CursorPositionDCHolder cursorPositionDCHolder = emc.getCursorPositionDCHolder();
    CompoundDataClassBrick cxcyaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick();
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick();
    emc.putGlobalVar("ca", contentX);
    emc.putGlobalVar("contentY", contentY);
  }
}
