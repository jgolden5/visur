package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  @Test void dcConstrainsDFs() {
    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPosDC.makeBrick(cursorPositionDCHolder);
    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(-1, cursorPosDCB, cursorPositionDCHolder);
    
    assertNull(aDCB.getVal().getVal());

    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder);
    cursorPosDCB.putInner("a", aDCB);
    DataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(null, cxcyDCB, cursorPositionDCHolder);
    DataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(null, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    cursorPosDCB.putInner("cxcy", cxcyDCB);
  }

  @Test void cdcbCalculateInner() {

  }
  @Test void calculateInnerFailsWithNotEnoughInfo() {

  }

}
