package CursorPositionDC;

import DataClass.PrimitiveDataClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestCursorPositionDC {

  CursorPositionDC cursorPositionDC = new CursorPositionDC();
  CAAndNLDC caAndNLDC = new CAAndNLDC(2);
  RCXCYAndNLDC rcxcyAndNLDC = new RCXCYAndNLDC(3);
  JavaIntDF javaIntDF = new JavaIntDF(0);
  IntArrayListDF intArrayListDF = new IntArrayListDF(0);
  WholeNumberDC wholeNumberDC = new WholeNumberDC(javaIntDF);
  WholeNumberListDC wholeNumberListDC = new WholeNumberListDC(intArrayListDF);

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
    NLDC nlDC = new NLDC(javaIntDF);
    caAndNLDC.putInner("nl", nlDC);
    assertEquals(nlDC, caAndNLDC.getInner("nl"));
  }

}
