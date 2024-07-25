package CursorPositionDC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLDC {

  CursorPositionDC cursorPositionDC = new CursorPositionDC();
  CAAndNLDC caAndNLDC = new CAAndNLDC(2);

  @Test
  void putAndGetLayer() {
    cursorPositionDC.putLayer(caAndNLDC);
    assertEquals(caAndNLDC, cursorPositionDC.getLayer(0));
  }

  @BeforeEach
  void layerSetup() {

  }

  @Test void makeBrick() {


  }

}
