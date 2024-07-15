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
  CompoundDataClassBrick shortCXCYDCB;
  PrimitiveDataClassBrick shortCXDCB;
  PrimitiveDataClassBrick shortCYDCB;


  @BeforeEach
  void setupDCBs() {
    cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick();
    ArrayList<Integer> newlineIndices = new ArrayList<>();
    newlineIndices.add(11);
    newlineIndices.add(24);
    newlineIndices.add(32);
    int canvasWidth = 5;
    niDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("ni");
    cwDCB = (PrimitiveDataClassBrick) cursorPositionDCB.getInner("cw");
    coordinatesDCB = (CompoundDataClassBrick) cursorPositionDCB.getInner("coordinates");
    longCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("longCXCY");
    longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    shortCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("shortCXCY");
    shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCX");
    shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCY");
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

  @Test void caAndShortCXCYPutSafe() {
    //1 = shortCXCY can be set when ca is unset
    Result r = shortCXDCB.putSafe(0);
    assertNull(r.getError());
    r = shortCYDCB.putSafe(0);
    assertNull(r.getError());
    assertTrue(shortCXCYDCB.isComplete());
    assertTrue(shortCXDCB.isComplete());
    assertTrue(shortCYDCB.isComplete());
    assertEquals(0, shortCXDCB.getVal());
    assertEquals(0, shortCYDCB.getVal());

    //2 = ca can be set when shortCXCY is set and no conflicts exist
    r = caDCB.putSafe(0);
    assertNull(r.getError());
    assertEquals(0, caDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());

    //3 = ca CAN'T be set when shortCXCY is set and conflicts DO exist
    //a
    r = caDCB.putSafe(3);
    assertNotNull(r.getError());
    assertEquals(0, caDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());

    //b
    r = caDCB.putSafe(17);
    assertNotNull(r.getError());
    assertEquals(0, caDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());

    //c
    r = caDCB.putSafe(31);
    assertNotNull(r.getError());
    assertEquals(0, caDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());

    //4 = shortCXCY can be set when ca is set and no conflicts exist
    shortCXCYDCB.remove();
    assertFalse(shortCXCYDCB.isComplete());
    r = caDCB.putSafe(15);
    assertNull(r.getError());

    r = shortCXDCB.putSafe(3);
    assertNull(r.getError());
    assertEquals(3, shortCXDCB.getVal());
    shortCYDCB.putSafe(3);
    assertNull(r.getError());
    assertEquals(3, shortCYDCB.getVal());
    assertTrue(caDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());

    //5 = shortCXCY CAN'T be set when ca is set and conflicts DO exist
    //a
    r = shortCXDCB.putSafe(3);
    assertNull(r.getError());
    assertEquals(3, shortCXDCB.getVal());
    r = shortCYDCB.putSafe(4);
    assertNotNull(r.getError());
    assertEquals(3, shortCYDCB.getVal());
    assertTrue(caDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());

    //b
    r = shortCXDCB.putSafe(0);
    assertNotNull(r.getError());
    assertEquals(3, shortCXDCB.getVal());
    assertTrue(caDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());

    //c
    r = shortCXCYDCB.remove();
    assertNull(r.getError());
    assertFalse(shortCXCYDCB.isComplete());
    r = caDCB.putSafe(4);
    assertNull(r.getError());
    r = shortCYDCB.putSafe(0);
    assertNull(r.getError());
    assertEquals(0, shortCYDCB.getVal());
    r = shortCXDCB.putSafe(3);
    assertNotNull(r.getError());
    assertFalse(shortCXCYDCB.isComplete());
    assertTrue(caDCB.isComplete());

    //6 = ca can be set when shortCXCY is unset
    r = shortCXCYDCB.remove();
    assertNull(r.getError());
    r = caDCB.putSafe(32);
    assertNull(r.getError());
    assertEquals(32, caDCB.getVal());

  }

  @Test void longCXCYAndShortCXCYPutSafe() {
    //1 = longCXCY can be set when shortCXCY is unset
    assertFalse(shortCXCYDCB.isComplete());
    longCXDCB.putSafe(6);
    assertEquals(6, longCXDCB.getVal());
    longCYDCB.putSafe(1);
    assertEquals(1, longCYDCB.getVal());

    //2 = shortCXCY can be set when longCXCY is set and no conflicts exist
    shortCXDCB.putSafe(1);
    assertEquals(1, shortCXDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());
    shortCYDCB.putSafe(4);
    assertEquals(4, shortCYDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());

    //3 = shortCXCY CAN'T be set when longCXCY is set and conflicts DO exist
    shortCXDCB.putSafe(1);
    assertEquals(1, shortCXDCB.getVal());
    assertTrue(longCXDCB.isComplete());
    Result r = shortCYDCB.putSafe(3);
    assertNotNull(r.getError());
    assertEquals(4, shortCYDCB.getVal());

    //4 = shortCXCY can be set when longCXCY is unset
    longCXCYDCB.remove();
    assertFalse(longCXCYDCB.isComplete());
    shortCXDCB.putSafe(4);
    assertEquals(4, shortCXDCB.getVal());
    shortCYDCB.putSafe(4);
    assertEquals(4, shortCYDCB.getVal());

    //5 = longCXCY can be set when shortCXCY is set and no conflicts exist
    longCXDCB.putSafe(9);
    assertEquals(9, longCXDCB.getVal());
    longCYDCB.putSafe(1);
    assertEquals(1, longCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());

    //6 = longCXCY CAN'T be set when shortCXCY is set and conflicts DO exist
    longCYDCB.putSafe(0);
    assertEquals(1, longCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());

    //7 = longCXCY can be set when shortCXCY is set to something on the first short line and no conflicts exist
    longCXCYDCB.remove();
    assertFalse(longCXCYDCB.isComplete());
    shortCXDCB.putSafe(2);
    shortCYDCB.putSafe(0);
    assertTrue(shortCXCYDCB.isComplete());
    longCXDCB.putSafe(2);
    assertEquals(2, longCXDCB.getVal());
    longCYDCB.putSafe(0);
    assertEquals(0, longCYDCB.getVal());

  }

  @Test void longCXCYAndShortCXCYPutForce() {
    //1 = longCXCY can be set when shortCXCY is unset
    assertFalse(shortCXCYDCB.isComplete());
    longCXDCB.putForce(0);
    assertEquals(0, longCXDCB.getVal());
    longCYDCB.putForce(1);
    assertEquals(1, longCYDCB.getVal());
    assertFalse(shortCXCYDCB.isComplete());

    //2 = shortCXCY can be set when longCXCY is set and no conflicts exist, but even then, longCXCY gets UNSET
    assertTrue(longCXCYDCB.isComplete());
    shortCXDCB.putForce(0);
    assertEquals(0, shortCXDCB.getVal());
    assertFalse(longCXCYDCB.isComplete());
    shortCYDCB.putForce(1);
    assertEquals(1, shortCYDCB.getVal());
    assertFalse(longCXCYDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());

    //3 = longCXCY can be set when shortCXCY is unset
    shortCXCYDCB.remove();
    assertFalse(shortCXDCB.isComplete());
    assertFalse(shortCYDCB.isComplete());
    longCXDCB.putForce(0);
    assertEquals(0, longCXDCB.getVal());
    longCYDCB.putForce(3);
    assertEquals(3, longCYDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());

    //4 = shortCXCY can be set when longCXCY is set and no conflicts exist, but longCXCY gets UNSET
    shortCXDCB.putForce(1);
    assertEquals(1, shortCXDCB.getVal());
    assertFalse(longCXCYDCB.isComplete());
    shortCYDCB.putForce(3);
    assertEquals(3, shortCYDCB.getVal());

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

    //6 = setting longCXCY will unset ca when ca is set, even if no conflicts exist
    longCXDCB.remove();
    longCYDCB.remove();
    caDCB.putForce(ca);
    longCX = 2;
    longCY = 1;
    longCXDCB.putForce(longCX);
    assertFalse(caDCB.isComplete());
    assertEquals(2, longCXDCB.getVal());
    longCYDCB.putForce(longCY);
    assertEquals(longCY, longCYDCB.get().getVal());
    assertFalse(caDCB.isComplete());

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

  @Test void caAndShortCXCYPutForce() {
    //1 = shortCXCY can be set when ca is unset
    shortCXDCB.putForce(1);
    assertEquals(1, shortCXDCB.getVal());
    shortCYDCB.putForce(3);
    assertTrue(shortCXCYDCB.isComplete());
    assertEquals(3, shortCYDCB.getVal());
    assertFalse(caDCB.isComplete());

    //2 = ca can be set when shortCXCY is set and no conflicts exist
    caDCB.putForce(13);
    assertEquals(13, caDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertEquals(1, shortCXDCB.getVal());
    assertEquals(3, shortCYDCB.getVal());

    //3 = ca can be set when shortCXCY is set and conflicts DO exist, but shortCXCY needs to be UNSET
    assertTrue(shortCXCYDCB.isComplete());
    caDCB.putForce(18);
    assertEquals(18, caDCB.getVal());
    assertFalse(shortCXCYDCB.isComplete());

    //4 = setting shortCX or shortCY will ALWAYS remove ca,
    // because it cannot be proven that shortCX will not conflict with ca once shortCY is set, for example
    assertTrue(caDCB.isComplete());
    shortCXDCB.putForce(1);
    assertFalse(caDCB.isComplete());
    assertEquals(1, shortCXDCB.getVal());

    //5 = setting caDCB when shortCXCY is incomplete will likewise ALWAYS remove all inners from shortCXCY
    assertTrue(shortCXDCB.isComplete());
    assertFalse(shortCXCYDCB.isComplete());
    caDCB.putForce(18);
    assertFalse(shortCXDCB.isComplete());
    assertEquals(18, caDCB.getVal());

    //6 = shortCXCY can be set when ca is set and conflicts DO exist, but ca needs to be UNSET
    shortCXDCB.putForce(0);
    assertFalse(caDCB.isComplete());
    assertEquals(0, shortCXDCB.getVal());
    shortCYDCB.putForce(3);
    assertEquals(3, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());

    //7 = ca can be set when shortCXCY is unset
    shortCXCYDCB.remove();
    assertFalse(shortCXCYDCB.isComplete());
    caDCB.putForce(20);
    assertEquals(20, caDCB.getVal());

  }

  @Test void allThreeCoordinatesPutSafe() {
    //1 = shortCXCY can be set if ca and longCXCY are set and no conflicts exist
    //1/2 - setup
    assertFalse(caDCB.isComplete());
    assertFalse(longCXCYDCB.isComplete());
    assertFalse(shortCXCYDCB.isComplete());
    caDCB.putSafe(14);
    assertEquals(14, caDCB.getVal());
    longCXDCB.putSafe(2);
    assertEquals(2, longCXDCB.getVal());
    longCYDCB.putSafe(1);
    assertEquals(1, longCYDCB.getVal());
    assertTrue(caDCB.isComplete());
    assertTrue(longCXCYDCB.isComplete());

    //2/2 - actual test
    shortCXDCB.putSafe(2);
    assertEquals(2, shortCXDCB.getVal());
    assertTrue(caDCB.isComplete());
    assertTrue(longCXCYDCB.isComplete());
    shortCYDCB.putSafe(3);
    assertEquals(3, shortCYDCB.getVal());
    assertTrue(caDCB.isComplete());
    assertTrue(longCXCYDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());

    //2 = shortCXCY CAN'T be set if ca and longCXCY are set and conflicts DO exist
    Result r = shortCXDCB.putSafe(0);
    assertNotNull(r.getError());
    assertEquals(2, shortCXDCB.getVal());

    //3 = longCXCY can be set if ca and shortCXCY are set and no conflicts exist
    r = longCXDCB.putSafe(2);
    assertNull(r.getError());
    assertEquals(2, longCXDCB.getVal());
    r = longCYDCB.putSafe(1);
    assertNull(r.getError());
    assertEquals(1, longCYDCB.getVal());
    assertTrue(caDCB.isComplete());
    assertTrue(longCXCYDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());

    //4 = longCXCY CAN'T be set if ca and shortCXCY are set and a conflict exists
    r = longCXDCB.putSafe(2);
    assertNull(r.getError());
    assertEquals(2, longCXDCB.getVal());
    r = longCYDCB.putSafe(2);
    assertNotNull(r.getError());
    assertEquals(1, longCYDCB.getVal());
    assertTrue(caDCB.isComplete());
    assertTrue(longCXCYDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());

    //5 = ca can be set if longCXCY and shortCXCY are set and no conflicts exist
    caDCB.remove();
    assertTrue(longCXCYDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());
    assertFalse(caDCB.isComplete());
    caDCB.putSafe(14);
    assertEquals(14, caDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());
    assertTrue(caDCB.isComplete());

    //6 = ca CAN'T be set if longCXCY and shortCXCY are set and a conflict exists
    r = caDCB.putSafe(19);
    assertNotNull(r.getError());
    assertEquals(14, caDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());
    assertTrue(shortCXCYDCB.isComplete());
    assertTrue(caDCB.isComplete());

    //7 = shortCXCY can be set when ca & longCXCY get set with new coordinates, and no conflicts exist
    caDCB.remove();
    longCXCYDCB.remove();
    shortCXCYDCB.remove();
    assertFalse(longCXCYDCB.isComplete());
    assertFalse(shortCXCYDCB.isComplete());
    assertFalse(caDCB.isComplete());
    caDCB.putSafe(31);
    assertEquals(31, caDCB.getVal());
    longCXDCB.putSafe(6);
    assertEquals(6, longCXDCB.getVal());
    longCYDCB.putSafe(2);
    assertEquals(2, longCYDCB.getVal());
    shortCXDCB.putSafe(1);
    assertEquals(1, shortCXDCB.getVal());
    shortCYDCB.putSafe(7);
    assertEquals(7, shortCYDCB.getVal());

    //8 = shortCXCY CAN'T be set when ca and longCXCY are set and conflicts DO exist
    shortCXDCB.putSafe(0);
    assertEquals(1, shortCXDCB.getVal());
    shortCYDCB.putSafe(6);
    assertEquals(7, shortCYDCB.getVal());

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

    //12 = cwDCB.getOrCalc, like niDCB.getOrCalc, should simply fetch cw without unsetting ni or coordinates
    int canvasWidth = (int) cwDCB.getVal();
    r = cwDCB.getOrCalc();
    assertNull(r.getError());
    cwDCB = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(cwDCB.getVal(), canvasWidth);
    assertTrue(cwDCB.isComplete());
    assertTrue(coordinatesDCB.isComplete());

  }

  @Test void caAndShortCXCYGetOrCalc() {
    //1 = when ca = 0, shortCX should = 0 and shortCY should equal 0
    caDCB.putForce(0);
    assertEquals(0, caDCB.getVal());
    Result r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    PrimitiveDataClassBrick pdcbFromResult = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(0, pdcbFromResult.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    pdcbFromResult = (PrimitiveDataClassBrick) r.getVal();
    assertEquals(0, pdcbFromResult.getVal());

    //2 = when ca = 12, shortCX should = 0 and shortCY should equal 3
    caDCB.putForce(12);
    r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(0, shortCXDCB.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(3, shortCYDCB.getVal());

    //3 = when ca = 21, shortCX should = 3 and shortCY should equal 4
    caDCB.putForce(21);
    r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(4, shortCXDCB.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(4, shortCYDCB.getVal());

    //4 = when shortCX = 1 and shortCY = 2, ca should = 11
    shortCXDCB.putForce(1);
    assertEquals(1, shortCXDCB.getVal());
    shortCYDCB.putForce(2);
    assertEquals(2, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    r = caDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(11, caDCB.getVal());

    //5 = when shortCX = 4 and shortCY = 6, ca should = 29
    shortCXDCB.putForce(4);
    assertEquals(4, shortCXDCB.getVal());
    shortCYDCB.putForce(6);
    assertEquals(6, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertFalse(caDCB.isComplete());
    r = caDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(29, caDCB.getVal());

    //6 = when shortCX = 0 and shortCY = 7, ca should = 30
    shortCXDCB.putForce(0);
    assertEquals(0, shortCXDCB.getVal());
    shortCYDCB.putForce(7);
    assertEquals(7, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertFalse(caDCB.isComplete());
    r = caDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(30, caDCB.getVal());

    //7 = when ca = 1, shortCX should = 1 and shortCY should equal 0
    caDCB.putForce(1);
    assertFalse(shortCXCYDCB.isComplete());
    r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(1, shortCXDCB.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(0, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertTrue(caDCB.isComplete());

    //8 = when ca = 19, shortCX should = 2 and shortCY should equal 4
    caDCB.putForce(19);
    assertFalse(shortCXCYDCB.isComplete());
    r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(2, shortCXDCB.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(4, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertTrue(caDCB.isComplete());

    //9 = when shortCX = 3 and shortCY = 0, ca should = 3
    shortCXDCB.putForce(3);
    assertEquals(3, shortCXDCB.getVal());
    shortCYDCB.putForce(0);
    assertEquals(0, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertFalse(caDCB.isComplete());
    r = caDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(3, caDCB.getVal());

  }

  @Test void longCXCYAndShortCXCYGetOrCalc() {
    //1 = when longCX = 12 and longCY = 1, shortCX should = 2 and shortCY should = 5
    longCXDCB.putForce(12);
    assertEquals(12, longCXDCB.getVal());
    longCYDCB.putForce(1);
    assertEquals(1, longCYDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());
    assertFalse(shortCXCYDCB.isComplete());
    Result r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(2, shortCXDCB.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(5, shortCYDCB.getVal());

    //2 = when longCX = 5 and longCY = 2, shortCX should = 0 and shortCY should = 7
    longCXDCB.putForce(5);
    assertEquals(5, longCXDCB.getVal());
    longCYDCB.putForce(2);
    assertEquals(2, longCYDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());
    assertFalse(shortCXCYDCB.isComplete());
    r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(0, shortCXDCB.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(7, shortCYDCB.getVal());

    //3 = when longCX = 7 and longCY = 0, shortCX should = 2 and shortCY should = 1
    longCXDCB.putForce(7);
    assertEquals(7, longCXDCB.getVal());
    longCYDCB.putForce(0);
    assertEquals(0, longCYDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());
    assertFalse(shortCXCYDCB.isComplete());
    r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(2, shortCXDCB.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(1, shortCYDCB.getVal());

    //4 = when shortCX = 3 and shortCY = 0, longCX should = 3 and longCY should = 0
    shortCXDCB.putForce(3);
    assertEquals(3, shortCXDCB.getVal());
    shortCYDCB.putForce(0);
    assertEquals(0, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertFalse(longCXCYDCB.isComplete());
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(3, longCXDCB.getVal());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(0, longCYDCB.getVal());

    //5 = when shortCX = 1 and shortCY = 3, longCX should = 1 and longCY should = 1
    shortCXDCB.putForce(1);
    assertEquals(1, shortCXDCB.getVal());
    shortCYDCB.putForce(3);
    assertEquals(3, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertFalse(longCXCYDCB.isComplete());
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(1, longCXDCB.getVal());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(1, longCYDCB.getVal());

    //6 = when shortCX = 4 and shortCY = 4, longCX should = 9 and longCY should = 1
    shortCXDCB.putForce(4);
    assertEquals(4, shortCXDCB.getVal());
    shortCYDCB.putForce(4);
    assertEquals(4, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertFalse(longCXCYDCB.isComplete());
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(9, longCXDCB.getVal());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(1, longCYDCB.getVal());

    //7 = when longCX = 6 and longCY = 0, shortCX should = 1 and shortCY should = 1
    longCXDCB.putForce(6);
    assertEquals(6, longCXDCB.getVal());
    longCYDCB.putForce(0);
    assertEquals(0, longCYDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());
    assertFalse(shortCXCYDCB.isComplete());
    r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(1, shortCXDCB.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(1, shortCYDCB.getVal());

    //8 = when longCX = 10 and longCY = 0, shortCX should = 0 and shortCY should = 2
    longCXDCB.putForce(10);
    assertEquals(10, longCXDCB.getVal());
    longCYDCB.putForce(0);
    assertEquals(0, longCYDCB.getVal());
    assertTrue(longCXCYDCB.isComplete());
    assertFalse(shortCXCYDCB.isComplete());
    r = shortCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(0, shortCXDCB.getVal());
    r = shortCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(2, shortCYDCB.getVal());

    //9 = when shortCX = 2 and shortCY = 7, longCX should = 7 and longCY should = 2
    shortCXDCB.putForce(2);
    assertEquals(2, shortCXDCB.getVal());
    shortCYDCB.putForce(7);
    assertEquals(7, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertFalse(longCXCYDCB.isComplete());
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(7, longCXDCB.getVal());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(2, longCYDCB.getVal());

    //10 = when shortCX = 0 and shortCY = 0, longCX should = 0 and longCY should = 0
    shortCXDCB.putForce(0);
    assertEquals(0, shortCXDCB.getVal());
    shortCYDCB.putForce(0);
    assertEquals(0, shortCYDCB.getVal());
    assertTrue(shortCXCYDCB.isComplete());
    assertFalse(longCXCYDCB.isComplete());
    r = longCXDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(0, longCXDCB.getVal());
    r = longCYDCB.getOrCalc();
    assertNull(r.getError());
    assertEquals(0, longCYDCB.getVal());

  }

}
