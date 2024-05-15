package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.Optional;

public class WholeNumberListDC extends PrimitiveDataClass {
  @Override
  public PrimitiveDataClassBrick makeBrick(String name, Object val, CompoundDataClassBrick outerBrick, DCHolder dcHolder) {
    CursorPositionDCHolder cursorPositionDCHolder = (CursorPositionDCHolder) dcHolder;
    PrimitiveDataClassBrick res;
    if(isValidInput(val)) {
      res = PrimitiveDataClassBrick.make(outerBrick, this, DataFormBrick.make(cursorPositionDCHolder.intArrayListDF, val));
    } else {
      res = PrimitiveDataClassBrick.make(outerBrick, this, null);
    }
    res.putName(name);
    return res;
  }

  @Override
  public boolean isValidInput(Object val) {
    boolean res = false;
    if(val instanceof ArrayList) {
      ArrayList valAsArrayList = (ArrayList) val;
      if (valAsArrayList.size() > 0) {
        if (valAsArrayList.get(0) instanceof Integer) {
          res = true;
        }
      } else {
        res = true;
      }
    }
    return res;
  }

  @Override
  public Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick, DCHolder dcHolder) {
    return Result.make(null, "incalculable");
  }
}
