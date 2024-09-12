package CursorPositionDC;

import DataClass.CompoundDataClassBrick;
import DataClass.LayeredDataClassBrick;
import DataClass.OuterDataClassBrick;
import DataClass.PrimitiveDataClassBrick;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCursorPositionDC {

  static CursorPositionDCHolder cursorPositionDCHolder = new CursorPositionDCHolder();
  static CursorPositionDC cursorPositionDC;
  static LayeredDataClassBrick cursorPositionDCB;
  static CoordinatesDC coordinatesDC;
  static CompoundDataClassBrick coordinatesDCB;
  static CAAndNLDC caAndNLDC;
  static CompoundDataClassBrick caAndNLDCB;
  static CXCYAndNLDC CXCYAndNLDC;
  static CompoundDataClassBrick cxcyAndNLDCB;
  static WholeNumberDC wholeNumberDC;
  static PrimitiveDataClassBrick caDCB;
  static PrimitiveDataClassBrick cxDCB;
  static PrimitiveDataClassBrick cyDCB;
  static WholeNumberListDC nlDC;
  static PrimitiveDataClassBrick nlDCB;
  static JavaIntDF javaIntDF;

  @BeforeAll
  static void initVars() {
    cursorPositionDC = cursorPositionDCHolder.cursorPositionDC;
    coordinatesDC = cursorPositionDCHolder.coordinatesDC;
    caAndNLDC = cursorPositionDCHolder.caAndNLDC;
    CXCYAndNLDC = cursorPositionDCHolder.CXCYAndNLDC;
    wholeNumberDC = cursorPositionDCHolder.wholeNumberDC;
    nlDC = cursorPositionDCHolder.wholeNumberListDC;
    javaIntDF = cursorPositionDCHolder.javaIntDF;

    ArrayList<OuterDataClassBrick> nlOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> cyOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> cxOuters = new ArrayList<>();

    nlDCB = wholeNumberDC.makeBrick("nl", nlOuters, true);
    cxDCB = wholeNumberDC.makeBrick("cx", cxOuters, false);
    cyDCB = wholeNumberDC.makeBrick("cy", cyOuters, false);
    cursorPositionDCB = cursorPositionDC.makeBrick(nlDCB);
    coordinatesDCB = cursorPositionDCB.getLayer(0);
    caAndNLDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("caAndNL");
    cxcyAndNLDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("cxcyAndNL");
    caDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("ca");

    coordinatesDCB.putInner("caAndNL", caAndNLDCB);
    coordinatesDCB.putInner("cxcyAndNL", cxcyAndNLDCB);

    caAndNLDCB.putInner("ca", caDCB);
    caAndNLDCB.putInner("nl", nlDCB);

    cxcyAndNLDCB.putInner("cx", cxDCB);
    cxcyAndNLDCB.putInner("cy", cyDCB);
    cxcyAndNLDCB.putInner("nl", nlDCB);

    assertTrue(nlDCB.isReadOnly());
    assertFalse(cxDCB.isReadOnly());
    assertFalse(caDCB.isReadOnly());
    assertFalse(cyDCB.isReadOnly());

  }

  @BeforeEach
  void remove() {
    boolean nlDCBWasCompleteBeforeRemoval = nlDCB.isComplete();
    cursorPositionDCB.remove();
    assertFalse(cursorPositionDCB.isComplete());
    assertFalse(coordinatesDCB.isComplete());
    assertFalse(cxDCB.isComplete());
    assertFalse(cyDCB.isComplete());
    assertEquals(nlDCBWasCompleteBeforeRemoval, nlDCB.isComplete());
  }

  @Test
  void setCursorPositionDCHolder() {
    assertEquals(coordinatesDC, cursorPositionDC.getLayer(0));

    //coordinates
    assertEquals(caAndNLDC, coordinatesDC.getInner("caAndNL"));
    assertEquals(CXCYAndNLDC, coordinatesDC.getInner("cxcyAndNL"));

    assertEquals(nlDC, caAndNLDC.getInner("nl"));
    assertEquals(wholeNumberDC, caAndNLDC.getInner("wholeNumber"));

    assertEquals(nlDC, CXCYAndNLDC.getInner("nl"));
    assertEquals(wholeNumberDC, CXCYAndNLDC.getInner("wholeNumber"));

  }

  @Test
  void setInners() {
    assertEquals("coordinates", cursorPositionDCB.getLayer(0).name);

    assertNotNull(coordinatesDCB.getInner("caAndNL"));
    assertNotNull(coordinatesDCB.getInner("cxcyAndNL"));

    assertNotNull(caAndNLDCB.getInner("ca"));
    assertNotNull(caAndNLDCB.getInner("nl"));

    assertNotNull(cxcyAndNLDCB.getInner("cx"));
    assertNotNull(cxcyAndNLDCB.getInner("cy"));
    assertNotNull(cxcyAndNLDCB.getInner("nl"));

  }

  @Test
  void setOuters() {
    ArrayList<OuterDataClassBrick> coordinatesOutersAsBricks = coordinatesDCB.getOuters();
    ArrayList<String> coordinateOuters = getOuterNamesFromBricks(coordinatesOutersAsBricks);
    assertTrue(coordinateOuters.size() == 1);
    assertTrue(coordinateOuters.contains("cursorPosition"));

    ArrayList<OuterDataClassBrick> caAndNLOutersAsBricks = caAndNLDCB.getOuters();
    ArrayList<String> caAndNLOuters = getOuterNamesFromBricks(caAndNLOutersAsBricks);
    assertTrue(caAndNLOuters.size() == 1);
    assertTrue(caAndNLOuters.contains("coordinates"));

    ArrayList<OuterDataClassBrick> cxcyAndNLOutersAsBricks = cxcyAndNLDCB.getOuters();
    ArrayList<String> cxcyAndNLOuters = getOuterNamesFromBricks(cxcyAndNLOutersAsBricks);
    assertTrue(cxcyAndNLOuters.size() == 1);
    assertTrue(cxcyAndNLOuters.contains("coordinates"));

    ArrayList<OuterDataClassBrick> nlOutersAsBricks = nlDCB.getOuters();
    ArrayList<String> nlOuters = getOuterNamesFromBricks(nlOutersAsBricks);
    assertTrue(nlOuters.size() == 4);
    assertTrue(nlOuters.contains("caAndNL"));
    assertTrue(nlOuters.contains("cxcyAndNL"));

    ArrayList<OuterDataClassBrick> caOutersAsBricks = caDCB.getOuters();
    ArrayList<String> caOuters = getOuterNamesFromBricks(caOutersAsBricks);
    assertTrue(caOuters.size() == 1);
    assertTrue(caOuters.contains("caAndNL"));

    ArrayList<OuterDataClassBrick> cxOutersAsBricks = cxDCB.getOuters();
    ArrayList<String> cxOuters = getOuterNamesFromBricks(cxOutersAsBricks);
    assertTrue(cxOuters.size() == 2);
    assertTrue(cxOuters.contains("cxcyAndNL"));

    ArrayList<OuterDataClassBrick> cyOutersAsBricks = cyDCB.getOuters();
    ArrayList<String> cyOuters = getOuterNamesFromBricks(cyOutersAsBricks);
    assertTrue(cyOuters.size() == 3);
    assertTrue(cyOuters.contains("cxcyAndNL"));

  }

  private ArrayList<String> getOuterNamesFromBricks(ArrayList<OuterDataClassBrick> outersAsBricks) {
    ArrayList<String> outerNames = new ArrayList<>();
    for(OuterDataClassBrick outerBrick : outersAsBricks) {
      outerNames.add(outerBrick.getName());
    }
    return outerNames;
  }

  @Test
  void isComplete() {
    assertFalse(cursorPositionDCB.isComplete());
    assertFalse(coordinatesDCB.isComplete());
    assertFalse(nlDCB.isComplete());
    assertFalse(caDCB.isComplete());
    assertFalse(cxDCB.isComplete());
    assertFalse(cyDCB.isComplete());

    //coordinates
    //caAndNL
    caDCB.put(0);
    assertTrue(caDCB.isComplete());
    assertFalse(caAndNLDCB.isComplete());

    ArrayList<Integer> nextLineIndices = new ArrayList<>();
    nextLineIndices.add(12);
    nextLineIndices.add(25);
    nextLineIndices.add(32);
    nlDCB.put(nextLineIndices);
    assertTrue(nlDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());

    assertTrue(coordinatesDCB.isComplete());

    //cxcyAndNL
    assertTrue(nlDCB.isComplete());
    assertFalse(cxcyAndNLDCB.isComplete());
    cxDCB.put(0);
    assertTrue(cxDCB.isComplete());
    assertFalse(cxcyAndNLDCB.isComplete());

    cyDCB.put(0);
    assertTrue(cyDCB.isComplete());
    assertTrue(cxcyAndNLDCB.isComplete());

    assertTrue(coordinatesDCB.isComplete());

  }

  @Test
  void putWhenNotComplete() {
    //coordinates
    //set caAndNL when cxcyAndNL is unset
    assertFalse(caAndNLDCB.isComplete());
    caDCB.put(0);
    assertEquals(0, caDCB.getVal());

    ArrayList<Integer> nl = new ArrayList<>();
    nl.add(5);
    nl.add(10);
    nlDCB.put(nl);

    caAndNLDCB.putInner("ca", caDCB);
    caAndNLDCB.putInner("nl", nlDCB);
    assertEquals(nl, nlDCB.getVal());
    assertTrue(caAndNLDCB.isComplete());
    assertTrue(coordinatesDCB.isComplete());

    caAndNLDCB.remove();

    //set cxcyAndNL when caAndNL is unset
    assertFalse(caAndNLDCB.isComplete());
    assertFalse(cxcyAndNLDCB.isComplete());

    cxDCB.put(0);
    assertEquals(0, cxDCB.getVal());
    assertFalse(cxcyAndNLDCB.isComplete());
    cyDCB.put(1);
    assertEquals(1, cyDCB.getVal());
    assertTrue(cxcyAndNLDCB.isComplete());

  }

  @Test
  void putWhenComplete() {
    //coordinates
    //setting caAndNL unsets cxcyAndNL if cxcyAndNL is set
    cxDCB.put(0);
    cyDCB.put(1);
    ArrayList<Integer> nl = new ArrayList<>();
    nl.add(13);
    nlDCB.put(nl);
    assertTrue(cxcyAndNLDCB.isComplete());
    assertFalse(caAndNLDCB.isComplete());
    caDCB.put(0);
    assertFalse(cxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());

    //setting cxcyAndNL unsets caAndNL if caAndNL is set
    cxDCB.put(1);
    assertTrue(cxDCB.isComplete());
    assertTrue(nlDCB.isComplete());
    cyDCB.put(2);
    assertEquals(2, cyDCB.getVal());
    assertTrue(cxcyAndNLDCB.isComplete());
    assertFalse(caAndNLDCB.isComplete());

  }

  @Test void getOrCalc() {
    //coordinatesDC
    //1 if nl = [12, 25, 32], cx = 5, and cy = 1, ca can be calculated = 17
    // (getOrCalc also works on set values such as nl, cx and cy)
    ArrayList<Integer> nl = new ArrayList<>();
    nl.add(12);
    nl.add(25);
    nl.add(32);
    nlDCB.put(nl);
    cxDCB.put(5);
    cyDCB.put(1);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(5, cxDCB.getVal());
    assertEquals(1, cyDCB.getVal());
    cxDCB.getOrCalc();
    assertEquals(5, cxDCB.getVal());
    cyDCB.getOrCalc();
    assertEquals(1, cyDCB.getVal());

    caDCB.getOrCalc();
    assertEquals(17, caDCB.getVal());
    assertEquals(nl, nlDCB.getVal());
    assertTrue(cxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());

    //2 if nl = [12, 25, 32], cx = 3, and cy = 2, ca can be calculated = 28
    cxDCB.put(3);
    cyDCB.put(2);
    assertTrue(cxcyAndNLDCB.isComplete());
    assertEquals(3, cxDCB.getVal());
    assertEquals(2, cyDCB.getVal());
    assertEquals(nl, nlDCB.getVal());
    caDCB.getOrCalc();
    assertEquals(28, caDCB.getVal());
    assertTrue(cxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());

    //3 if nl = [12, 25, 32] and ca = 14, cx should = 2 and cy should = 1
    // (getOrCalc also works on set values such as nl, and ca)
    caDCB.put(14);
    assertEquals(14, caDCB.getVal());
    assertTrue(caAndNLDCB.isComplete());
    assertFalse(cxcyAndNLDCB.isComplete());
    nlDCB.getOrCalc();
    assertEquals(nl, nlDCB.getVal());
    caDCB.getOrCalc();
    assertEquals(14, caDCB.getVal());

    cyDCB.getOrCalc();
    assertEquals(1, cyDCB.getVal());
    cxDCB.getOrCalc();
    assertEquals(2, cxDCB.getVal());
    assertTrue(cxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());
    assertTrue(coordinatesDCB.isComplete());

  }

}
