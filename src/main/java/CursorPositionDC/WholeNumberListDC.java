package CursorPositionDC;

import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.PrimitiveDataClass;
import DataClass.PrimitiveDataClassBrick;

public class WholeNumberListDC extends PrimitiveDataClass {
  @Override
  public PrimitiveDataClassBrick makeBrick(Object val, CompoundDataClassBrick outerBrick, DCHolder dcHolder) {
    return null;
  }

  @Override
  public boolean isValidInput(Object val) {
    return false;
  }

}
