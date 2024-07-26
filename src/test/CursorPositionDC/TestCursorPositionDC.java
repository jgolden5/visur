package CursorPositionDC;

import DataClass.CompoundDataClassBrick;
import DataClass.LayeredDataClassBrick;
import DataClass.OuterDataClassBrick;
import DataClass.PrimitiveDataClassBrick;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
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

    ArrayList<OuterDataClassBrick> nlOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> loOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> llOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> vcxOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> cyOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> rcxOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> caOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> rcxAndLOOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> vcxAndLLOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> virtualOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> rcxcyAndNLOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> caAndNLOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> coordinatesOuters = new ArrayList<>();

    cursorPositionDCB = cursorPositionDC.makeBrick();
    coordinatesDCB = cursorPositionDCB.getLayer(0);
    caAndNLDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("caAndNL");
    rcxcyAndNLDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("rcxcyAndNL");
    virtualDCB = cursorPositionDCB.getLayer(1);
    vcxAndLLDCB = (CompoundDataClassBrick) virtualDCB.getInner("vcxAndLL");
    rcxAndLODCB = (CompoundDataClassBrick) virtualDCB.getInner("rcxAndLO");
    caDCB = wholeNumberDC.makeBrick("ca", caOuters);
    rcxDCB = wholeNumberDC.makeBrick("rcx", rcxOuters);
    cyDCB = wholeNumberDC.makeBrick("cy", cyOuters);
    vcxDCB = wholeNumberDC.makeBrick("vcx", vcxOuters);
    llDCB = wholeNumberDC.makeBrick("ll", llOuters);
    loDCB = wholeNumberDC.makeBrick("lo", loOuters);
    nlDCB = nlDC.makeBrick("nl", nlOuters);

    cursorPositionDCB.putLayer(coordinatesDCB);

    coordinatesDCB.putInner("caAndNL", caAndNLDCB);
    coordinatesDCB.putInner("rcxcyAndNL", rcxcyAndNLDCB);

    caAndNLDCB.putInner("ca", caDCB);
    caAndNLDCB.putInner("nl", nlDCB);

    rcxcyAndNLDCB.putInner("rcx", rcxDCB);
    rcxcyAndNLDCB.putInner("cy", cyDCB);
    rcxcyAndNLDCB.putInner("nl", nlDCB);

    coordinatesOuters.add(cursorPositionDCB);
    virtualOuters.add(cursorPositionDCB);
    caAndNLOuters.add(coordinatesDCB);
    rcxcyAndNLOuters.add(coordinatesDCB);
    nlOuters.add(caAndNLDCB);
    nlOuters.add(rcxcyAndNLDCB);
    caOuters.add(caAndNLDCB);
    rcxOuters.add(rcxcyAndNLDCB);
    rcxOuters.add(rcxAndLODCB);
    cyOuters.add(rcxcyAndNLDCB);

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
  void setInners() {
    assertEquals("coordinates", cursorPositionDCB.getLayer(0).name);
    assertEquals("virtual", cursorPositionDCB.getLayer(1).name);

    assertNotNull(coordinatesDCB.getInner("caAndNL"));
    assertNotNull(coordinatesDCB.getInner("rcxcyAndNL"));

    assertNotNull(virtualDCB.getInner("vcxAndLL"));
    assertNotNull(virtualDCB.getInner("rcxAndLO"));

    assertNotNull(caAndNLDCB.getInner("ca"));
    assertNotNull(caAndNLDCB.getInner("nl"));

    assertNotNull(rcxcyAndNLDCB.getInner("rcx"));
    assertNotNull(rcxcyAndNLDCB.getInner("cy"));
    assertNotNull(rcxcyAndNLDCB.getInner("nl"));

    assertNotNull(vcxAndLLDCB.getInner("vcx"));
    assertNotNull(vcxAndLLDCB.getInner("ll"));

    assertNotNull(rcxAndLODCB.getInner("rcx"));
    assertNotNull(rcxAndLODCB.getInner("lo"));
  }

  @Test
  void setOuters() {
    ArrayList<OuterDataClassBrick> coordinatesOuters = coordinatesDCB.getOuters();
    assertTrue(coordinatesOuters.size() == 1);
    assertTrue(coordinatesOuters.contains("cursorPosition"));

    ArrayList<OuterDataClassBrick> caAndNLOuters = caAndNLDCB.getOuters();
    assertTrue(caAndNLOuters.size() == 1);
    assertTrue(caAndNLOuters.contains("coordinates"));

    ArrayList<OuterDataClassBrick> rcxcyAndNLOuters = rcxcyAndNLDCB.getOuters();
    assertTrue(rcxcyAndNLOuters.size() == 1);
    assertTrue(rcxcyAndNLOuters.contains("coordinates"));

    ArrayList<OuterDataClassBrick> nlOuters = nlDCB.getOuters();
    assertTrue(rcxcyAndNLOuters.size() == 2);
    assertTrue(nlOuters.contains("caAndNL"));
    assertTrue(nlOuters.contains("rcxcyAndNL"));

    ArrayList<OuterDataClassBrick> caOuters = caDCB.getOuters();
    assertTrue(rcxcyAndNLOuters.size() == 1);
    assertTrue(caOuters.contains("caAndNL"));

    ArrayList<OuterDataClassBrick> rcxOuters = rcxDCB.getOuters();
    assertTrue(rcxOuters.size() == 1);
    assertTrue(rcxOuters.contains("rcxcyAndNL"));
    assertTrue(rcxOuters.contains("rcxAndLO"));

    ArrayList<OuterDataClassBrick> cyOuters = cyDCB.getOuters();
    assertTrue(cyOuters.size() == 1);
    assertTrue(cyOuters.contains("rcxcyAndNL"));

  }

//  @Test
//  void putButNoUnset() {
//    caDCB.put(0);
//    caAndNLDCB.putInner("ca", caDCB);
//  }
//
//  @Test
//  void putAndUnset() {
//
//  }
//
}
