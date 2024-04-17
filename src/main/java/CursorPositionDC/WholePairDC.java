package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.DataClassBrick;

public class WholePairDC extends CompoundDataClass {
  @Override
  public boolean minimumValuesAreSet(CompoundDataClassBrick cdcb, DCHolder dcHolder) {
    return false;
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
