package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.DataClassBrick;
import DataClass.Result;

public class LongCXCYDC extends CompoundDataClass {
  public LongCXCYDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public DataClassBrick makeBrick() {
    return null;
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick) {
    return null;
  }

  @Override
  public boolean conflictsCheck(CompoundDataClassBrick brick, String targetName, Object targetVal) {
    return false;
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    return null;
  }
}
