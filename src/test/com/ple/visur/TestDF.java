package com.ple.visur;

import CursorPositionDC.*;
import DataClass.DataFormBrick;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class TestDF {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  @Test void dfMakeBrick() {
    ArrayList<Integer> intArrayExample = new ArrayList<>();
    intArrayExample.add(0);
    intArrayExample.add(50);
    intArrayExample.add(100);
    Optional<DataFormBrick> intArrayListDFBAsOptional = cursorPositionDCHolder.intArrayListDF.makeBrick(intArrayExample);
    DataFormBrick intArrayListDFB;
    intArrayListDFB = intArrayListDFBAsOptional.orElse(null);
    assertEquals(cursorPositionDCHolder.intArrayListDF, intArrayListDFB.getDF());
    assertEquals(intArrayExample, intArrayListDFB.getVal());

    intArrayListDFBAsOptional = cursorPositionDCHolder.intArrayListDF.makeBrick("metal > country");
    intArrayListDFB = intArrayListDFBAsOptional.orElse(null);
    assertNull(intArrayListDFB);
  }

  @Test
  public void directConversion() {

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
