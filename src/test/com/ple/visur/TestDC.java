package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  @Test void dcConstrainsDFs() {
    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(cursorPositionDCHolder, null);
    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(-1, cursorPosDCB, cursorPositionDCHolder);

    assertNull(aDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cursorPosDCB, cursorPositionDCHolder);

    assertEquals(0, aDCB.getVal().getVal());

    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, cursorPosDCB);
    cursorPosDCB.putInner("a", aDCB);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(-1, cxcyDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(2, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    cursorPosDCB.putInner("cxcy", cxcyDCB);

    assertNull(cxDCB);

    assertEquals(2, cyDCB.getVal().getVal());

    long stupidlyLongNumber = 9876543210L;

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(stupidlyLongNumber, cursorPosDCB, cursorPositionDCHolder);

    assertNull(aDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(null, cursorPosDCB, cursorPositionDCHolder);

    assertNull(aDCB.getVal().getVal());

    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(2000000000000L, cxcyDCB, cursorPositionDCHolder);

    assertNull(cyDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cursorPosDCB, cursorPositionDCHolder);

    assertEquals(cursorPositionDCHolder.javaIntDF, aDCB.getVal().getDF());

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(9999999999999L, cursorPosDCB, cursorPositionDCHolder);

    assertNull(aDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(null, cursorPosDCB, cursorPositionDCHolder);

    assertEquals(cursorPositionDCHolder.javaIntDF, aDCB.getVal().getDF());

  }

  @Test void dfMakeBrick() {
  }

  @Test void cdcbCalculateInner() {



  }
  @Test void calculateInnerFailsWithNotEnoughInfo() {

  }

}
