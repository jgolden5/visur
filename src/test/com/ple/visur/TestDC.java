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
    CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick(null, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.cursorPositionDC, cursorPositionDCB.getCDC());
    assertNull(cursorPositionDCB.getOuter());
    assertEquals("cursorPosition", cursorPositionDCB.getName());

    //cxcycaDC.makeBrick works
    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick(cursorPositionDCB, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.cxcycaDC, cxcycaDCB.getCDC());
    assertEquals(cursorPositionDCHolder.cursorPositionDC, cxcycaDCB.getOuter().getCDC());
    assertEquals("cxcyca", cxcycaDCB.getName());

    //cxcyDC.makeBrick works
    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick(cxcycaDCB, cursorPositionDCHolder);
    cxcyDCB.putName("cxcy"); //name only needs to be put manually in the case that the dc is meant to be more generic
    assertEquals(cursorPositionDCHolder.wholePairDC, cxcyDCB.getCDC());
    assertEquals(cursorPositionDCHolder.cxcycaDC, cxcyDCB.getOuter().getCDC());
    assertEquals("cxcy", cxcyDCB.getName());

    //pdc makeBrick tests
    //niDC.makeBrick works
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick(newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
    niDCB.putName("ni");
    assertEquals(cursorPositionDCHolder.wholeNumberListDC, niDCB.getPDC());
    assertEquals(newlineIndices, niDCB.getDFB().getVal());
    assertEquals(cursorPositionDCHolder.cursorPositionDC, niDCB.getOuter().getCDC());
    assertEquals("ni", niDCB.getName());

    //wholeNumberDC.makeBrick works for cxDCB
    int cx = 0;
    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cx, cxcyDCB, cursorPositionDCHolder);
    cxDCB.putName("cx");
    assertEquals(cursorPositionDCHolder.wholeNumberDC, cxDCB.getPDC());
    assertEquals(cx, cxDCB.getDFB().getVal());
    assertEquals(cursorPositionDCHolder.wholePairDC, cxDCB.getOuter().getCDC());
    assertEquals("cx", cxDCB.getName());

    //wholeNumberDC.makeBrick works for cyDCB
    int cy = 1;
    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cy, cxcyDCB, cursorPositionDCHolder);
    cyDCB.putName("cy");
    assertEquals(cursorPositionDCHolder.wholeNumberDC, cyDCB.getPDC());
    assertEquals(cy, cyDCB.getDFB().getVal());
    assertEquals(cursorPositionDCHolder.wholePairDC, cyDCB.getOuter().getCDC());
    assertEquals("cy", cyDCB.getName());

    //wholeNumberDC.makeBrick works for caDCB
    int ca = 2;
    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(ca, cxcycaDCB, cursorPositionDCHolder);
    caDCB.putName("ca");
    assertEquals(cursorPositionDCHolder.wholeNumberDC, caDCB.getPDC());
    assertEquals(ca, caDCB.getDFB().getVal());
    assertEquals(cursorPositionDCHolder.cxcycaDC, caDCB.getOuter().getCDC());
    assertEquals("ca", caDCB.getName());
  }

  @Test void dcbRemove() {

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
