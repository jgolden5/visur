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
    cursorPosDCB.put("ca", aDCB);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(-1, cxcyDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(2, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.put("cx", cxDCB);
    cxcyDCB.put("cy", cyDCB);
    cursorPosDCB.put("cxcy", cxcyDCB);

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
    Result r = cursorPosDCB.put("ni", null);
    assertNull(r.getError());

    r = cursorPosDCB.put("cxcyca", null);
    assertNull(r.getError());

    ArrayList<Integer> startingNewlineIndices = new ArrayList<>();
    startingNewlineIndices.add(11);
    startingNewlineIndices.add(24);
    startingNewlineIndices.add(32);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(startingNewlineIndices, cursorPosDCB, cursorPositionDCHolder);
    r = cursorPosDCB.put("ni", niDCB);
    assertNull(r.getError());

    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick(cursorPosDCB, cursorPositionDCHolder);
    cxcycaDCB.put("cxcy", null);
    cxcycaDCB.put("ca", null);
    cursorPosDCB.put("cxcyca", cxcycaDCB);
    assertNull(r.getError());

    PrimitiveDataClassBrick aDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(10, cxcycaDCB, cursorPositionDCHolder);
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cxcycaDCB, cursorPositionDCHolder);
    r = cxcycaDCB.put("ca", aDCB);
    assertNull(r.getError());

    r = cxcycaDCB.put("cxcy", cxcyDCB);
    assertNotNull(r.getError());

    cxcyDCB.put("cx", null);
    cxcyDCB.put("cy", null);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cxcyDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cxcyDCB, cursorPositionDCHolder);

    r = cxcyDCB.put("cx", cxDCB);
    assertNull(r.getError());

    r = cxcyDCB.put("cy", cyDCB);
    assertNull(r.getError());

  }

  @Test void cdcbGetOrCalculateInner() {

    //note that these tests assume the following editorContent:
//    final String initialEditorContent = "Hello world\n" +
//      "How are you?\n" +
//      "Goodbye";


    CompoundDataClassBrick cursorPosDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(null, cursorPositionDCHolder);
    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick(cursorPosDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(10, cursorPosDCB, cursorPositionDCHolder);
    cxcycaDCB.put("ca", null);
    cxcycaDCB.put("cxcy", null);

    cursorPosDCB.put("cxcyca", cxcycaDCB);
    cursorPosDCB.put("ni", null);

    ArrayList<Integer> startingNewlineIndices = new ArrayList<>();
    startingNewlineIndices.add(11);
    startingNewlineIndices.add(24);
    startingNewlineIndices.add(32);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(startingNewlineIndices, cursorPosDCB, cursorPositionDCHolder);

    cursorPosDCB.put("ni", niDCB);

    //test 1
    cxcycaDCB.put("ca", caDCB);
    Result<DataClassBrick> r = cxcycaDCB.getOrCalc("cxcy", cursorPositionDCHolder);
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) r.getVal();
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");

    assertNull(r.getError());
    assertEquals(10, cxDCB.getDFB().getVal());
    assertEquals(0, cyDCB.getDFB().getVal());

    //test 2
    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(7, cxcyDCB, cursorPositionDCHolder);
    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(1, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.put("cx", cxDCB);
    cxcyDCB.put("cy", cyDCB);
    cxcycaDCB.put("ca", null);
    cxcycaDCB.put("cxcy", cxcyDCB);
    r = cxcycaDCB.getOrCalc("ca", cursorPositionDCHolder);
    caDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(19, caDCB.getDFB().getVal());

    //test 3
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(12, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.put("cxcy", null);
    cxcycaDCB.put("ca", caDCB);
    r = cxcycaDCB.getOrCalc("cxcy", cursorPositionDCHolder);
    cxcyDCB = (CompoundDataClassBrick) r.getVal();
    cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");

    assertNull(r.getError());
    assertEquals(0, cxDCB.getDFB().getVal());
    assertEquals(1, cyDCB.getDFB().getVal());

    //test 4
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(21, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.put("cxcy", null);
    cxcycaDCB.put("ca", caDCB);
    r = cxcycaDCB.getOrCalc("cxcy", cursorPositionDCHolder);
    cxcyDCB = (CompoundDataClassBrick) r.getVal();
    cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");

    assertNull(r.getError());
    assertEquals(9, cxDCB.getDFB().getVal());
    assertEquals(1, cyDCB.getDFB().getVal());

    //test 5
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(32, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.put("cxcy", null);
    cxcycaDCB.put("ca", caDCB);
    r = cxcycaDCB.getOrCalc("cxcy", cursorPositionDCHolder);
    cxcyDCB = (CompoundDataClassBrick) r.getVal();
    cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");

    assertNull(r.getError());
    assertEquals(7, cxDCB.getDFB().getVal());
    assertEquals(2, cyDCB.getDFB().getVal());

    //test 6
    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(0, cxcyDCB, cursorPositionDCHolder);
    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(1, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.put("cx", cxDCB);
    cxcyDCB.put("cy", cyDCB);
    cxcycaDCB.put("ca", null);
    cxcycaDCB.put("cxcy", cxcyDCB);
    cursorPosDCB.put("cxcyca", cxcycaDCB);
    r = cursorPosDCB.getOrCalc("ca", cursorPositionDCHolder);
    caDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(12, caDCB.getDFB().getVal());

    //test 7
    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(1, cxcyDCB, cursorPositionDCHolder);
    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(2, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.put("cx", cxDCB);
    cxcyDCB.put("cy", cyDCB);
    cxcycaDCB.put("ca", null);
    cxcycaDCB.put("cxcy", cxcyDCB);
    cursorPosDCB.put("cxcyca", cxcycaDCB);
    r = cursorPosDCB.getOrCalc("ca", cursorPositionDCHolder);
    caDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(26, caDCB.getDFB().getVal());

    //test 8
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(16, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.put("cxcy", null);
    cxcycaDCB.put("ca", caDCB);
    cursorPosDCB.put("cxcyca", cxcycaDCB);
    r = cursorPosDCB.getOrCalc("cx", cursorPositionDCHolder);
    cxDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(4, cxDCB.getDFB().getVal());

    //test 9
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(16, cxcycaDCB, cursorPositionDCHolder);
    cxcycaDCB.put("cxcy", null);
    cxcycaDCB.put("ca", caDCB);
    cursorPosDCB.put("cxcyca", cxcycaDCB);
    r = cursorPosDCB.getOrCalc("cy", cursorPositionDCHolder);
    cyDCB = (PrimitiveDataClassBrick) r.getVal();

    assertNull(r.getError());
    assertEquals(1, cyDCB.getDFB().getVal());

    //test 10
    r = cursorPosDCB.getOrCalc("cx", cursorPositionDCHolder);
    cxDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(4, cxDCB.getDFB().getVal());

  }

}
