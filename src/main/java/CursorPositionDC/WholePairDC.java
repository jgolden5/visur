package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.DataClassBrick;

import java.util.HashMap;

public class WholePairDC extends CompoundDataClass {
  public WholePairDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public DataClassBrick derive(CompoundDataClassBrick cdcb, String innerName, DCHolder dcHolder) {
    return null;
  }

  @Override
  public CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer) {
    CursorPositionDCHolder cursorPositionDCHolder = (CursorPositionDCHolder)dcHolder;
    return CompoundDataClassBrick.make(outer, this, new HashMap<>());
  }

}
