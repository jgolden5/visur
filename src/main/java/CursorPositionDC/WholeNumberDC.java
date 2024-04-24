package CursorPositionDC;

import DataClass.*;

public class WholeNumberDC extends PrimitiveDataClass {
  @Override
  public PrimitiveDataClassBrick makeBrick(Object val, CompoundDataClassBrick outerBrick) {
    DataForm targetDF = null;
    Object res = null;
    if(isValidInput(val)) {
      res = val;
    }
    return PrimitiveDataClassBrick.make(this, outerBrick, DataFormBrick.make(targetDF, res));
  }

  @Override
  public boolean isValidInput(Object val) {
    if(val instanceof Integer && (Integer) val >= 0) {
      return true;
    } else {
      return false;
    }
  }
}
