package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestCursorPositionDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  CompoundDataClassBrick cursorPositionDCB;
  PrimitiveDataClassBrick niDCB;
  PrimitiveDataClassBrick cwDCB;
  CompoundDataClassBrick coordinatesDCB;
  PrimitiveDataClassBrick caDCB;
  CompoundDataClassBrick longCXCYDCB;
  PrimitiveDataClassBrick longCXDCB;
  PrimitiveDataClassBrick longCYDCB;


  @BeforeEach
  void setupDCBs() {
    cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick();
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    int canvasWidth = 5;
    niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
    cwDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("cw");
    coordinatesDCB = (CompoundDataClassBrick) cursorPositionDCB.getInner("coordinates");
    longCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("longCXCY");
    longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    caDCB = (PrimitiveDataClassBrick) coordinatesDCB.getInner("ca");

    Result r = niDCB.putSafe(newlineIndices);
    assertNull(r.getError());

    r = cwDCB.putSafe(canvasWidth);
    assertNull(r.getError());

    assertFalse(cursorPositionDCB.isComplete());
  }

  @Test void caAndLongCXCYPutSafe() {
    //1 = longCXCY can be set when ca is unset
    int longCX = 4;
    int longCY = 0;
    Result r = longCXDCB.putSafe(longCX);
    assertNull(r.getError());
    r = longCYDCB.putSafe(longCY);
    assertNull(r.getError());
    assertEquals(longCX, longCXDCB.get().getVal());
    assertEquals(longCY, longCYDCB.get().getVal());

    //2 = ni can be set always (because cursorPositionDC.minimumRequiredSetValues == 3 and cw is already set by this point)
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    r = niDCB.putSafe(newlineIndices);
    assertNull(r.getError());
    assertEquals(newlineIndices, niDCB.get().getVal());

    //3 = cw can be set always (just like ni)
    int canvasWidth = 5;
    cwDCB.putSafe(canvasWidth);

    //4 = ca can be set when longCXCY is set and no conflicts exist
    int ca = 4;
    r = caDCB.putSafe(ca);
    assertNull(r.getError());
    assertEquals(ca, caDCB.get().getVal());

    //5 = ca CAN'T be set when longCXCY is set and conflicts DO exist
    int previousCA = ca;
    ca = 14;
    r = caDCB.putSafe(ca);
    assertNotNull(r.getError());
    assertEquals(previousCA, caDCB.get().getVal());

    //6 = longCXCY can be set when ca is set and no conflicts exist
    longCXDCB.remove();
    longCYDCB.remove();
    caDCB.putSafe(ca);
    longCX = 2;
    longCY = 1;
    r = longCXDCB.putSafe(longCX);
    assertNull(r.getError());
    assertTrue(longCXDCB.isComplete());
    assertFalse(longCYDCB.isComplete());
    assertFalse(longCXCYDCB.isComplete());
    assertTrue(caDCB.isComplete());
    r = longCYDCB.putSafe(longCY);
    assertNull(r.getError());
    assertEquals(longCX, longCXDCB.get().getVal());
    assertEquals(longCY, longCYDCB.get().getVal());

    //7 = longCXCY CAN'T be set when ca is set and conflicts DO exist
    int previousCX = longCX;
    int previousCY = longCY;
    longCX = 1;
    longCY = 0;
    r = longCXDCB.putSafe(longCX);
    assertNotNull(r.getError());
    r = longCYDCB.putSafe(longCY);
    assertNotNull(r.getError());
    assertEquals(previousCX, longCXDCB.get().getVal());
    assertEquals(previousCY, longCYDCB.get().getVal());

    //8 = ca can be set when longCXCY is unset
    longCXDCB.remove();
    longCYDCB.remove();
    ca = 30;
    r = caDCB.putSafe(ca);
    assertNull(r.getError());
    assertEquals(ca, caDCB.get().getVal());

  }

  @Test void caAndLongCXCYPutForce() {
    //1 = longCXCY can be set when ca is unset
    int longCX = 10;
    int longCY = 0;
    longCXDCB.putForce(longCX);
    longCYDCB.putForce(longCY);
    assertEquals(longCX, longCXDCB.get().getVal());
    assertEquals(longCY, longCYDCB.get().getVal());

    //2 = ni can be force put without removing any other values
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    niDCB.putForce(newlineIndices);
    assertEquals(newlineIndices, niDCB.get().getVal());

    //3 = cw can be force put without removing any other values (just like ni)
    int canvasWidth = 5;
    cwDCB.putForce(canvasWidth);

    //4 = ca can be set when longCXCY is set and no conflicts exist
    int ca = 10;
    caDCB.putForce(ca);
    assertEquals(ca, caDCB.get().getVal());

    //5 = ca CAN be set when longCXCY is set and conflicts DO exist, but longCXCY needs to be UNSET
    ca = 14;
    caDCB.putForce(ca);
    assertFalse(longCXCYDCB.isComplete());
    assertFalse(longCXDCB.isComplete());
    assertFalse(longCYDCB.isComplete());
    assertEquals(14, caDCB.get().getVal());

    //6 = longCXCY can be set when ca is set and no conflicts exist
    longCXDCB.remove();
    longCYDCB.remove();
    caDCB.putForce(ca);
    longCX = 2;
    longCY = 1;
    longCXDCB.putForce(longCX);
    assertTrue(caDCB.isComplete());
    longCYDCB.putForce(longCY);
    assertEquals(longCX, longCXDCB.get().getVal());
    assertEquals(longCY, longCYDCB.get().getVal());
    assertTrue(caDCB.isComplete());

    //7 = longCXCY CAN be set when ca is set and conflicts DO exist, but ca needs to be UNSET
    longCX = 1;
    longCY = 0;
    longCXDCB.putForce(longCX);
    assertTrue(longCXCYDCB.isComplete());
    longCYDCB.putForce(longCY);
    assertTrue(longCXCYDCB.isComplete());
    assertEquals(longCX, longCXDCB.get().getVal());
    assertEquals(longCY, longCYDCB.get().getVal());
    assertFalse(caDCB.isComplete());

    //8 = ca can be set when longCXCY is unset
    longCXDCB.remove();
    longCYDCB.remove();
    ca = 30;
    caDCB.putForce(ca);
    assertEquals(ca, caDCB.get().getVal());

  }

  @Test void caAndLongCXCYGetOrCalc() {
    //1 = caDCB.getOrCalc when caDCB is set to 0
    int ca = 0;
    caDCB.putForce(ca);
    Result r = caDCB.getOrCalc();
    caDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(ca, caDCB.getVal());

    assertEquals(caDCB, longCXDCB.getOuter().getOuter().getInner("ca"));

    //2 = caDCB.getOrCalc when longCXCYDCB is set to (5, 0) [should be 5]
    int longCX = 5;
    int longCY = 0;

    assertTrue(caDCB.isComplete());
    longCXDCB.putForce(longCX);
    longCYDCB.putForce(longCY);
    assertTrue(longCXCYDCB.isComplete());
    assertFalse(caDCB.isComplete());
    r = caDCB.getOrCalc();
    assertNull(r.getError());
    caDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(5, caDCB.get().getVal());

    //3 = longCXCYDCB.getOrCalc when longCXCYDCB is set to (5, 0)
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    longCXDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(5, longCXDCB.get().getVal());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    longCYDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(0, longCYDCB.get().getVal());

    //4 = longCXCYDCB.getOrCalc when caDCB is set to 12 [should be (0, 1)]
    caDCB.putForce(12);
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    longCXDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(0, longCXDCB.get().getVal());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    longCYDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(1, longCYDCB.get().getVal());

    //5 = longCXCYDCB.getOrCalc when caDCB is set to 13 [should be (1, 1)]
    caDCB.putForce(13);
    assertFalse(longCXCYDCB.isComplete());
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    longCXDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(1, longCXDCB.get().getVal());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    longCYDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(1, longCYDCB.get().getVal());

    //6 = longCXCYDCB.getOrCalc when caDCB is set to 22 [should be (10, 1)]
    assertTrue(longCXCYDCB.isComplete());
    caDCB.putForce(22);
    assertFalse(longCXCYDCB.isComplete());
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    longCXDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(10, longCXDCB.get().getVal());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    longCYDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(1, longCYDCB.get().getVal());

    //7 = caDCB.getOrCalc when longCXCYDCB is set to (4, 2) [should be 29]
    longCXDCB.putForce(4);
    longCYDCB.putForce(2);
    assertTrue(longCXCYDCB.isComplete());
    assertFalse(caDCB.isComplete());
    r = caDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(29, caDCB.get().getVal());

    //8 = longCXCYDCB.getOrCalc when caDCB is set to 10 [should be (10, 0)]
    caDCB.putForce(10);
    assertTrue(caDCB.isComplete());
    assertFalse(longCXDCB.isComplete());
    assertFalse(longCYDCB.isComplete());
    assertFalse(longCXCYDCB.isComplete());
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(10, longCXDCB.get().getVal());
    assertEquals(0, longCYDCB.get().getVal());

    //9 = caDCB.getOrCalc when all values are unset [should be an error]
    r = longCXCYDCB.remove();
    assertNull(r.getError());
    r = caDCB.remove();
    assertNull(r.getError());
    r = caDCB.getOrCalc();
    assertNotNull(r.getError());
    assertNull(r.getVal());

    //10 = longCXCYDCB.getOrCalc when all values are unset [should be an error]
    r = longCXDCB.getOrCalc();
    assertNotNull(r.getError());
    assertNull(r.getVal());
    r = longCYDCB.getOrCalc();
    assertNotNull(r.getError());
    assertNull(r.getVal());

    //11 = niDCB.getOrCalc should simply fetch newlineIndices without unsetting cw or coordinates
    caDCB.putSafe(0);
    ArrayList<Integer> newlineIndices = (ArrayList<Integer>) niDCB.getVal();
    r = niDCB.getOrCalc();
    assertNull(r.getError());
    niDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(niDCB.getVal(), newlineIndices);
    assertTrue(cwDCB.isComplete());
    assertTrue(coordinatesDCB.isComplete());

  }

}
