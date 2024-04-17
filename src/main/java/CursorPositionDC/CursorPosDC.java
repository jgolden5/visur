package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.DataClassBrick;

public class CursorPosDC extends CompoundDataClass {
  @Override
  public boolean minimumValuesAreSet(CompoundDataClassBrick cdcb, DCHolder dcHolder) {
      return true;
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
