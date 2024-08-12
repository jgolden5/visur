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
    PrimitiveDataClassBrick cyDCB = reusablePDCBs[2];

    CAAndNLDC caAndNLDC = (CAAndNLDC) getInner("caAndNL");
    CompoundDataClassBrick caAndNLDCB = caAndNLDC.makeBrick("caAndNL", new ArrayList<>(), nlDCB);
    caAndNLDCB.putOuter(coordinatesDCB);

    RCXCYAndNLDC rcxcyAndNLDC = (RCXCYAndNLDC) getInner("rcxcyAndNL");
    CompoundDataClassBrick rcxcyAndNLDCB = rcxcyAndNLDC.makeBrick("rcxcyAndNL", new ArrayList<>(), nlDCB, rcxDCB, cyDCB);
    rcxcyAndNLDCB.putOuter(coordinatesDCB);

    coordinatesInners.put("caAndNL", caAndNLDCB);
    coordinatesInners.put("rcxcyAndNL", rcxcyAndNLDCB);

    return coordinatesDCB.getInitializedBrickFromInners(coordinatesInners);
  }

  @Override
  public Result<Object> calcInternal(String targetName, OuterDataClassBrick thisAsBrick) {
    Result<Object> r;
    PrimitiveDataClassBrick[] coordinatesVars = getAllCoordinatePDCBs(thisAsBrick);
    if (targetName.equals("ca")) {
      r = calcCAAndNL(coordinatesVars);
    } else if (targetName.equals("rcx") || targetName.equals("cy")) {
      r = calcRCXCYAndNL(targetName, coordinatesVars);
    } else {
      r = Result.make(null, "name not recognized");
    }
    return r;
  }

  private PrimitiveDataClassBrick[] getAllCoordinatePDCBs(OuterDataClassBrick thisAsBrick) {
    CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
    CompoundDataClassBrick caAndNLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("caAndNL");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("ca");
    PrimitiveDataClassBrick nlDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("nl");
    CompoundDataClassBrick rcxCYAndNLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("rcxcyAndNL");
    PrimitiveDataClassBrick rcxDCB = (PrimitiveDataClassBrick) rcxCYAndNLDCB.getInner("rcx");
    PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) rcxCYAndNLDCB.getInner("cy");
    return new PrimitiveDataClassBrick[]{caDCB, nlDCB, rcxDCB, cyDCB};
  }

  private Result<Object> calcCAAndNL(PrimitiveDataClassBrick[] coordinateBricks) {
    PrimitiveDataClassBrick caDCB = coordinateBricks[0];
    PrimitiveDataClassBrick nlDCB = coordinateBricks[1];
    PrimitiveDataClassBrick rcxDCB = coordinateBricks[2];
    PrimitiveDataClassBrick cyDCB = coordinateBricks[3];
    ArrayList<Integer> nl = (ArrayList<Integer>) nlDCB.getVal();
    int rcx = (int)rcxDCB.getVal();
    int cy = (int)cyDCB.getVal();
    int lineStart = cy > 0 ? nl.get(cy - 1) : 0;
    int ca = lineStart + rcx;
    caDCB.cacheVal(ca);
    return Result.make(ca, null);
  }

  private Result<Object> calcRCXCYAndNL(String name, PrimitiveDataClassBrick[] coordinateBricks) {
    PrimitiveDataClassBrick caDCB = coordinateBricks[0];
    PrimitiveDataClassBrick nlDCB = coordinateBricks[1];
    PrimitiveDataClassBrick rcxDCB = coordinateBricks[2];
    PrimitiveDataClassBrick cyDCB = coordinateBricks[3];
    int ca = (int)caDCB.getVal();
    ArrayList<Integer> nl = (ArrayList<Integer>) nlDCB.getVal();
    int cy;
    for(cy = 0; cy < nl.size(); cy++) {
      int nextLineStart = nl.get(cy);
      if(nextLineStart > ca) break;
    }
    int currentLineStart = cy > 0 ? nl.get(cy - 1) : 0;
    int rcx = ca - currentLineStart;
    Result<Object> r = Result.make(null, "name not recognized");
    if(name.equals("rcx")) {
      r = Result.make(rcx, null);
    } else if(name.equals("cy")) {
      r = Result.make(cy, null);
    }
    rcxDCB.cacheVal(rcx);
    cyDCB.cacheVal(cy);
    return r;
  }

}
