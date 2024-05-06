package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  @Test void dcConstrainsDFs() {
    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(null, cursorPositionDCHolder);
    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(-1, cursorPosDCB, cursorPositionDCHolder);

    assertNull(aDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cursorPosDCB, cursorPositionDCHolder);

    assertEquals(0, aDCB.getDFB().getVal());

    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPosDCB, cursorPositionDCHolder);
    cursorPosDCB.putInner("ca", aDCB);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(-1, cxcyDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(2, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    cursorPosDCB.putInner("cxcy", cxcyDCB);

    assertNull(cxDCB);

    assertEquals(2, cyDCB.getDFB().getVal());

    long stupidlyLongNumber = 9876543210L;

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(stupidlyLongNumber, cursorPosDCB, cursorPositionDCHolder);

    assertNull(aDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(null, cursorPosDCB, cursorPositionDCHolder);

    assertNull(aDCB.getDFB().getVal());

    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(2000000000000L, cxcyDCB, cursorPositionDCHolder);

    assertNull(cyDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cursorPosDCB, cursorPositionDCHolder);

    assertEquals(cursorPositionDCHolder.javaIntDF, aDCB.getDFB().getDF());

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(9999999999999L, cursorPosDCB, cursorPositionDCHolder);

    assertNull(aDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(null, cursorPosDCB, cursorPositionDCHolder);

    assertEquals(cursorPositionDCHolder.javaIntDF, aDCB.getDFB().getDF());

    ArrayList<Integer> newlineIndicesExample = new ArrayList<>();
    newlineIndicesExample.add(0);
    newlineIndicesExample.add(50);
    newlineIndicesExample.add(100);

    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(newlineIndicesExample, cursorPosDCB, cursorPositionDCHolder);

    assertEquals(newlineIndicesExample, niDCB.getDFB().getVal());

    niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(new ArrayList<>(), cursorPosDCB, cursorPositionDCHolder);

    assertEquals(new ArrayList<>(), niDCB.getDFB().getVal());

    niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(null, cursorPosDCB, cursorPositionDCHolder);

    assertNull(niDCB);

  }

  @Test void cdcbPutInnerClearsUnsetValues() {

    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(null, cursorPositionDCHolder);
    Result r = cursorPosDCB.putInner("ni", null);
    assertNull(r.getError());

    r = cursorPosDCB.putInner("cxcyca", null);
    assertNull(r.getError());

    ArrayList<Integer> startingNewlineIndices = new ArrayList<>();
    startingNewlineIndices.add(11);
    startingNewlineIndices.add(24);
    startingNewlineIndices.add(32);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(startingNewlineIndices, cursorPosDCB, cursorPositionDCHolder);
    r = cursorPosDCB.putInner("ni", niDCB);
    assertNull(r.getError());

    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick(cursorPosDCB, cursorPositionDCHolder);
    cxcycaDCB.putInner("cxcy", null);
    cxcycaDCB.putInner("ca", null);
    cursorPosDCB.putInner("cxcyca", cxcycaDCB);
    assertNull(r.getError());

    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(10, cxcycaDCB, cursorPositionDCHolder);
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cxcycaDCB, cursorPositionDCHolder);
    r = cxcycaDCB.putInner("ca", aDCB);
    assertNull(r.getError());

    r = cxcycaDCB.putInner("cxcy", cxcyDCB);
    assertNotNull(r.getError());

    cxcyDCB.putInner("cx", null);
    cxcyDCB.putInner("cy", null);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cxcyDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cxcyDCB, cursorPositionDCHolder);

    r = cxcyDCB.putInner("cx", cxDCB);
    assertNull(r.getError());

    r = cxcyDCB.putInner("cy", cyDCB);
    assertNull(r.getError());

  }

  @Test void cdcbGetOrCalculateInner() {

    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(null, cursorPositionDCHolder);
    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick(cursorPosDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(10, cursorPosDCB, cursorPositionDCHolder);
    cxcycaDCB.putInner("ca", null);
    cxcycaDCB.putInner("cxcy", null);

    cursorPosDCB.putInner("cxcyca", cxcycaDCB);
    cursorPosDCB.putInner("ni", null);

    ArrayList<Integer> startingNewlineIndices = new ArrayList<>();
    startingNewlineIndices.add(11);
    startingNewlineIndices.add(24);
    startingNewlineIndices.add(32);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(startingNewlineIndices, cursorPosDCB, cursorPositionDCHolder);

    cursorPosDCB.putInner("ni", niDCB);

    //test 1
    cxcycaDCB.putInner("ca", caDCB);
    Result<DataClassBrick> r = cxcycaDCB.getOrCalculateInner("cxcy", cursorPositionDCHolder);
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) r.getVal();
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");

    assertNull(r.getError());
    assertEquals(10, cxDCB.getDFB().getVal());
    assertEquals(0, cyDCB.getDFB().getVal());

    //test 2
    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(7, cxcyDCB, cursorPositionDCHolder);
    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(1, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    cxcycaDCB.putInner("ca", null);
    cxcycaDCB.putInner("cxcy", cxcyDCB);
    r = cxcycaDCB.getOrCalculateInner("ca", cursorPositionDCHolder);
    caDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(19, caDCB.getDFB().getVal());

    //test 3
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(12, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.putInner("cxcy", null);
    cxcycaDCB.putInner("ca", caDCB);
    r = cxcycaDCB.getOrCalculateInner("cxcy", cursorPositionDCHolder);
    cxcyDCB = (CompoundDataClassBrick) r.getVal();
    cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");

    assertNull(r.getError());
    assertEquals(0, cxDCB.getDFB().getVal());
    assertEquals(1, cyDCB.getDFB().getVal());

    //test 4
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(21, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.putInner("cxcy", null);
    cxcycaDCB.putInner("ca", caDCB);
    r = cxcycaDCB.getOrCalculateInner("cxcy", cursorPositionDCHolder);
    cxcyDCB = (CompoundDataClassBrick) r.getVal();
    cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");

    assertNull(r.getError());
    assertEquals(9, cxDCB.getDFB().getVal());
    assertEquals(1, cyDCB.getDFB().getVal());

    //test 5
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(32, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.putInner("cxcy", null);
    cxcycaDCB.putInner("ca", caDCB);
    r = cxcycaDCB.getOrCalculateInner("cxcy", cursorPositionDCHolder);
    cxcyDCB = (CompoundDataClassBrick) r.getVal();
    cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");

    assertNull(r.getError());
    assertEquals(7, cxDCB.getDFB().getVal());
    assertEquals(2, cyDCB.getDFB().getVal());

    //test 6
    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cxcyDCB, cursorPositionDCHolder);
    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(1, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    cxcycaDCB.putInner("ca", null);
    cxcycaDCB.putInner("cxcy", cxcyDCB);
    cursorPosDCB.putInner("cxcyca", cxcycaDCB);
    r = cursorPosDCB.getOrCalculateInner("ca", cursorPositionDCHolder);
    caDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(12, caDCB.getDFB().getVal());

    //test 7
    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(1, cxcyDCB, cursorPositionDCHolder);
    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(2, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);
    cxcycaDCB.putInner("ca", null);
    cxcycaDCB.putInner("cxcy", cxcyDCB);
    cursorPosDCB.putInner("cxcyca", cxcycaDCB);
    r = cursorPosDCB.getOrCalculateInner("ca", cursorPositionDCHolder);
    caDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(26, caDCB.getDFB().getVal());

    //test 8
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(16, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.putInner("cxcy", null);
    cxcycaDCB.putInner("ca", caDCB);
    cursorPosDCB.putInner("cxcyca", cxcycaDCB);
    r = cursorPosDCB.getOrCalculateInner("cx", cursorPositionDCHolder);
    cxDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(4, cxDCB.getDFB().getVal());

    //test 9
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(16, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.putInner("cxcy", null);
    cxcycaDCB.putInner("ca", caDCB);
    cursorPosDCB.putInner("cxcyca", cxcycaDCB);
    r = cursorPosDCB.getOrCalculateInner("cy", cursorPositionDCHolder);
    cyDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(1, cyDCB.getDFB().getVal());

    //test 10
    r = cursorPosDCB.getOrCalculateInner("cx", cursorPositionDCHolder);
    cxDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(4, cxDCB.getDFB().getVal());

  }

}
