package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;
import java.util.Optional;

public class WholeNumberListDC extends PrimitiveDataClass {
  public WholeNumberListDC(DataForm defaultDF) {
    super(defaultDF);
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
  public PrimitiveDataClassBrick makeBrick(String name, Object val, CompoundDataClassBrick outer) {
    return null;
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    return null;
  }

}
