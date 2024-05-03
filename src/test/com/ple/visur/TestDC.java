package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  @Test void dcConstrainsDFs() {
    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(cursorPositionDCHolder, null);
    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(-1, cursorPosDCB, cursorPositionDCHolder);

    assertNull(aDCB);

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cursorPosDCB, cursorPositionDCHolder);

    assertEquals(0, aDCB.getDFB().getVal());

    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, cursorPosDCB);
    cursorPosDCB.putInner("a", aDCB);
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

    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(cursorPositionDCHolder, null);
    Result r = cursorPosDCB.putInner("ni", null);
    assertNull(r.getError());

    r = cursorPosDCB.putInner("cxcya", null);
    assertNull(r.getError());

    ArrayList<Integer> startingNewlineIndices = new ArrayList<>();
    startingNewlineIndices.add(11);
    startingNewlineIndices.add(24);
    startingNewlineIndices.add(32);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(startingNewlineIndices, cursorPosDCB, cursorPositionDCHolder);
    r = cursorPosDCB.putInner("ni", niDCB);
    assertNull(r.getError());

    CompoundDataClassBrick cxcyaDCB = cursorPositionDCHolder.cxcyaDC.makeBrick(cursorPositionDCHolder, cursorPosDCB);
    cxcyaDCB.putInner("cxcy", null);
    cxcyaDCB.putInner("a", null);
    cursorPosDCB.putInner("cxcya", cxcyaDCB);
    assertNull(r.getError());

    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(10, cxcyaDCB, cursorPositionDCHolder);
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, cxcyaDCB);
    r = cxcyaDCB.putInner("a", aDCB);
    assertNull(r.getError());

    r = cxcyaDCB.putInner("cxcy", cxcyDCB);
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

    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(cursorPositionDCHolder, null);
    CompoundDataClassBrick cxcyaDCB = cursorPositionDCHolder.cxcyaDC.makeBrick(cursorPositionDCHolder, cursorPosDCB);
    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(10, cursorPosDCB, cursorPositionDCHolder);
    cxcyaDCB.putInner("a", aDCB);
    cxcyaDCB.putInner("cxcy", null);

    cursorPosDCB.putInner("cxcya", cxcyaDCB);
    cursorPosDCB.putInner("ni", null);

    //test 1
    Result r = cxcyaDCB.getOrCalculateInner("a", cursorPositionDCHolder);
    assertNotNull(r.getError());
    assertNull(r.getVal());

    ArrayList<Integer> startingNewlineIndices = new ArrayList<>();
    startingNewlineIndices.add(11);
    startingNewlineIndices.add(24);
    startingNewlineIndices.add(32);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(startingNewlineIndices, cursorPosDCB, cursorPositionDCHolder);

    cursorPosDCB.putInner("ni", niDCB);

    //test 2
    r = cxcyaDCB.getOrCalculateInner("cxcy", cursorPositionDCHolder);
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) r.getVal();
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");

    assertNull(r.getError());
    assertEquals(10, cxDCB.getDFB().getVal());
    assertEquals(0, cyDCB.getDFB().getVal());

  }

}
