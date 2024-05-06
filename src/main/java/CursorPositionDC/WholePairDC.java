package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.DataClassBrick;
import DataClass.Result;

import java.util.HashMap;

public class WholePairDC extends CompoundDataClass {
  public WholePairDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer) {
    CursorPositionDCHolder cursorPositionDCHolder = (CursorPositionDCHolder)dcHolder;
    return CompoundDataClassBrick.make(outer, this, new HashMap<>());
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick, DCHolder dcHolder) {
    return Result.make(null, "incalculable");
  }

}
