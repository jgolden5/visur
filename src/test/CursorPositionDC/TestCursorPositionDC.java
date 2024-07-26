package CursorPositionDC;

import DataClass.CompoundDataClassBrick;
import DataClass.LayeredDataClassBrick;
import DataClass.OuterDataClassBrick;
import DataClass.PrimitiveDataClassBrick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    coordinatesDCB = coordinatesDC.makeBrick("coordinates", coordinatesOuters);
    caAndNLDCB = caAndNLDC.makeBrick("caAndNL", caAndNLOuters);
    rcxcyAndNLDCB = rcxcyAndNLDC.makeBrick("rcxcyAndNL", rcxcyAndNLOuters);
    virtualDCB = virtualDC.makeBrick("virtual", virtualOuters);
    vcxAndLLDCB = vcxAndLLDC.makeBrick("vcxAndLL", vcxAndLLOuters);
    rcxAndLODCB = rcxAndLODC.makeBrick("rcxAndLO", rcxAndLOOuters);
    caDCB = wholeNumberDC.makeBrick("ca", caOuters);
    rcxDCB = wholeNumberDC.makeBrick("rcx", rcxOuters);
    cyDCB = wholeNumberDC.makeBrick("cy", cyOuters);
    vcxDCB = wholeNumberDC.makeBrick("vcx", vcxOuters);
    llDCB = wholeNumberDC.makeBrick("ll", llOuters);
    loDCB = wholeNumberDC.makeBrick("lo", loOuters);
    nlDCB = nlDC.makeBrick("nl", nlOuters);
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
