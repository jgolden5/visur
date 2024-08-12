package CursorPositionDC;

import DataClass.CompoundDataClassBrick;
import DataClass.LayeredDataClassBrick;
import DataClass.OuterDataClassBrick;
import DataClass.PrimitiveDataClassBrick;
import org.junit.jupiter.api.Assertions;
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
  static VCXAndLLDC vcxAndLLDC;
  static CompoundDataClassBrick vcxAndLLDCB;
  static RCXAndLODC rcxAndLODC;
  static CompoundDataClassBrick rcxAndLODCB;
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
    vcxAndLLDC = cursorPositionDCHolder.vcxAndLLDC;
    rcxAndLODC = cursorPositionDCHolder.rcxAndLODC;
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
    vcxAndLLDCB = (CompoundDataClassBrick) virtualDCB.getInner("vcxAndLL");
    rcxAndLODCB = (CompoundDataClassBrick) virtualDCB.getInner("rcxAndLO");
    caDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("ca");
    vcxDCB = (PrimitiveDataClassBrick) vcxAndLLDCB.getInner("vcx");
    loDCB = (PrimitiveDataClassBrick) rcxAndLODCB.getInner("lo");

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

    virtualDCB.putInner("vcxAndLL", vcxAndLLDCB);
    virtualDCB.putInner("rcxAndLO", rcxAndLODCB);

    vcxAndLLDCB.putInner("vcx", vcxDCB);
    vcxAndLLDCB.putInner("ll", llDCB);

    rcxAndLODCB.putInner("rcx", rcxDCB);
    rcxAndLODCB.putInner("lo", loDCB);

    assertTrue(nlDCB.getIsReadOnly());
    assertFalse(rcxDCB.getIsReadOnly());
    assertFalse(caDCB.getIsReadOnly());
    assertFalse(cyDCB.getIsReadOnly());
    assertFalse(vcxDCB.getIsReadOnly());
    assertFalse(llDCB.getIsReadOnly());
    assertFalse(loDCB.getIsReadOnly());

  }

  @BeforeEach
  void remove() {
    boolean nlDCBWasCompleteBeforeRemoval = nlDCB.isComplete();
    cursorPositionDCB.remove();
    assertFalse(cursorPositionDCB.isComplete());
    assertFalse(coordinatesDCB.isComplete());
    assertFalse(llFromCYDCB.isComplete());
    assertFalse(virtualDCB.isComplete());
    assertFalse(cyDCB.isComplete());
    assertFalse(vcxDCB.isComplete());
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
    assertEquals(vcxAndLLDC, virtualDC.getInner("vcxAndLL"));
    assertEquals(rcxAndLODC, virtualDC.getInner("rcxAndLO"));

    assertEquals(wholeNumberDC, vcxAndLLDC.getInner("wholeNumber"));

    assertEquals(wholeNumberDC, rcxAndLODC.getInner("wholeNumber"));

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

    assertNotNull(virtualDCB.getInner("vcxAndLL"));
    assertNotNull(virtualDCB.getInner("rcxAndLO"));

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

    assertNotNull(vcxAndLLDCB.getInner("vcx"));
    assertNotNull(vcxAndLLDCB.getInner("ll"));

    assertNotNull(rcxAndLODCB.getInner("rcx"));
    assertNotNull(rcxAndLODCB.getInner("lo"));

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

    ArrayList<OuterDataClassBrick> rcxOutersAsBricks = rcxDCB.getOuters();
    ArrayList<String> rcxOuters = getOuterNamesFromBricks(rcxOutersAsBricks);
    assertTrue(rcxOuters.size() == 2);
    assertTrue(rcxOuters.contains("rcxcyAndNL"));
    assertTrue(rcxOuters.contains("rcxAndLO"));

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
    assertTrue(llOuters.contains("vcxAndLL"));

    ArrayList<OuterDataClassBrick> loOutersAsBricks = loDCB.getOuters();
    ArrayList<String> loOuters = getOuterNamesFromBricks(loOutersAsBricks);
    assertTrue(loOuters.size() == 1);
    assertTrue(loOuters.contains("rcxAndLO"));

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
    //vcxAndLL
    assertFalse(virtualDCB.isComplete());
    assertFalse(vcxAndLLDCB.isComplete());
    vcxDCB.put(0);
    assertTrue(vcxDCB.isComplete());
    assertTrue(llDCB.isComplete());
    assertTrue(vcxAndLLDCB.isComplete());

    assertTrue(virtualDCB.isComplete());

    //rcxAndLO
    assertFalse(rcxAndLODCB.isComplete());
    rcxDCB.put(1);
    assertEquals(1, rcxDCB.getVal());
    loDCB.put(0);
    assertTrue(loDCB.isComplete());
    assertTrue(rcxAndLODCB.isComplete());
    assertTrue(virtualDCB.isComplete());

    assertTrue(cursorPositionDCB.isComplete());//not possible because setting ll in llCYAndNL unsets cy,
    // making it impossible to set llFromCYDCB and therefore also impossible to set CursorPositionDCB

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

    //llcyAndNL can be set when nl and cy are unset
    cyAndNLDCB.remove();
    assertFalse(cyAndNLDCB.isComplete());
    assertFalse(llcyAndNLDCB.isComplete());
    llDCB.put(10);
    assertEquals(10, llDCB.getVal());
    assertFalse(llcyAndNLDCB.isComplete()); //actually, this is impossible because llcyAndNL currently can never be set
    assertTrue(cyAndNLDCB.isComplete());

    //virtual
    //set vcxAndLL when rcxAndLO is unset
    assertFalse(rcxAndLODCB.isComplete());
    assertFalse(vcxAndLLDCB.isComplete());

    vcxDCB.put(0);
    assertEquals(0, vcxDCB.getVal());
    assertFalse(vcxAndLLDCB.isComplete());
    llDCB.put(10);
    assertEquals(10, llDCB.getVal());
    assertTrue(vcxAndLLDCB.isComplete());

    //set rcxAndLO when vcxAndLL is unset
    vcxAndLLDCB.remove();
    assertFalse(vcxAndLLDCB.isComplete());
    rcxDCB.put(4);
    assertEquals(4, rcxDCB.getVal());
    assertFalse(rcxAndLODCB.isComplete());
    loDCB.put(-1);
    assertEquals(-1, loDCB.getVal());
    assertTrue(rcxAndLODCB.isComplete());

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

    //virtual
    //setting vcxAndLL unsets rcxAndLO if rcxAndLO is set
    assertFalse(rcxAndLODCB.isComplete());
    assertTrue(rcxDCB.isComplete());
    loDCB.put(-3);
    assertFalse(vcxAndLLDCB.isComplete());
    assertTrue(rcxAndLODCB.isComplete());
    vcxDCB.put(5);
    assertFalse(vcxAndLLDCB.isComplete());
    assertFalse(rcxAndLODCB.isComplete());
    llDCB.put(15);
    assertTrue(vcxAndLLDCB.isComplete());
    assertFalse(rcxAndLODCB.isComplete());

    //setting rcxAndLO unsets vcxAndLL if vcxAndLL is set
    rcxDCB.put(1);
    assertEquals(1, rcxDCB.getVal());
    assertTrue(rcxDCB.isComplete());
    loDCB.put(0);
    assertEquals(0, loDCB.getVal());
    assertFalse(vcxAndLLDCB.isComplete());
    assertTrue(rcxAndLODCB.isComplete());

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
    rcxDCB.getOrCalc().getVal();
    assertEquals(5, rcxDCB.getVal());
    cyDCB.getOrCalc().getVal();
    assertEquals(1, cyDCB.getVal());

    caDCB.getOrCalc().getVal();
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
    caDCB.getOrCalc().getVal();
    assertEquals(28, caDCB.getVal());
    assertTrue(rcxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());

    //3 if nl = [12, 25, 32] and ca = 14, rcx should = 2 and cy should = 1
    // (getOrCalc also works on set values such as nl, and ca)
    caDCB.put(14);
    assertEquals(14, caDCB.getVal());
    assertTrue(caAndNLDCB.isComplete());
    assertFalse(rcxcyAndNLDCB.isComplete());
    nlDCB.getOrCalc().getVal();
    assertEquals(nl, nlDCB.getVal());
    caDCB.getOrCalc().getVal();
    assertEquals(14, caDCB.getVal());

    cyDCB.getOrCalc().getVal();
    assertEquals(1, cyDCB.getVal());
    rcxDCB.getOrCalc().getVal();
    assertEquals(2, rcxDCB.getVal());
    assertTrue(rcxcyAndNLDCB.isComplete());
    assertTrue(caAndNLDCB.isComplete());
    assertTrue(coordinatesDCB.isComplete());

    //virtualDC
    //4 if vcx = 7 and ll = 5, lo should = 2, and rcx should = 5
    // (getOrCalc also works on set values such as vcx, and ll)
    coordinatesDCB.remove();
    assertFalse(coordinatesDCB.isComplete());
    vcxDCB.put(7);
    assertEquals(7, vcxDCB.getVal());
    llDCB.put(5);
    assertEquals(5, llDCB.getVal());
    vcxDCB.getOrCalc().getVal();
    assertEquals(7, vcxDCB.getVal());
    llDCB.getOrCalc().getVal();
    assertEquals(5, llDCB.getVal());

    loDCB.getOrCalc().getVal();
    assertEquals(2, loDCB.getVal());
    rcxDCB.getOrCalc().getVal();
    assertEquals(5, rcxDCB.getVal());
    assertTrue(vcxAndLLDCB.isComplete());
    assertTrue(rcxAndLODCB.isComplete());

    //5 if vcx = 19 and ll = 20, lo should = -1, and rcx should = 19
    vcxDCB.put(19);
    assertEquals(19, vcxDCB.getVal());
    llDCB.put(20);
    assertEquals(20, llDCB.getVal());
    loDCB.getOrCalc().getVal();
    assertEquals(-1, loDCB.getVal());
    rcxDCB.getOrCalc().getVal();
    assertEquals(19, rcxDCB.getVal());
    assertTrue(vcxAndLLDCB.isComplete());
    assertTrue(rcxAndLODCB.isComplete());

    //6 if lo = 5 and rcx = 10, vcx should = 15 and ll should = 10
    // (getOrCalc also works on set values such as lo, and rcx)
    loDCB.put(5);
    assertEquals(5, loDCB.getVal());
    rcxDCB.put(10);
    assertEquals(10, rcxDCB.getVal());
    loDCB.getOrCalc().getVal();
    assertEquals(5, loDCB.getVal());
    rcxDCB.getOrCalc().getVal();
    assertEquals(10, rcxDCB.getVal());

    vcxDCB.getOrCalc().getVal();
    assertEquals(15, vcxDCB.getVal());
    llDCB.getOrCalc().getVal();
    assertEquals(10, llDCB.getVal());
    assertTrue(rcxAndLODCB.isComplete());
    assertTrue(vcxAndLLDCB.isComplete());

    //7 if lo = -2 and rcx = 8, vcx should = 8 and ll should = 10
    loDCB.put(-2);
    assertEquals(-2, loDCB.getVal());
    rcxDCB.put(8);
    assertEquals(8, rcxDCB.getVal());

    vcxDCB.getOrCalc().getVal();
    assertEquals(8, vcxDCB.getVal());
    llDCB.getOrCalc().getVal();
    assertEquals(10, llDCB.getVal());
    assertTrue(rcxAndLODCB.isComplete());
    assertTrue(vcxAndLLDCB.isComplete());

    //llFromCYDC
    //8 if cy = 0, ll should = 12
    cyDCB.put(0);
    assertEquals(0, cyDCB.getVal());
    assertEquals(nl, nlDCB.getVal());

    llDCB.getOrCalc().getVal();
    assertEquals(12, llDCB.getVal());

    //9 if cy = 1, ll should = 13
    cyDCB.put(1);
    assertEquals(1, cyDCB.getVal());
    assertEquals(nl, nlDCB.getVal());

    llDCB.getOrCalc().getVal();
    assertEquals(13, llDCB.getVal());

    //10 if cy = 2, ll should = 7
    cyDCB.put(2);
    assertEquals(2, cyDCB.getVal());
    assertEquals(nl, nlDCB.getVal());

    llDCB.getOrCalc().getVal();
    assertEquals(7, llDCB.getVal());

    //11 if vcx = 1, cy = 0, & nl = [12, 25, 32], then ll should = 12 and rcx should = 1
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

    //12 if vcx = 8, cy = 0, & nl = ^, then ll should = 12 and rcx should = 8
    vcxDCB.put(8);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(8, vcxDCB.getVal());
    assertEquals(0, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(7, llDCB.getVal());
    assertEquals(8, rcxDCB.getVal());

    //13 if vcx = 12, cy = 1, & nl = ^, then ll should = 13 and rcx should = 12
    vcxDCB.put(12);
    cyDCB.put(1);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(12, vcxDCB.getVal());
    assertEquals(1, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(13, llDCB.getVal());
    assertEquals(12, rcxDCB.getVal());

    //14 if vcx = 15, cy = 1, & nl = ^, then ll should = 13 and rcx should = 13
    vcxDCB.put(15);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(15, vcxDCB.getVal());
    assertEquals(1, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(13, llDCB.getVal());
    assertEquals(13, rcxDCB.getVal());

    //15 if vcx = 4, cy = 2, & nl = ^, then ll should = 7 and rcx should = 4
    vcxDCB.put(4);
    cyDCB.put(2);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(4, vcxDCB.getVal());
    assertEquals(2, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(7, llDCB.getVal());
    assertEquals(4, rcxDCB.getVal());

    //16 if vcx = 9, cy = 2, & nl = ^, then ll should = 7 and rcx should = 7
    vcxDCB.put(9);
    cyDCB.put(2);
    assertEquals(nl, nlDCB.getVal());
    assertEquals(9, vcxDCB.getVal());
    assertEquals(2, cyDCB.getVal());
    rcxDCB.getOrCalc();
    assertEquals(7, llDCB.getVal());
    assertEquals(7, rcxDCB.getVal());

  }

}
