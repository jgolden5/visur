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
  public PrimitiveDataClassBrick makeBrick(String name, Object val, ArrayList<OuterDataClassBrick> outers) {
    PrimitiveDataClassBrick res;
    if(isValidInput(val)) {
      res = PrimitiveDataClassBrick.make(name, outers, DataFormBrick.make(defaultDF, val), this);
    } else {
      res = PrimitiveDataClassBrick.make(name, outers, null, this);
    }
    return res;
  }

  @Override
  public PrimitiveDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers) {
    return PrimitiveDataClassBrick.make(name, outers, null, this);
  }

}
