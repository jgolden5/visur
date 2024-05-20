package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  final CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick();

  @Test void dcMakeBrick() {
    //cursorPositionDC.makeBrick
    CompoundDataClassBrick cxcycaDCB = (CompoundDataClassBrick) cursorPositionDCB.getInner("cxcyca");
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick)cxcycaDCB.getInner("cxcy");
    assertFalse(cursorPositionDCB.isComplete());
    //test cxcycaDC.makeBrick
    assertFalse(cursorPositionDCB.getInner("cxcyca").isComplete());
    //test wholeNumberListDC.makeBrick
    assertFalse(cursorPositionDCB.getInner("ni").isComplete());
    //test wholePairDC.makeBrick
    assertFalse(cxcycaDCB.getInner("cxcy").isComplete());
    //test wholeNumberDC.makeBrick
    assertFalse(cxcycaDCB.getInner("ca").isComplete());
    assertFalse(cxcyDCB.getInner("cx").isComplete());
    assertFalse(cxcyDCB.getInner("cy").isComplete());
    //makeBrick should set outer of current inner being constructed
    assertEquals(cxcycaDCB, cxcyDCB.getOuter());
    DataClassBrick caDCB = cxcycaDCB.getInner("ca");
    assertEquals(cxcycaDCB, caDCB.getOuter());
    //ensure that all values are set after cursorPositionDC.makeBrick gets called initially

  }


  @Test void dcbPutSafe() {
    int cx = 4;
    int cy = 0;
    CompoundDataClassBrick cxcycaDCB = (CompoundDataClassBrick) cursorPositionDCB.getInner("cxcyca");
    CompoundDataClassBrick cxcyDCB = (CompoundDataClassBrick) cxcycaDCB.getInner("cxcy");
    PrimitiveDataClassBrick cxDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) cxcyDCB.getInner("cy");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) cxcycaDCB.getInner("ca");

    //cxcy can be set when ca is unset
    Result r = cxDCB.putSafe(cx);
    assertNull(r.getError());
    r = cyDCB.putSafe(cy);
    assertNull(r.getError());
    assertEquals(cx, cxDCB.get().getVal());
    assertEquals(cy, cyDCB.get().getVal());

    //ni can be set always (because cursorPositionDC.minimumRequiredSetValues == 2)
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
    r = niDCB.putSafe(newlineIndices);
    assertNull(r.getError());
    assertEquals(newlineIndices, niDCB.get().getVal());

    //ca can be set when cxcy is set and no conflicts exist
    int ca = 4;
    r = caDCB.putSafe(ca);
    assertNull(r.getError());
    assertEquals(ca, caDCB.get().getVal());

    //ca CAN'T be set when cxcy is set and conflicts DO exist
    int previousCA = ca;
    ca = 14;
    r = caDCB.putSafe(ca);
    assertNotNull(r.getError());
    assertEquals(previousCA, caDCB.get().getVal());

    //cxcy can be set when ca is set and no conflicts exist
    cxDCB.remove();
    cyDCB.remove();
    caDCB.putSafe(ca);
    cx = 2;
    cy = 1;
    r = cxDCB.putSafe(cx);
    assertNull(r.getError());
    r = cyDCB.putSafe(cy);
    assertNull(r.getError());
    assertEquals(cx, cxDCB.get().getVal());
    assertEquals(cy, cyDCB.get().getVal());

    //cxcy CAN'T be set when ca is set and conflicts DO exist
    int previousCX = cx;
    int previousCY = cy;
    cx = 1;
    cy = 0;
    r = cxDCB.putSafe(cx);
    assertNotNull(r.getError());
    r = cyDCB.putSafe(cy);
    assertNotNull(r.getError());
    assertEquals(previousCX, cxDCB.get().getVal());
    assertEquals(previousCY, cyDCB.get().getVal());

  }

//  @Test void pdcbGet() {
//    int cx = 3;
//    int cy = 1;
//    CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition",  null, cursorPositionDCHolder);
//    ArrayList<Integer> newlineIndices = new ArrayList<>();
//    newlineIndices.add(11);
//    newlineIndices.add(24);
//    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick("cxcyca", cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick("cxcy", cxcycaDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", cx, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", cy, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", null, cxcycaDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    cursorPositionDCB.putInner(niDCB);
//    cursorPositionDCB.putInner(cxcycaDCB);
//
//    //cxDCB.get produces accurate cx val
//    Result r = cxDCB.get();
//    assertEquals(cx, r.getVal());
//
//    //cyDCB.get accurately fetches an unset dcb, and includes a result with an error message
//    cyDCB.remove();
//    r = cyDCB.get();
//    assertNotNull(r.getError());
//    assertFalse(cyDCB.isComplete());
//
//    //caDCB.get() returns null when cxcycaDCB is complete but ca is unset
//    r = caDCB.get();
//    assertNull(r.getVal());
//    assertNotNull(r.getError());
//
//    //caDCB.get() returns ca int val when cxcycaDCB is complete and ca is set
//    cxcyDCB.remove();
//    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", 10, cxcycaDCB, cursorPositionDCHolder);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    assertFalse(cxcyDCB.isComplete());
//    assertTrue(caDCB.isComplete());
//    assertTrue(cxcycaDCB.isComplete());
//    r = caDCB.get(); //actual test
//    assertEquals(10, r.getVal());
//    assertNull(r.getError());
//
//    //niDCB.get() returns newlineIndices arrayList exactly as declared when niDCB is complete
//    assertTrue(niDCB.isComplete());
//    r = niDCB.get(); //actual test
//    assertEquals(newlineIndices, r.getVal());
//    assertNull(r.getError());
//
//  }

