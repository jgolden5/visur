package CursorPositionDC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestCursorPositionDC {

  CursorPositionDCHolder cursorPositionDCHolder = new CursorPositionDCHolder();
  CursorPositionDC cursorPositionDC = cursorPositionDCHolder.cursorPositionDC;
  CoordinatesDC coordinatesDC = cursorPositionDCHolder.coordinatesDC;
  CAAndNLDC caAndNLDC = cursorPositionDCHolder.caAndNLDC;
  RCXCYAndNLDC rcxcyAndNLDC = cursorPositionDCHolder.rcxcyAndNLDC;
  VirtualDC virtualDC = cursorPositionDCHolder.virtualDC;
  VCXAndLLDC vcxAndLLDC = cursorPositionDCHolder.vcxAndLLDC;
  RCXAndLODC rcxAndLODC = cursorPositionDCHolder.rcxAndLODC;
  WholeNumberDC wholeNumberDC = cursorPositionDCHolder.wholeNumberDC;
  WholeNumberListDC nlDC = cursorPositionDCHolder.wholeNumberListDC;
  JavaIntDF javaIntDF = cursorPositionDCHolder.javaIntDF;

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
  }

  @Test
  void ldcRemoveLayers() {
//    cursorPositionDC.removeLayers();
  }

  @Test
  void putAndGetLayer() {
    cursorPositionDC.putLayer(caAndNLDC);
    assertEquals(caAndNLDC, cursorPositionDC.getLayer(0));

    cursorPositionDC.putLayer(rcxcyAndNLDC);
    assertEquals(rcxcyAndNLDC, cursorPositionDC.getLayer(1));

    assertEquals(caAndNLDC, cursorPositionDC.getLayer(0));
    assertEquals(rcxcyAndNLDC, cursorPositionDC.getLayer(1));
    assertNotEquals(caAndNLDC, cursorPositionDC.getLayer(1));
    assertNotEquals(rcxcyAndNLDC, cursorPositionDC.getLayer(0));
  }

  @Test
  void putAndGetInner() {
    caAndNLDC.putInner("nl", nlDC);
    assertEquals(nlDC, caAndNLDC.getInner("nl"));

    CADC caDC = new CADC(javaIntDF);
    caAndNLDC.putInner("ca", caDC);
    assertEquals(caDC, caAndNLDC.getInner("ca"));

    assertNotEquals(caDC, caAndNLDC.getInner("nl"));
    assertNotEquals(nlDC, caAndNLDC.getInner("ca"));
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

}
