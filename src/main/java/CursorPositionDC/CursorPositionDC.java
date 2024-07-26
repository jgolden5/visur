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
    return LayeredDataClassBrick.make(name, this, new ArrayList<>());
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
