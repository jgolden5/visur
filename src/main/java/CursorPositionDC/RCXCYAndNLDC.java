package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class RCXCYAndNLDC extends CompoundDataClass {
  public RCXCYAndNLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick rcxcyAndNLDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> rcxcyAndNLInners = new HashMap<>();

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];
    PrimitiveDataClassBrick rcxDCB = reusablePDCBs[1];
    PrimitiveDataClassBrick cyDCB = reusablePDCBs[2];

    rcxDCB.putOuter(rcxcyAndNLDCB);
    cyDCB.putOuter(rcxcyAndNLDCB);
    nlDCB.putOuter(rcxcyAndNLDCB);

    rcxcyAndNLInners.put("rcx", rcxDCB);
    rcxcyAndNLInners.put("cy",  cyDCB);
    rcxcyAndNLInners.put("nl", nlDCB);

    return rcxcyAndNLDCB.getInitializedBrickFromInners(rcxcyAndNLInners);
  }

  @Override
  public Result<Object> calcInternal(Stack<String> innerToOuterBrickNames, OuterDataClassBrick thisAsBrick) {
//    ArrayList<OuterDataClassBrick> outers = thisAsBrick.getOuters();
//    OuterDataClassBrick coordinatesDCB = outers.get(0);
//    CoordinatesDC coordinatesDC = (CoordinatesDC)coordinatesDCB.dc;
//    return coordinatesDC.calcInternal(name, coordinatesDCB);
    return null;
  }

}
