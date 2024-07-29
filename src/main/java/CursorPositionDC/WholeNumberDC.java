package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;

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
  public PrimitiveDataClassBrick makeBrick(String name, Object val, ArrayList<OuterDataClassBrick> outers, boolean isReadOnly) {
    PrimitiveDataClassBrick res;
    if(isValidInput(val)) {
      res = PrimitiveDataClassBrick.make(name, outers, DataFormBrick.make(defaultDF, val), this, isReadOnly);
    } else {
      res = PrimitiveDataClassBrick.make(name, outers, null, this, isReadOnly);
    }
    return res;
  }

  @Override
  public PrimitiveDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, boolean isReadOnly) {
    return PrimitiveDataClassBrick.make(name, outers, null, this, isReadOnly);
  }

}
