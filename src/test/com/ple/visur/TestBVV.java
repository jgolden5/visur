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

  }

}