//  @Test void dcbRemove() {
//    CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition", null, cursorPositionDCHolder);
//    ArrayList<Integer> newlineIndices = new ArrayList<>();
//    newlineIndices.add(11);
//    newlineIndices.add(24);
//    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick("cxcyca", cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick("cxcy", cxcycaDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", 12, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", 1, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", 1, cxcycaDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    cursorPositionDCB.putInner(niDCB);
//    cursorPositionDCB.putInner(cxcycaDCB);
//
//    //cxDCB.remove works when cxDCB was set
//    Result r = cxDCB.remove();
//    assertNull(cxDCB.getDFB());
//    assertNull(r.getError());
//
//    //cyDCB.remove works when cyDCB was unset
//    cyDCB.remove();
//    r = cyDCB.remove();
//    assertNull(cyDCB.getDFB());
//    assertNull(r.getError());
//
//    //cxcyDCB.remove works when complete
//    int cx = 10;
//    int cy = 1;
//    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", cx, cxcyDCB, cursorPositionDCHolder);
//    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", cy, cxcyDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    assertTrue(cxcyDCB.isComplete());
//    cxcyDCB.remove();
//    assertNull(cxDCB.getDFB());
//    assertNull(cyDCB.getDFB());
//    assertFalse(cxcyDCB.isComplete());
//
//    //cxcyDCB.remove works when incomplete
//    cxcyDCB.remove();
//    assertNull(cxDCB.getDFB());
//    assertNull(cyDCB.getDFB());
//    assertFalse(cxcyDCB.isComplete());
//
//    //caDCB.remove works when complete
//    assertTrue(caDCB.isComplete());
//    caDCB.remove();
//    assertNull(caDCB.getDFB()); //in future tests, this condition will be replaced by the next line, but this ensures they both work the same
//    assertFalse(caDCB.isComplete());
//
//    //cxcycaDCB.remove works when complete [cxcy complete, ca incomplete]
//    cx = 0;
//    cy = 2;
//    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", cx, cxcyDCB, cursorPositionDCHolder);
//    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", cy, cxcyDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    assertFalse(caDCB.isComplete());
//    assertTrue(cxcyDCB.isComplete());
//    assertTrue(cxcycaDCB.isComplete());
//    assertTrue(niDCB.isComplete());
//    cxcycaDCB.remove(); //actual test post-setup starts here
//    assertFalse(caDCB.isComplete());
//    assertFalse(cxcyDCB.isComplete());
//    assertFalse(cxcycaDCB.isComplete());
//    assertTrue(niDCB.isComplete());
//
//    //niDCB.remove works when niDCB is set
//    assertTrue(niDCB.isComplete());
//    niDCB.remove();
//    assertFalse(niDCB.isComplete());
//
//    //cursorPositionDCB.remove works when cursorPositionDCB is set
//    cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition", null, cursorPositionDCHolder);
//    newlineIndices = new ArrayList<>();
//    newlineIndices.add(11);
//    newlineIndices.add(24);
//    niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
//    cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick("cxcyca", cursorPositionDCB, cursorPositionDCHolder);
//    cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick("cxcy", cxcycaDCB, cursorPositionDCHolder);
//    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", 12, cxcyDCB, cursorPositionDCHolder);
//    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", 1, cxcyDCB, cursorPositionDCHolder);
//    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", 1, cxcycaDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB); cursorPositionDCB.putInner(niDCB);
//    cursorPositionDCB.putInner(cxcycaDCB);
//    assertTrue(cursorPositionDCB.isComplete());
//    cursorPositionDCB.remove(); //actual test begins here
//    assertFalse(niDCB.isComplete());
//    assertFalse(cxcycaDCB.isComplete());
//    assertFalse(cxcyDCB.isComplete());
//    assertFalse(cxDCB.isComplete());
//    assertFalse(cyDCB.isComplete());
//    assertFalse(caDCB.isComplete());
//    assertFalse(cursorPositionDCB.isComplete());
//
//    //cursorPositionDCB.remove works when cursorPositionDCB is unset
//    cursorPositionDCB.remove();
//    assertFalse(cursorPositionDCB.isComplete());
//  }

}
