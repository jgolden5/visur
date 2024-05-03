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

  @Test void cdcbGetOrCalculateInner() {
    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(cursorPositionDCHolder, null);
    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(10, cursorPosDCB, cursorPositionDCHolder);
    cursorPosDCB.putInner("a", aDCB);

    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, cursorPosDCB);
    cursorPosDCB.putInner("cxcy", cxcyDCB);

    ArrayList<Integer> exampleNewlineIndices = new ArrayList<>();
    exampleNewlineIndices.add(11);
    exampleNewlineIndices.add(24);
    exampleNewlineIndices.add(32);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(exampleNewlineIndices, cursorPosDCB, cursorPositionDCHolder);
    cursorPosDCB.putInner("ni", niDCB);

    cxcyDCB = (CompoundDataClassBrick)cursorPosDCB.getOrCalculateInner("cxcy", cursorPositionDCHolder).getVal();
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick)cxcyDCB.getOrCalculateInner("cx", cursorPositionDCHolder).getVal();
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick)cxcyDCB.getOrCalculateInner("cy", cursorPositionDCHolder).getVal();
    assertEquals(10, cxDCB.getDFB().getVal()); //if pass-by-reference is not working as expected, a) understand why, and
    assertEquals(0, cyDCB.getDFB().getVal()); // b) manually get cx and cy after having calculating the new cxcyDCB

    cursorPosDCB.putInner("a", null);

    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(7, cxcyDCB, cursorPositionDCHolder);
    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(1, cxcyDCB, cursorPositionDCHolder);

    cxcyDCB.putInner("cx", cxDCB);
    cxcyDCB.putInner("cy", cyDCB);

    cursorPosDCB.putInner("cxcy", cxcyDCB);

    aDCB = (PrimitiveDataClassBrick) cursorPosDCB.getOrCalculateInner("a", cursorPositionDCHolder).getVal();

    assertEquals(19, aDCB.getDFB().getVal());

    aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(12, cursorPosDCB, cursorPositionDCHolder);
    cursorPosDCB.putInner("a", aDCB);
    cxcyDCB = (CompoundDataClassBrick) cursorPosDCB.getOrCalculateInner("cxcy", cursorPositionDCHolder).getVal();

    cursorPosDCB.putInner("cxcy", cxcyDCB);

    cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getOrCalculateInner("cx", cursorPositionDCHolder).getVal();
    cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getOrCalculateInner("cy", cursorPositionDCHolder).getVal();

    assertEquals(0, cxDCB.getDFB().getVal());
    assertEquals(1, cyDCB.getDFB().getVal());

  }

  @Test void cdcbPutInnerClearsUnsetValues() {

    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(cursorPositionDCHolder, null);
    Result r = cursorPosDCB.putInner("ni", null);
    assertNull(r.error);

    r = cursorPosDCB.putInner("cxcya", null);
    assertNull(r.error);

    ArrayList<Integer> startingNewlineIndices = new ArrayList<>();
    startingNewlineIndices.add(11);
    startingNewlineIndices.add(24);
    startingNewlineIndices.add(32);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(startingNewlineIndices, cursorPosDCB, cursorPositionDCHolder);
    r = cursorPosDCB.putInner("ni", niDCB);
    assertNull(r.error);

    CompoundDataClassBrick cxcyaDCB = cursorPositionDCHolder.cxcyaDC.makeBrick(cursorPositionDCHolder, cursorPosDCB);
    cxcyaDCB.putInner("cxcy", null);
    cxcyaDCB.putInner("a", null);
    cursorPosDCB.putInner("cxcya", cxcyaDCB);
    assertNull(r.error);

    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(10, cxcyaDCB, cursorPositionDCHolder);
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cursorPositionDCHolder, cxcyaDCB);
    r = cxcyaDCB.putInner("cxcy", cxcyDCB);

    assertNotNull(r.error);

  }

}
