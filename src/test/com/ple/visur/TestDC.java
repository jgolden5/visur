package com.ple.visur;

import CursorPositionDC.*;
import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.DataFormBrick;
import DataClass.PrimitiveDataClassBrick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  @Test void dcConstrainsDFs() {
    CompoundDataClassBrick CursorPosDCB = cursorPositionDCHolder.cursorPosDC.makeBrick(cursorPositionDCHolder);
  }

  @Test void cdcbCalculateInner() {

  }
  @Test void calculateInnerFailsWithNotEnoughInfo() {

  }

}
