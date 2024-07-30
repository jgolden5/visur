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
    if(name.equals("ca")) {
      r = calcCAAndNL(thisAsBrick);
    } else if(name.equals("cy")) {
      r = calcRCXCYAndNL(thisAsBrick);
    } else {
      r = Result.make(null, "name not recognized");
    }
    return r;
  }

  private Result<PrimitiveDataClassBrick> calcCAAndNL(OuterDataClassBrick thisAsBrick) {
    Result<PrimitiveDataClassBrick> r;
    if(thisAsBrick.isComplete()) {
      CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) thisAsBrick;
      CompoundDataClassBrick rcxCYAndNLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("rcxCYAndNL");
      PrimitiveDataClassBrick rcxDCB = (PrimitiveDataClassBrick) rcxCYAndNLDCB.getInner("rcx");
      PrimitiveDataClassBrick cyDCB = (PrimitiveDataClassBrick) rcxCYAndNLDCB.getInner("cy");
      PrimitiveDataClassBrick nlDCB = (PrimitiveDataClassBrick) rcxCYAndNLDCB.getInner("nl");
      int rcx = (int)rcxDCB.getVal();
      int cy = (int)cyDCB.getVal();
      ArrayList<Integer> nl = (ArrayList<Integer>) nlDCB.getVal();
      int lineStart = cy > 0 ? nl.get(cy) : 0;
      int ca = lineStart + rcx;
      CompoundDataClassBrick caAndNLDCB = (CompoundDataClassBrick) thisAsCDCB.getInner("caAndNL");
      PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("ca");
      caDCB.put(ca);
      r = Result.make(ca, null);
    } else {
      r = Result.make(null, "brick incomplete, calculations impossible");
    }
    return r;
  }

  private Result<PrimitiveDataClassBrick> calcRCXCYAndNL(OuterDataClassBrick thisAsBrick) {
    return null;
  }

}
