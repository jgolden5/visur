package CursorPositionDC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLDC {

  CursorPositionDC cursorPositionDC = new CursorPositionDC();
  CAAndNLDC caAndNLDC = new CAAndNLDC(2);
  RCXCYAndNLDC rcxcyAndNLDC = new RCXCYAndNLDC(3);

  @Test
  void putAndGetLayer() {
    cursorPositionDC.putLayer(caAndNLDC);
    assertEquals(caAndNLDC, cursorPositionDC.getLayer(0));

    cursorPositionDC.putLayer(rcxcyAndNLDC);
    assertEquals(rcxcyAndNLDC, cursorPositionDC.getLayer(1));
  }

  @BeforeEach
  void layerSetup() {

  }

  @Test void makeBrick() {


  }

}
