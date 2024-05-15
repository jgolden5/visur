package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();

  @Test void dcMakeBrick() {
    //cdc makeBrick tests
    //cursorPositionDC.makeBrick works
    CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition", null, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.cursorPositionDC, cursorPositionDCB.getCDC());
    assertNull(cursorPositionDCB.getOuter());
    assertEquals("cursorPosition", cursorPositionDCB.getName());

    //cxcycaDC.makeBrick works
    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick("cxcyca", cursorPositionDCB, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.cxcycaDC, cxcycaDCB.getCDC());
    assertEquals(cursorPositionDCHolder.cursorPositionDC, cxcycaDCB.getOuter().getCDC());
    assertEquals("cxcyca", cxcycaDCB.getName());

    //cxcyDC.makeBrick works
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick("cxcy", cxcycaDCB, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.wholePairDC, cxcyDCB.getCDC());
    assertEquals(cursorPositionDCHolder.cxcycaDC, cxcyDCB.getOuter().getCDC());
    assertEquals("cxcy", cxcyDCB.getName());

    //pdc makeBrick tests
    //niDC.makeBrick works
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.wholeNumberListDC, niDCB.getPDC());
    assertEquals(newlineIndices, niDCB.getDFB().getVal());
    assertEquals(cursorPositionDCHolder.cursorPositionDC, niDCB.getOuter().getCDC());
    assertEquals("ni", niDCB.getName());

    //wholeNumberDC.makeBrick works for cxDCB
    int cx = 0;
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", cx, cxcyDCB, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.wholeNumberDC, cxDCB.getPDC());
    assertEquals(cx, cxDCB.getDFB().getVal());
    assertEquals(cursorPositionDCHolder.wholePairDC, cxDCB.getOuter().getCDC());
    assertEquals("cx", cxDCB.getName());

    //wholeNumberDC.makeBrick works for cyDCB
    int cy = 1;
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", cy, cxcyDCB, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.wholeNumberDC, cyDCB.getPDC());
    assertEquals(cy, cyDCB.getDFB().getVal());
    assertEquals(cursorPositionDCHolder.wholePairDC, cyDCB.getOuter().getCDC());
    assertEquals("cy", cyDCB.getName());

    //wholeNumberDC.makeBrick works for caDCB
    int ca = 2;
    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", ca, cxcycaDCB, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.wholeNumberDC, caDCB.getPDC());
    assertEquals(ca, caDCB.getDFB().getVal());
    assertEquals(cursorPositionDCHolder.cxcycaDC, caDCB.getOuter().getCDC());
    assertEquals("ca", caDCB.getName());
  }

  @Test void dcbRemove() {
    CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition", null, cursorPositionDCHolder);
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick("cxcyca", cursorPositionDCB, cursorPositionDCHolder);
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick("cxcy", cxcycaDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", 12, cxcyDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", 1, cxcyDCB, cursorPositionDCHolder);
    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", 1, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner(cxDCB);
    cxcyDCB.putInner(cyDCB);
    cxcycaDCB.putInner(cxcyDCB);
    cxcycaDCB.putInner(caDCB);
    cursorPositionDCB.putInner(niDCB);
    cursorPositionDCB.putInner(cxcycaDCB);

    //cxDCB.remove works when cxDCB was set
    Result r = cxDCB.remove(cursorPositionDCHolder);
    assertNull(cxDCB.getDFB());
    assertNull(r.getError());

    //cyDCB.remove works when cyDCB was unset
    cyDCB.remove(cursorPositionDCHolder);
    r = cyDCB.remove(cursorPositionDCHolder);
    assertNull(cyDCB.getDFB());
    assertNull(r.getError());

    //cxcyDCB.remove works when cxDCB and cyDCB are set
    int cx = 10;
    int cy = 1;
    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", cx, cxcyDCB, cursorPositionDCHolder);
    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", cy, cxcyDCB, cursorPositionDCHolder);
    cxcyDCB.putInner(cxDCB);
    cxcyDCB.putInner(cyDCB);
    cxcyDCB.remove(cursorPositionDCHolder);
    assertNull(cxDCB.getDFB());
    assertNull(cyDCB.getDFB());
    assertFalse(cxcyDCB.isComplete());

  }

//  @Test void dcbGet() {
//    int cx = 3;
//    CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(null, cursorPositionDCHolder);
//    ArrayList<Integer> newlineIndices = new ArrayList<>();
//    newlineIndices.add(11);
//    newlineIndices.add(24);
//    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick(cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cxcycaDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cx, cxcyDCB, cursorPositionDCHolder);
//
//    //cxDCB.get produces accurate cx val
//    Result r = cxDCB.get();
//    assertEquals(cx, r.getVal());
//
//    //cyDCB.get accurately fetches an unset dcb, and includes a result with an error message
//    Result r = cyDCB
//
//  }

}
