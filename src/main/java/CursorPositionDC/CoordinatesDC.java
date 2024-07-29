package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CoordinatesDC extends CompoundDataClass {

  public CoordinatesDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, PrimitiveDataClassBrick nlDCB, PrimitiveDataClassBrick rcxDCB, ArrayList<OuterDataClassBrick> outers) {
    CompoundDataClassBrick coordinatesDCB = CompoundDataClassBrick.make(name, outers, this, new HashMap<>());
    HashMap<String, DataClassBrick> coordinatesInners = new HashMap<>();

    CAAndNLDC caAndNLDC = (CAAndNLDC) getInner("caAndNL");
    CompoundDataClassBrick caAndNLDCB = caAndNLDC.makeBrick("caAndNL", new ArrayList<>());
    caAndNLDCB.putOuter(coordinatesDCB);

    RCXCYAndNLDC rcxcyAndNLDC = (RCXCYAndNLDC) getInner("rcxcyAndNL");
    CompoundDataClassBrick rcxcyAndNLDCB = rcxcyAndNLDC.makeBrick("rcxcyAndNL", new ArrayList<>());
    rcxcyAndNLDCB.putOuter(coordinatesDCB);

    coordinatesInners.put("caAndNL", caAndNLDCB);
    coordinatesInners.put("rcxcyAndNL", rcxcyAndNLDCB);

    return coordinatesDCB.getInitializedBrickFromInners(coordinatesInners);
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, DataClassBrick outerAsBrick) {
    return null;
  }

  @Override
  public ConflictsCheckResult conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal) {
    return null;
  }

}
