package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.ArrayList;
import java.util.HashMap;

public class CursorPositionDC extends LayeredDataClass {

  public LayeredDataClassBrick makeBrick() {
    return makeBrick("cursorPosition", null);
  }

  @Override
  public LayeredDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers) {
    LayeredDataClassBrick cursorPositionDCB = LayeredDataClassBrick.make(name, this, new ArrayList<>());

    CoordinatesDC coordinatesDC = (CoordinatesDC) getLayer(0);
    CompoundDataClassBrick coordinatesDCB = coordinatesDC.makeBrick("coordinates", new ArrayList<>());
    coordinatesDCB.putOuter(cursorPositionDCB);

    VirtualDC virtualDC = (VirtualDC) getLayer(1);
    CompoundDataClassBrick virtualDCB = virtualDC.makeBrick("virtual", new ArrayList<>());
    coordinatesDCB.putOuter(virtualDCB);

    cursorPositionDCB.putLayer(coordinatesDCB);
    cursorPositionDCB.putLayer(virtualDCB);

    return cursorPositionDCB;
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, DataClassBrick outerAsBrick) {
    return Result.make();
  }

  @Override
  public ConflictsCheckResult conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal) {
    return null;
  }

}
