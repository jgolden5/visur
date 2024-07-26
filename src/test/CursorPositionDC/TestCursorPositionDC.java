package CursorPositionDC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestCursorPositionDC {

  CursorPositionDC cursorPositionDC = new CursorPositionDC();
  CAAndNLDC caAndNLDC = new CAAndNLDC(2);
  RCXCYAndNLDC rcxcyAndNLDC = new RCXCYAndNLDC(3);

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

  @BeforeEach
  void layerSetup() {

  }

  @Test
  void makeBrick() {
  }

}
