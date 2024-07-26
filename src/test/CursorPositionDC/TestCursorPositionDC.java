package CursorPositionDC;

import DataClass.CompoundDataClassBrick;
import DataClass.LayeredDataClassBrick;
import DataClass.PrimitiveDataClassBrick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestCursorPositionDC {

  CursorPositionDCHolder cursorPositionDCHolder = new CursorPositionDCHolder();
  CursorPositionDC cursorPositionDC;
  LayeredDataClassBrick cursorPositionDCB;
  CoordinatesDC coordinatesDC;
  CompoundDataClassBrick coordinatesDCB;
  CAAndNLDC caAndNLDC;
  CompoundDataClassBrick caAndNLDCB;
  RCXCYAndNLDC rcxcyAndNLDC;
  CompoundDataClassBrick rcxcyAndNLDCB;
  VirtualDC virtualDC;
  CompoundDataClassBrick virtualDCB;
  VCXAndLLDC vcxAndLLDC;
  CompoundDataClassBrick vcxAndLLDCB;
  RCXAndLODC rcxAndLODC;
  CompoundDataClassBrick rcxAndLODCB;
  WholeNumberDC wholeNumberDC;
  PrimitiveDataClassBrick caDCB;
  PrimitiveDataClassBrick rcxDCB;
  PrimitiveDataClassBrick cyDCB;
  PrimitiveDataClassBrick vcxDCB;
  PrimitiveDataClassBrick llDCB;
  PrimitiveDataClassBrick loDCB;
  WholeNumberListDC nlDC;
  PrimitiveDataClassBrick nlDCB;
  JavaIntDF javaIntDF;

  @BeforeEach
  void resetVars() {
    cursorPositionDCHolder = new CursorPositionDCHolder();
    cursorPositionDC = cursorPositionDCHolder.cursorPositionDC;
    coordinatesDC = cursorPositionDCHolder.coordinatesDC;
    caAndNLDC = cursorPositionDCHolder.caAndNLDC;
    rcxcyAndNLDC = cursorPositionDCHolder.rcxcyAndNLDC;
    virtualDC = cursorPositionDCHolder.virtualDC;
    vcxAndLLDC = cursorPositionDCHolder.vcxAndLLDC;
    rcxAndLODC = cursorPositionDCHolder.rcxAndLODC;
    wholeNumberDC = cursorPositionDCHolder.wholeNumberDC;
    nlDC = cursorPositionDCHolder.wholeNumberListDC;
    javaIntDF = cursorPositionDCHolder.javaIntDF;

    cursorPositionDCB = cursorPositionDC.makeBrick();
    coordinatesDCB = coordinatesDC.makeBrick();
    caAndNLDCB = caAndNLDC.makeBrick();
    rcxcyAndNLDCB = rcxcyAndNLDC.makeBrick();
    virtualDCB = virtualDC.makeBrick();
    vcxAndLLDCB = vcxAndLLDC.makeBrick();
    rcxAndLODCB = rcxAndLODC.makeBrick();
    caDCB = wholeNumberDC.makeBrick();
    rcxDCB = wholeNumberDC.makeBrick();
    cyDCB = wholeNumberDC.makeBrick();
    vcxDCB = wholeNumberDC.makeBrick();
    llDCB = wholeNumberDC.makeBrick();
    loDCB = wholeNumberDC.makeBrick();
    nlDCB = nlDC.makeBrick();
  }

  @Test
  void setCursorPositionDCHolder() {
    assertEquals(coordinatesDC, cursorPositionDC.getLayer(0));
    assertEquals(virtualDC, cursorPositionDC.getLayer(1));

    assertEquals(caAndNLDC, coordinatesDC.getInner("caAndNL"));
    assertEquals(rcxcyAndNLDC, coordinatesDC.getInner("rcxcyAndNL"));

    assertEquals(vcxAndLLDC, virtualDC.getInner("vcxAndLL"));
    assertEquals(rcxAndLODC, virtualDC.getInner("rcxAndLO"));

    assertEquals(nlDC, caAndNLDC.getInner("nl"));
    assertEquals(wholeNumberDC, caAndNLDC.getInner("wholeNumber"));

    assertEquals(nlDC, rcxcyAndNLDC.getInner("nl"));
    assertEquals(wholeNumberDC, rcxcyAndNLDC.getInner("wholeNumber"));

    assertEquals(wholeNumberDC, vcxAndLLDC.getInner("wholeNumber"));

    assertEquals(wholeNumberDC, rcxAndLODC.getInner("wholeNumber"));

  }

  @Test
  void makeBrick() {

  }

}
