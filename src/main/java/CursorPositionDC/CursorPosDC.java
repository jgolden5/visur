package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.DataClassBrick;

public class CursorPosDC extends CompoundDataClass {
  public CursorPosDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public DataClassBrick derive(CompoundDataClassBrick cdcb, String innerName, DCHolder dcHolder) {
    return null;
  }

  @Override
  public CompoundDataClassBrick makeBrick(DCHolder dcHolder) {
    return null;
  }

}
