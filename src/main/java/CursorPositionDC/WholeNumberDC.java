package CursorPositionDC;

import DataClass.*;

public class WholeNumberDC extends PrimitiveDataClass {
  @Override
  public PrimitiveDataClassBrick makeBrick(String name, Object val, CompoundDataClassBrick outerBrick, DCHolder dcHolder) {
    PrimitiveDataClassBrick res;
    if(isValidInput(val)) {
      CursorPositionDCHolder cursorPositionDCHolder = (CursorPositionDCHolder) dcHolder;
      DataForm targetDF = cursorPositionDCHolder.javaIntDF;
      res = PrimitiveDataClassBrick.make(outerBrick, this, DataFormBrick.make(targetDF, val));
    } else {
      res = PrimitiveDataClassBrick.make(outerBrick, this, null);
    }
    res.putName(name);
    return res;
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
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick, DCHolder dcHolder) {
    return Result.make(null, "incalculable");
  }

  @Override
  public DataClassBrick makeBrick() {
    return null;
  }

  @Override
  public DataClassBrick makeBrick(CompoundDataClassBrick outer) {
    return null;
  }

}
