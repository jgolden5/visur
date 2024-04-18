package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.DataClassBrick;

import java.util.HashMap;

public class CursorPosDC extends CompoundDataClass {
  public CursorPosDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public DataClassBrick derive(CompoundDataClassBrick cdcb, String innerName, DCHolder dcHolder) {
    return null;
  }

  @Override
  public CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer) {
    return CompoundDataClassBrick.make(this, outer, new HashMap<>());
  }

}
