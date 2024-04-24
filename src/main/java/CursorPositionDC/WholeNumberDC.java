package CursorPositionDC;

import DataClass.*;

public class WholeNumberDC extends PrimitiveDataClass {
  @Override
  public PrimitiveDataClassBrick makeBrick(Object val, CompoundDataClassBrick outerBrick) {
    DataForm targetDF = null;
    PrimitiveDataClassBrick res;
    if(isValidInput(val)) {
      res = PrimitiveDataClassBrick.make(this, outerBrick, DataFormBrick.make(targetDF, val));
    } else {
      res = null;
    }
    return res;
  }

  @Override
  public boolean isValidInput(Object val) {
    if(val instanceof Integer && (Integer) val >= 0 || val == null) {
      return true;
    } else {
      return false;
    }
  }
}
