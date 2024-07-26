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
    ArrayList<CompoundDataClassBrick> cursorPositionDCBLayers = new ArrayList<>();
    LayeredDataClassBrick cursorPositionDCB = LayeredDataClassBrick.make(name, null, cursorPositionDCBLayers);
    //do work for initializing direct layers of cursorPositionDCB here
    return cursorPositionDCB.initLayersAndReturnBrick(cursorPositionDCBLayers);
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
