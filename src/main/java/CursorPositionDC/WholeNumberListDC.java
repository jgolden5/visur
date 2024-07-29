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
  public PrimitiveDataClassBrick makeBrick(String name, Object val, ArrayList<OuterDataClassBrick> outers, boolean isReadOnly) {
    DataFormBrick dfb = DataFormBrick.make(defaultDF, val);
    return PrimitiveDataClassBrick.make(name, outers, dfb, this, isReadOnly);
  }

  @Override
  public PrimitiveDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, boolean isReadOnly) {
    PrimitiveDataClassBrick wholeNumberListDCB = PrimitiveDataClassBrick.make(name, outers, null, this, isReadOnly);
    return wholeNumberListDCB;
  }

}
