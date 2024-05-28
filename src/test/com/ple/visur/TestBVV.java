package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestBVV {

  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  CompoundDataClassBrick cursorPositionDCB;
  PrimitiveDataClassBrick niDCB;
  CompoundDataClassBrick cxcycaDCB;
  PrimitiveDataClassBrick caDCB;
  CompoundDataClassBrick cxcyDCB;
  PrimitiveDataClassBrick cxDCB;
  PrimitiveDataClassBrick cyDCB;
  BrickVisurVar cxBVV;
  BrickVisurVar cyBVV;
  BrickVisurVar caBVV;


  @BeforeEach void setupDCBsAndBVVs() {
    cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick();
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
    cxcycaDCB = (CompoundDataClassBrick) cursorPositionDCB.getInner("cxcyca");
    cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");

    Result r = niDCB.putSafe(newlineIndices);
    assertNull(r.getError());

    assertFalse(cursorPositionDCB.isComplete());

    cxBVV = BrickVisurVar.make(cxDCB);
    cyBVV = BrickVisurVar.make(cyDCB);
    caBVV = BrickVisurVar.make(caDCB);

    assertTrue(cxDCB instanceof PrimitiveDataClassBrick);
    assertTrue(cyDCB instanceof PrimitiveDataClassBrick);
    assertTrue(caDCB instanceof PrimitiveDataClassBrick);
  }

  @Test void putValGetVal() {
    //1 = cxBVV.putVal(0); r.getError should = null, and cxBVV.getVal() should = 0
    Result r = cxBVV.putVal(0);
    assertNull(r.getError());
    int cx = (int)cxBVV.getVal();
    assertEquals(0, cx);

    //2 = cyBVV.putVal(1); r.getError should = null, and cyBVV.getVal should = 1, and cxBVV.getVal should still = 0
    r = cyBVV.putVal(1);
    assertNull(r.getError());
    int cy = (int)cyBVV.getVal();
    assertEquals(1, cy);

    //3 = caBVV.putVal(12); r.getError should = null, and caBVV.getVal should = 0 since it does not conflict
    r = caBVV.putVal(12);
    assertNull(r.getError());
    int ca = (int)caBVV.getVal();
    assertEquals(12, ca);
    assertTrue(cxBVV.isComplete());
    assertTrue(cyBVV.isComplete());

    //4 = caBVV.putVal(11); r.getError should == null, caBVV.getVal should = 11, but cxBVV and cyBVV.isComplete should = false
    r = caBVV.putVal(11);
    assertNull(r.getError());
    ca = (int)caBVV.getVal();
    assertEquals(11, ca);
    assertFalse(cxBVV.isComplete());
    assertFalse(cyBVV.isComplete());

    //5 = cxBVV.putVal(11); cyBVV.putVal(0); r.getError for both should == null; caBVV.getVal should still = 11;
    r = cxBVV.putVal(11);
    assertNull(r.getError());
    r = cyBVV.putVal(0);
    assertNull(r.getError());
    cx = (int)cxBVV.getVal();
    cy = (int)cyBVV.getVal();
    ca = (int)caBVV.getVal();
    assertEquals(11, cx);
    assertEquals(0, cy);
    assertEquals(11, ca);
    assertTrue(cxBVV.isComplete());
    assertTrue(cyBVV.isComplete());

    //6 = cxBVV.putVal(2); cyBVV.putVal(1); r.getError for both should == null; caBVV.isComplete should = false
    r = cxBVV.putVal(2);
    assertNull(r.getError());
    r = cyBVV.putVal(1);
    assertNull(r.getError());
    cx = (int)cxBVV.getVal();
    cy = (int)cyBVV.getVal();
    assertEquals(2, cx);
    assertEquals(1, cy);
    assertFalse(caBVV.isComplete());

  }

}
