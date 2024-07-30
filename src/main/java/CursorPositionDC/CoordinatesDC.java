package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CoordinatesDC extends CompoundDataClass {

  public CoordinatesDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    CompoundDataClassBrick coordinatesDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> coordinatesInners = new HashMap<>();

    PrimitiveDataClassBrick nlDCB = reusablePDCBs[0];
    PrimitiveDataClassBrick rcxDCB = reusablePDCBs[1];

    CAAndNLDC caAndNLDC = (CAAndNLDC) getInner("caAndNL");
    CompoundDataClassBrick caAndNLDCB = caAndNLDC.makeBrick("caAndNL", new ArrayList<>(), nlDCB);
    caAndNLDCB.putOuter(coordinatesDCB);

    RCXCYAndNLDC rcxcyAndNLDC = (RCXCYAndNLDC) getInner("rcxcyAndNL");
    CompoundDataClassBrick rcxcyAndNLDCB = rcxcyAndNLDC.makeBrick("rcxcyAndNL", new ArrayList<>(), nlDCB, rcxDCB);
    rcxcyAndNLDCB.putOuter(coordinatesDCB);

    coordinatesInners.put("caAndNL", caAndNLDCB);
    coordinatesInners.put("rcxcyAndNL", rcxcyAndNLDCB);

    return coordinatesDCB.getInitializedBrickFromInners(coordinatesInners);
  }

  @Override
  public Result<PrimitiveDataClassBrick> calcInternal(String name, OuterDataClassBrick thisAsBrick) {
    Result<PrimitiveDataClassBrick> r;
    if(thisAsBrick.isComplete()) {
      Object[] coordinatesVars = getAllCoordinatePDCBVals(thisAsBrick);
      CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
      if (name.equals("ca")) {
        r = calcCAAndNL(thisAsCDCB, coordinatesVars);
      } else if (name.equals("cy")) {
        r = calcRCXCYAndNL(thisAsCDCB, coordinatesVars);
      } else {
        r = Result.make(null, "name not recognized");
      }
    } else {
      r = Result.make(null, "brick incomplete, calculations impossible");
    }
    return r;
  }

  private Object[] getAllCoordinatePDCBVals(OuterDataClassBrick thisAsBrick) {
    CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
    CompoundDataClassBrick caAndNLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("caAndNL");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("ca");
    PrimitiveDataClassBrick nlDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("nl");
    CompoundDataClassBrick rcxCYAndNLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("rcxCYAndNL");
    PrimitiveDataClassBrick rcxDCB = (PrimitiveDataClassBrick) rcxCYAndNLDCB.getInner("rcx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) rcxCYAndNLDCB.getInner("cy");
    int ca = caDCB.isComplete() ? (int)caDCB.getVal() : -1;
    ArrayList<Integer> nl = nlDCB.isComplete() ? (ArrayList<Integer>) nlDCB.getVal() : null;
    int rcx = rcxDCB.isComplete() ? (int)rcxDCB.getVal() : -1;
    int cy = cyDCB.isComplete() ? (int)cyDCB.getVal() : -1;
    return new Object[]{ca, nl, rcx, cy};
  }

  private Result<PrimitiveDataClassBrick> calcCAAndNL(CompoundDataClassBrick thisAsCDCB, Object[] coordinateVars) {
    ArrayList<Integer> nl = (ArrayList<Integer>)coordinateVars[1];
    int rcx = (int)coordinateVars[2];
    int cy = (int)coordinateVars[3];
    int lineStart = cy > 0 ? nl.get(cy) : 0;
    int ca = lineStart + rcx;
    CompoundDataClassBrick caAndNLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("caAndNL");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("ca");
    caDCB.put(ca);
    return Result.make(ca, null);
  }

  private Result<PrimitiveDataClassBrick> calcRCXCYAndNL(CompoundDataClassBrick thisAsCDCB, Object[] coordinateVars) {
    return null;
  }

}
