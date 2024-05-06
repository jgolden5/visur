package CursorPositionDC;

import DataClass.*;
import DataClass.Result;

import java.util.HashMap;

public class CursorPositionDC extends CompoundDataClass {
  public CursorPositionDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer) {
    return CompoundDataClassBrick.make(outer, this, new HashMap<>());
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick, DCHolder dcHolder) {
    return Result.make(null, "incalculable");
  }

}
