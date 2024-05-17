package CursorPositionDC;

import DataClass.*;

public class WholeNumberDC extends PrimitiveDataClass {
  public WholeNumberDC(DataForm defaultDF) {
    super(defaultDF);
  }

  @Override
  public boolean isValidInput(Object val) {
    if(val != null && val instanceof Integer && (Integer) val >= 0) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public PrimitiveDataClassBrick makeBrick(String name, Object val, CompoundDataClassBrick outer) {
    PrimitiveDataClassBrick res;
    if(isValidInput(val)) {
      res = PrimitiveDataClassBrick.make(name, DataFormBrick.make(defaultDF, val), this, outer);
    } else {
      res = PrimitiveDataClassBrick.make(name, null, this, outer);
    }
    return res;
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    return null;
  }

}
