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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestCursorPositionDC {

  static CursorPositionDCHolder cursorPositionDCHolder = new CursorPositionDCHolder();
  static CursorPositionDC cursorPositionDC;
  static LayeredDataClassBrick cursorPositionDCB;
  static CoordinatesDC coordinatesDC;
  static CompoundDataClassBrick coordinatesDCB;
  static CAAndNLDC caAndNLDC;
  static CompoundDataClassBrick caAndNLDCB;
  static RCXCYAndNLDC rcxcyAndNLDC;
  static CompoundDataClassBrick rcxcyAndNLDCB;
  static CompoundDataClassBrick llFromCYDCB;
  static CompoundDataClassBrick cyAndNLDCB;
  static CompoundDataClassBrick llcyAndNLDCB;
  static LLFromCYDC llFromCYDC;
  static CYAndNLDC cyAndNLDC;
  static LLCYAndNLDC llcyAndNLDC;
  static VirtualDC virtualDC;
  static CompoundDataClassBrick virtualDCB;
  static VCXRCXAndLODC vcxRcxAndLODC;
  static CompoundDataClassBrick vcxRcxAndLODCB;
  static WholeNumberDC wholeNumberDC;
  static PrimitiveDataClassBrick caDCB;
  static PrimitiveDataClassBrick rcxDCB;
  static PrimitiveDataClassBrick cyDCB;
  static PrimitiveDataClassBrick vcxDCB;
  static PrimitiveDataClassBrick llDCB;
  static PrimitiveDataClassBrick loDCB;
  static WholeNumberListDC nlDC;
  static PrimitiveDataClassBrick nlDCB;
  static JavaIntDF javaIntDF;

  @BeforeAll
  static void initVars() {
    cursorPositionDC = cursorPositionDCHolder.cursorPositionDC;
    coordinatesDC = cursorPositionDCHolder.coordinatesDC;
    caAndNLDC = cursorPositionDCHolder.caAndNLDC;
    rcxcyAndNLDC = cursorPositionDCHolder.rcxcyAndNLDC;
    llFromCYDC = cursorPositionDCHolder.llFromCYDC;
    cyAndNLDC = cursorPositionDCHolder.cyAndNLDC;
    llcyAndNLDC = cursorPositionDCHolder.llcyAndNLDC;
    virtualDC = cursorPositionDCHolder.virtualDC;
    vcxRcxAndLODC = cursorPositionDCHolder.vcxRcxAndLODC;
    wholeNumberDC = cursorPositionDCHolder.wholeNumberDC;
    nlDC = cursorPositionDCHolder.wholeNumberListDC;
    javaIntDF = cursorPositionDCHolder.javaIntDF;

    ArrayList<OuterDataClassBrick> nlOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> llOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> cyOuters = new ArrayList<>();
    ArrayList<OuterDataClassBrick> rcxOuters = new ArrayList<>();

    nlDCB = wholeNumberDC.makeBrick("nl", nlOuters, true);
    rcxDCB = wholeNumberDC.makeBrick("rcx", rcxOuters, false);
    cyDCB = wholeNumberDC.makeBrick("cy", cyOuters, false);
    llDCB = wholeNumberDC.makeBrick("ll", llOuters, false);
    cursorPositionDCB = cursorPositionDC.makeBrick(nlDCB, rcxDCB, cyDCB, llDCB);
    coordinatesDCB = cursorPositionDCB.getLayer(0);
    caAndNLDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("caAndNL");
    rcxcyAndNLDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("rcxcyAndNL");
    llFromCYDCB = cursorPositionDCB.getLayer(1);
    cyAndNLDCB = (CompoundDataClassBrick)llFromCYDCB.getInner("cyAndNL");
    llcyAndNLDCB = (CompoundDataClassBrick)llFromCYDCB.getInner("llcyAndNL");
    virtualDCB = cursorPositionDCB.getLayer(2);
    vcxRcxAndLODCB = (CompoundDataClassBrick) virtualDCB.getInner("vcxRCXAndLO");
    caDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("ca");
    vcxDCB = (PrimitiveDataClassBrick) vcxRcxAndLODCB.getInner("vcx");
    loDCB = (PrimitiveDataClassBrick) vcxRcxAndLODCB.getInner("lo");

    coordinatesDCB.putInner("caAndNL", caAndNLDCB);
    coordinatesDCB.putInner("rcxcyAndNL", rcxcyAndNLDCB);

    caAndNLDCB.putInner("ca", caDCB);
    caAndNLDCB.putInner("nl", nlDCB);

    rcxcyAndNLDCB.putInner("rcx", rcxDCB);
    rcxcyAndNLDCB.putInner("cy", cyDCB);
    rcxcyAndNLDCB.putInner("nl", nlDCB);

    llFromCYDCB.putInner("cyAndNL", cyAndNLDCB);
    cyAndNLDCB.putInner("cy", cyDCB);
    cyAndNLDCB.putInner("nl", nlDCB);

    llFromCYDCB.putInner("llcyAndNL", llcyAndNLDCB);
    llcyAndNLDCB.putInner("ll", llDCB);
    llcyAndNLDCB.putInner("cy", cyDCB);
    llcyAndNLDCB.putInner("nl", nlDCB);

    virtualDCB.putInner("ll", llDCB);
    virtualDCB.putInner("vcxRCXAndLO", vcxRcxAndLODCB);

    vcxRcxAndLODCB.putInner("vcx", vcxDCB);
    vcxRcxAndLODCB.putInner("rcx", rcxDCB);
    vcxRcxAndLODCB.putInner("lo", loDCB);

    assertTrue(nlDCB.isReadOnly());
    assertFalse(rcxDCB.isReadOnly());
    assertFalse(caDCB.isReadOnly());
    assertFalse(cyDCB.isReadOnly());
    assertFalse(vcxDCB.isReadOnly());
    assertFalse(llDCB.isReadOnly());
    assertFalse(loDCB.isReadOnly());

  }

  @BeforeEach
  void remove() {
    boolean nlDCBWasCompleteBeforeRemoval = nlDCB.isComplete();
    cursorPositionDCB.remove();
    assertFalse(cursorPositionDCB.isComplete());
    assertFalse(coordinatesDCB.isComplete());
    assertFalse(llFromCYDCB.isComplete());
    assertFalse(virtualDCB.isComplete());
    assertFalse(rcxDCB.isComplete());
    assertFalse(cyDCB.isComplete());
    assertFalse(vcxDCB.isComplete());
    assertFalse(loDCB.isComplete());
    assertFalse(llDCB.isComplete());
    assertEquals(nlDCBWasCompleteBeforeRemoval, nlDCB.isComplete());
  }

  @Test
  void setCursorPositionDCHolder() {
    assertEquals(coordinatesDC, cursorPositionDC.getLayer(0));
    assertEquals(llFromCYDC, cursorPositionDC.getLayer(1));
    assertEquals(virtualDC, cursorPositionDC.getLayer(2));

    //coordinates
    assertEquals(caAndNLDC, coordinatesDC.getInner("caAndNL"));
    assertEquals(rcxcyAndNLDC, coordinatesDC.getInner("rcxcyAndNL"));

    assertEquals(nlDC, caAndNLDC.getInner("nl"));
    assertEquals(wholeNumberDC, caAndNLDC.getInner("wholeNumber"));

    assertEquals(nlDC, rcxcyAndNLDC.getInner("nl"));
    assertEquals(wholeNumberDC, rcxcyAndNLDC.getInner("wholeNumber"));

    //llFromCY
    assertEquals(cyAndNLDC, llFromCYDC.getInner("cyAndNL"));
    assertEquals(llcyAndNLDC, llFromCYDC.getInner("llcyAndNL"));

    assertEquals(nlDC, cyAndNLDC.getInner("nl"));
    assertEquals(wholeNumberDC, cyAndNLDC.getInner("wholeNumber"));

    assertEquals(nlDC, llcyAndNLDC.getInner("nl"));
    assertEquals(wholeNumberDC, llcyAndNLDC.getInner("wholeNumber"));

    //virtual
    assertEquals(wholeNumberDC, virtualDC.getInner("wholeNumber"));
    assertEquals(vcxRcxAndLODC, virtualDC.getInner("vcxRCXAndLO"));

    assertEquals(wholeNumberDC, vcxRcxAndLODC.getInner("wholeNumber"));

  }

  @Test
  void setInners() {
    assertEquals("coordinates", cursorPositionDCB.getLayer(0).name);
    assertEquals("llFromCY", cursorPositionDCB.getLayer(1).name);
    assertEquals("virtual", cursorPositionDCB.getLayer(2).name);

    assertNotNull(coordinatesDCB.getInner("caAndNL"));
    assertNotNull(coordinatesDCB.getInner("rcxcyAndNL"));

    assertNotNull(llFromCYDCB.getInner("cyAndNL"));
    assertNotNull(llFromCYDCB.getInner("llcyAndNL"));

    assertNotNull(virtualDCB.getInner("ll"));
    assertNotNull(virtualDCB.getInner("vcxRCXAndLO"));

    assertNotNull(caAndNLDCB.getInner("ca"));
    assertNotNull(caAndNLDCB.getInner("nl"));

    assertNotNull(rcxcyAndNLDCB.getInner("rcx"));
    assertNotNull(rcxcyAndNLDCB.getInner("cy"));
    assertNotNull(rcxcyAndNLDCB.getInner("nl"));

    assertNotNull(cyAndNLDCB.getInner("cy"));
    assertNotNull(cyAndNLDCB.getInner("nl"));

    assertNotNull(llcyAndNLDCB.getInner("ll"));
    assertNotNull(llcyAndNLDCB.getInner("cy"));
    assertNotNull(llcyAndNLDCB.getInner("nl"));
    assertNull(llcyAndNLDCB.getInner("bogusInner"));

    assertNotNull(vcxRcxAndLODCB.getInner("vcx"));
    assertNotNull(vcxRcxAndLODCB.getInner("rcx"));
    assertNotNull(vcxRcxAndLODCB.getInner("lo"));

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

    ArrayList<OuterDataClassBrick> rcxcyAndNLOutersAsBricks = rcxcyAndNLDCB.getOuters();
    ArrayList<String> rcxcyAndNLOuters = getOuterNamesFromBricks(rcxcyAndNLOutersAsBricks);
    assertTrue(rcxcyAndNLOuters.size() == 1);
    assertTrue(rcxcyAndNLOuters.contains("coordinates"));

    ArrayList<OuterDataClassBrick> nlOutersAsBricks = nlDCB.getOuters();
    ArrayList<String> nlOuters = getOuterNamesFromBricks(nlOutersAsBricks);
    assertTrue(nlOuters.size() == 4);
    assertTrue(nlOuters.contains("caAndNL"));
    assertTrue(nlOuters.contains("rcxcyAndNL"));
    assertTrue(nlOuters.contains("cyAndNL"));
    assertTrue(nlOuters.contains("llcyAndNL"));

    ArrayList<OuterDataClassBrick> caOutersAsBricks = caDCB.getOuters();
    ArrayList<String> caOuters = getOuterNamesFromBricks(caOutersAsBricks);
    assertTrue(caOuters.size() == 1);
    assertTrue(caOuters.contains("caAndNL"));

    ArrayList<OuterDataClassBrick> vcxOutersAsBricks = vcxDCB.getOuters();
    ArrayList<String> vcxOuters = getOuterNamesFromBricks(vcxOutersAsBricks);
    assertTrue(vcxOuters.size() == 1);
    assertTrue(vcxOuters.contains("vcxRCXAndLO"));

    ArrayList<OuterDataClassBrick> rcxOutersAsBricks = rcxDCB.getOuters();
    ArrayList<String> rcxOuters = getOuterNamesFromBricks(rcxOutersAsBricks);
    assertTrue(rcxOuters.size() == 2);
    assertTrue(rcxOuters.contains("rcxcyAndNL"));
    assertTrue(rcxOuters.contains("vcxRCXAndLO"));

    ArrayList<OuterDataClassBrick> cyOutersAsBricks = cyDCB.getOuters();
    ArrayList<String> cyOuters = getOuterNamesFromBricks(cyOutersAsBricks);
    assertTrue(cyOuters.size() == 3);
    assertTrue(cyOuters.contains("rcxcyAndNL"));
    assertTrue(cyOuters.contains("cyAndNL"));
    assertTrue(cyOuters.contains("llcyAndNL"));

    ArrayList<OuterDataClassBrick> llOutersAsBricks = llDCB.getOuters();
    ArrayList<String> llOuters = getOuterNamesFromBricks(llOutersAsBricks);
    assertTrue(llOuters.size() == 2);
    assertTrue(llOuters.contains("llcyAndNL"));
    assertTrue(llOuters.contains("virtual"));

    ArrayList<OuterDataClassBrick> loOutersAsBricks = loDCB.getOuters();
    ArrayList<String> loOuters = getOuterNamesFromBricks(loOutersAsBricks);
    assertTrue(loOuters.size() == 1);
    assertTrue(loOuters.contains("vcxRCXAndLO"));

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
    assertFalse(llFromCYDCB.isComplete());
    assertFalse(virtualDCB.isComplete());
    assertFalse(nlDCB.isComplete());
    assertFalse(caDCB.isComplete());
    assertFalse(rcxDCB.isComplete());
    assertFalse(cyAndNLDCB.isComplete());
    assertFalse(llcyAndNLDCB.isComplete());
    assertFalse(cyDCB.isComplete());
    assertFalse(vcxDCB.isComplete());
    assertFalse(llDCB.isComplete());
    assertFalse(loDCB.isComplete());

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

    //rcxcyAndNL
    assertTrue(nlDCB.isComplete());
    assertFalse(rcxcyAndNLDCB.isComplete());
    rcxDCB.put(0);
    assertTrue(rcxDCB.isComplete());
    assertFalse(rcxcyAndNLDCB.isComplete());

    cyDCB.put(0);
    assertTrue(cyDCB.isComplete());
    assertTrue(rcxcyAndNLDCB.isComplete());

    assertTrue(coordinatesDCB.isComplete());

    //llFromCY
    //cyAndNL
    assertTrue(cyDCB.isComplete());
    assertTrue(nlDCB.isComplete());
    assertTrue(cyAndNLDCB.isComplete());

    //llcyAndNL
    assertFalse(llDCB.isComplete());
    assertTrue(cyDCB.isComplete());
    assertTrue(nlDCB.isComplete());
    assertFalse(llcyAndNLDCB.isComplete());
    cyDCB.put(2);
    assertTrue(cyDCB.isComplete());
    llDCB.put(10);
    assertTrue(llDCB.isComplete());
    assertFalse(cyDCB.isComplete());
    assertFalse(llcyAndNLDCB.isComplete()); //llcyAndNLDCB cannot possibly be set as of right now

    //virtual
    //ll
    llDCB.remove();
    assertFalse(virtualDCB.isComplete());
    assertFalse(llDCB.isComplete());
    llDCB.put(0);
    assertTrue(llDCB.isComplete());
    assertFalse(virtualDCB.isComplete());

    //vcxRCXAndLO
    assertFalse(vcxRcxAndLODCB.isComplete());
    rcxDCB.put(1);
    assertEquals(1, rcxDCB.getVal());
    loDCB.put(0);
    assertEquals(0, loDCB.getVal());
    assertTrue(vcxRcxAndLODCB.isComplete());
    assertTrue(virtualDCB.isComplete());

  }

  @Test
  void putWhenNotComplete() {
    //coordinates
    //set caAndNL when rcxcyAndNL is unset
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

    //set rcxcyAndNL when caAndNL is unset
    assertFalse(caAndNLDCB.isComplete());
    assertFalse(rcxcyAndNLDCB.isComplete());

    rcxDCB.put(0);
    assertEquals(0, rcxDCB.getVal());
    assertFalse(rcxcyAndNLDCB.isComplete());
    cyDCB.put(1);
    assertEquals(1, cyDCB.getVal());
    assertTrue(rcxcyAndNLDCB.isComplete());

    //llFromCY
    //cyAndNL can be set when llcyAndNL are unset
    llcyAndNLDCB.remove();
    assertFalse(llcyAndNLDCB.isComplete());
    cyDCB.put(3);
    assertEquals(3, cyDCB.getVal());
    assertTrue(cyAndNLDCB.isComplete());

    //llcyAndNL CAN'T be set when nl and cy are unset, because setting ll with put unsets cy
    cyAndNLDCB.remove();
    assertFalse(cyAndNLDCB.isComplete());
    assertFalse(llcyAndNLDCB.isComplete());
    llDCB.put(10);
    assertEquals(10, llDCB.getVal());
    assertFalse(llcyAndNLDCB.isComplete());
    assertFalse(cyAndNLDCB.isComplete());

    //virtual
    //set ll within virtual when ll was previously unset
    assertFalse(vcxRcxAndLODCB.isComplete());
    llDCB.remove();
    assertFalse(llDCB.isComplete());

    llDCB.put(10);
    assertEquals(10, llDCB.getVal());
    assertTrue(llDCB.isComplete());

    //set vcxRCXAndLO when ll is unset
    llDCB.remove();
    vcxRcxAndLODCB.remove();
    assertFalse(llDCB.isComplete());
    assertFalse(vcxRcxAndLODCB.isComplete());
    vcxDCB.put(4);
    assertEquals(4, vcxDCB.getVal());
    assertFalse(vcxRcxAndLODCB.isComplete());
    loDCB.put(-1);
    assertEquals(-1, loDCB.getVal());
    assertTrue(vcxRcxAndLODCB.isComplete());

  }

  @Test
  void putWhenComplete() {
    //coordinates
    //setting caAndNL unsets rcxcyAndNL if rcxcyAndNL is set
    rcxDCB.put(0);
    cyDCB.put(1);
    ArrayList<Integer> nl = new ArrayList<>();
    nl.add(13);
    nlDCB.put(nl);
    assertTrue(rcxcyAndNLDCB.isComplete());
    assertFalse(caAndNLDCB.isComplete());
    caDCB.put(0);
    assertFalse(rcxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());

    //setting rcxcyAndNL unsets caAndNL if caAndNL is set
    rcxDCB.put(1);
    assertTrue(rcxDCB.isComplete());
    assertTrue(nlDCB.isComplete());
    cyDCB.put(2);
    assertEquals(2, cyDCB.getVal());
    assertTrue(rcxcyAndNLDCB.isComplete());
    assertFalse(caAndNLDCB.isComplete());

    //llFromCY
    //setting ll unsets cyAndNL if cyAndNL is set
    assertTrue(cyAndNLDCB.isComplete());
    llDCB.put(10);
    assertTrue(llDCB.isComplete());
    assertFalse(cyDCB.isComplete());
    assertFalse(cyAndNLDCB.isComplete());
    assertFalse(llcyAndNLDCB.isComplete());

    //virtual
    //llDCB.put unsets cy
    cyDCB.put(12);
    assertFalse(llDCB.isComplete());
    assertEquals(12, cyDCB.getVal());
    llDCB.put(12);
    assertTrue(llDCB.isComplete());
    assertFalse(cyDCB.isComplete());

    //rcxDCB.put always unsets ca
    vcxRcxAndLODCB.remove();
    caDCB.put(1);
    assertFalse(vcxRcxAndLODCB.isComplete());
    assertEquals(1, caDCB.getVal());
    rcxDCB.put(1); //even if it's the same number being set, it doesn't matter, ca should still get removed
    assertFalse(caDCB.isComplete());
    assertFalse(vcxRcxAndLODCB.isComplete());

    //vcxDCB.put doesn't unset anything if only one var is set
    vcxDCB.put(2);
    assertTrue(vcxDCB.isComplete());
    assertTrue(rcxDCB.isComplete());
    assertFalse(loDCB.isComplete());

    //loDCB.put will unset all other bricks since it will make nonEmpties > requiredSetValues
    loDCB.put(4);
    assertEquals(4, loDCB.getVal());
    assertFalse(vcxDCB.isComplete());
    assertFalse(rcxDCB.isComplete());
    assertFalse(vcxRcxAndLODCB.isComplete());

  }

  @Test void getOrCalc() {
    //coordinatesDC
    //1 if nl = [12, 25, 32], rcx = 5, and cy = 1, ca can be calculated = 17
    // (getOrCalc also works on set values such as nl, rcx and cy)
    ArrayList<Integer> nl = new ArrayList<>();
    nl.add(12);
    nl.add(25);
    nl.add(32);
    nlDCB.put(nl);
    rcxDCB.put(5);
    cyDCB.put(1);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(5, rcxDCB.getVal());
    assertEquals(1, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(5, rcxDCB.getVal());
    cyDCB.getOrCalc();
    assertEquals(1, cyDCB.getVal());

    caDCB.getOrCalc();
    assertEquals(17, caDCB.getVal());
    assertEquals(nl, nlDCB.getVal());
    assertTrue(rcxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());

    //2 if nl = [12, 25, 32], rcx = 3, and cy = 2, ca can be calculated = 28
    rcxDCB.put(3);
    cyDCB.put(2);
    assertTrue(rcxcyAndNLDCB.isComplete());
    assertEquals(3, rcxDCB.getVal());
    assertEquals(2, cyDCB.getVal());
    assertEquals(nl, nlDCB.getVal());
    caDCB.getOrCalc();
    assertEquals(28, caDCB.getVal());
    assertTrue(rcxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());

    //3 if nl = [12, 25, 32] and ca = 14, rcx should = 2 and cy should = 1
    // (getOrCalc also works on set values such as nl, and ca)
    caDCB.put(14);
    assertEquals(14, caDCB.getVal());
    assertTrue(caAndNLDCB.isComplete());
    assertFalse(rcxcyAndNLDCB.isComplete());
    nlDCB.getOrCalc();
    assertEquals(nl, nlDCB.getVal());
    caDCB.getOrCalc();
    assertEquals(14, caDCB.getVal());

    cyDCB.getOrCalc();
    assertEquals(1, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(2, rcxDCB.getVal());
    assertTrue(rcxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());
    assertTrue(coordinatesDCB.isComplete());

    //virtualDC
    //4 getLOFromVCXAndLL (lo > 0) if ll = 12, vcx = 13, and rcx = 12, lo should = 1
    vcxRcxAndLODCB.remove();
    llDCB.put(12);
    assertEquals(12, llDCB.getOrCalc().getVal().getVal());
    vcxDCB.put(13);
    assertEquals(13, vcxDCB.getOrCalc().getVal().getVal());
    rcxDCB.put(12);
    assertEquals(12, rcxDCB.getOrCalc().getVal().getVal());

    loDCB.getOrCalc();
    assertEquals(1, loDCB.getVal());

    //5 getLOFromVCXAndLL (lo < 0) if ll = 12, vcx = 10, and rcx = 10, lo should = -2
    llDCB.put(12);
    assertEquals(12, llDCB.getOrCalc().getVal().getVal());
    vcxDCB.put(10);
    assertEquals(10, vcxDCB.getOrCalc().getVal().getVal());
    rcxDCB.put(10);
    assertEquals(10, rcxDCB.getOrCalc().getVal().getVal());

    loDCB.getOrCalc();
    assertEquals(-2, loDCB.getVal());

    //6 getLOFromVCXAndLL (lo == 0) if ll = 12, vcx = 12, and rcx = 12, lo should = 0
    llDCB.put(12);
    assertEquals(12, llDCB.getOrCalc().getVal().getVal());
    vcxDCB.put(12);
    assertEquals(12, vcxDCB.getOrCalc().getVal().getVal());
    rcxDCB.put(12);
    assertEquals(12, rcxDCB.getOrCalc().getVal().getVal());

    loDCB.getOrCalc();
    assertEquals(0, loDCB.getVal());

    //7 getRCXFromVCXAndLO (lo > 0) if ll = 16, vcx = 19, and lo = 3, rcx should = 16
    llDCB.put(16);
    assertEquals(16, llDCB.getOrCalc().getVal().getVal());
    vcxDCB.put(19);
    assertEquals(19, vcxDCB.getOrCalc().getVal().getVal());
    loDCB.put(3);
    assertEquals(3, loDCB.getOrCalc().getVal().getVal());

    rcxDCB.getOrCalc();
    assertEquals(16, rcxDCB.getVal());

    //8 getRCXFromVCXAndLO (lo < 0) if ll = 16, vcx = 9, and lo = -7, rcx should = 9
    llDCB.put(16);
    assertEquals(16, llDCB.getOrCalc().getVal().getVal());
    vcxDCB.put(9);
    assertEquals(9, vcxDCB.getOrCalc().getVal().getVal());
    loDCB.put(-7);
    assertEquals(-7, loDCB.getOrCalc().getVal().getVal());

    rcxDCB.getOrCalc();
    assertEquals(9, rcxDCB.getVal());

    //9 getRCXFromVCXAndLO (lo == 0) if ll = 16, vcx = 16, and lo = 0, rcx should = 16
    llDCB.put(16);
    assertEquals(16, llDCB.getOrCalc().getVal().getVal());
    vcxDCB.put(16);
    assertEquals(16, vcxDCB.getOrCalc().getVal().getVal());
    loDCB.put(0);
    assertEquals(0, loDCB.getOrCalc().getVal().getVal());

    rcxDCB.getOrCalc();
    assertEquals(16, rcxDCB.getVal());

    //10 getVCXFromRCXAndLO (lo > 0) if ll = 33, rcx = 33, and lo = 14, vcx should = 47
    llDCB.put(33);
    assertEquals(33, llDCB.getOrCalc().getVal().getVal());
    rcxDCB.put(33);
    assertEquals(33, rcxDCB.getOrCalc().getVal().getVal());
    loDCB.put(14);
    assertEquals(14, loDCB.getOrCalc().getVal().getVal());

    vcxDCB.getOrCalc();
    assertEquals(47, vcxDCB.getVal());

    //11 getVCXFromRCXAndLO (lo < 0) if ll = 33, rcx = 10, and lo = -23, vcx should = 10
    llDCB.put(33);
    assertEquals(33, llDCB.getOrCalc().getVal().getVal());
    rcxDCB.put(10);
    assertEquals(10, rcxDCB.getOrCalc().getVal().getVal());
    loDCB.put(-23);
    assertEquals(-23, loDCB.getOrCalc().getVal().getVal());

    vcxDCB.getOrCalc();
    assertEquals(10, vcxDCB.getVal());

    //12 getVCXFromRCXAndLO (lo == 0) if ll = 33, rcx = 33, and lo = 0, vcx should = 33
    llDCB.put(33);
    assertEquals(33, llDCB.getOrCalc().getVal().getVal());
    rcxDCB.put(33);
    assertEquals(33, rcxDCB.getOrCalc().getVal().getVal());
    loDCB.put(0);
    assertEquals(0, loDCB.getOrCalc().getVal().getVal());

    vcxDCB.getOrCalc();
    assertEquals(33, vcxDCB.getVal());

    //llFromCYDC
    //7 if cy = 0, ll should = 12
    cyDCB.put(0);
    assertEquals(0, cyDCB.getVal());
    assertEquals(nl, nlDCB.getVal());

    llDCB.getOrCalc();
    assertEquals(12, llDCB.getVal());

    //8 if cy = 1, ll should = 13
    cyDCB.put(1);
    assertEquals(1, cyDCB.getVal());
    assertEquals(nl, nlDCB.getVal());

    llDCB.getOrCalc();
    assertEquals(13, llDCB.getVal());

    //9 if cy = 2, ll should = 7
    cyDCB.put(2);
    assertEquals(2, cyDCB.getVal());
    assertEquals(nl, nlDCB.getVal());

    llDCB.getOrCalc();
    assertEquals(7, llDCB.getVal());

    //using calcNeighbor
    //10 if vcx = 1, cy = 0, & nl = [12, 25, 32], then ll should = 12, rcx should = 1, and lo should = -11
    cursorPositionDCB.remove();
    nlDCB.put(nl);
    vcxDCB.put(1);
    cyDCB.put(0);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(1, vcxDCB.getVal());
    assertEquals(0, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(12, llDCB.getVal());
    assertEquals(1, rcxDCB.getVal());
    assertEquals(-11, loDCB.getVal());

    //11 if vcx = 15, cy = 0, & nl = ^, then ll should = 12, rcx should = 12, and lo should = 3
    vcxDCB.put(15);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(15, vcxDCB.getVal());
    assertEquals(0, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(12, llDCB.getVal());
    assertEquals(12, rcxDCB.getVal());
    assertEquals(3, loDCB.getVal());

    //12 if vcx = 12, cy = 1, & nl = ^, then ll should = 13, rcx should = 12, and lo should = -1
    vcxDCB.put(12);
    cyDCB.put(1);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(12, vcxDCB.getVal());
    assertEquals(1, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(13, llDCB.getVal());
    assertEquals(12, rcxDCB.getVal());
    assertEquals(-1, loDCB.getVal());

    //13 if vcx = 15, cy = 1, & nl = ^, then ll should = 13, rcx should = 13, and lo should = 2
    vcxDCB.put(15);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(15, vcxDCB.getVal());
    assertEquals(1, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(13, llDCB.getVal());
    assertEquals(13, rcxDCB.getVal());
    assertEquals(2, loDCB.getVal());

    //14 if vcx = 4, cy = 2, & nl = ^, then ll should = 7, rcx should = 4, and lo should = -3
    vcxDCB.put(4);
    cyDCB.put(2);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(4, vcxDCB.getVal());
    assertEquals(2, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(7, llDCB.getVal());
    assertEquals(4, rcxDCB.getVal());
    assertEquals(-3, loDCB.getVal());

    //15 if vcx = 8, cy = 2, & nl = ^, then ll should = 7, rcx should = 7, and lo should = 1
    vcxDCB.put(8);
    cyDCB.put(2);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(8, vcxDCB.getVal());
    assertEquals(2, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(7, llDCB.getVal());
    assertEquals(7, rcxDCB.getVal());
    assertEquals(1, loDCB.getVal());

    //16 if vcx = 9, cy = 1, & nl = ^, then ll should = 13, rcx should = 9, ca should = 21
    vcxDCB.put(9);
    cyDCB.put(1);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(9, vcxDCB.getVal());
    assertEquals(1, cyDCB.getVal());
    assertFalse(llDCB.isComplete());
    assertFalse(rcxDCB.isComplete());
    assertFalse(caDCB.isComplete());
    caDCB.getOrCalc();
    assertEquals(13, llDCB.getVal());
    assertEquals(9, rcxDCB.getVal());
    assertEquals(21, caDCB.getVal());

  }

}
