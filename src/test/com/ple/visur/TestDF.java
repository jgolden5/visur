package com.ple.visur;

import CursorPositionDC.*;
import DataClass.DataFormBrick;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class TestDF {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  @Test
  public void directConversion() {
    DataFormBrick aDFB = cursorPositionDCHolder.aDF.makeBrick(5).get();
    DataFormBrick cxcyDFB = aDFB.convertTo(cursorPositionDCHolder.cxcyDF, cursorPositionDCHolder);
    assertEquals(cursorPositionDCHolder.cxcyDF.makeBrick(CursorPosition.make(0, 5)).get(), cxcyDFB);
  }

  @Test
  public void indirectConversion() {

  }

  @Test
  public void selfConversion() {

  }

  @Test
  public void noNullDFBs() {

  }

}
