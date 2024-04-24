package CursorPositionDC;

import DataClass.*;

public class WholeNumberDC extends PrimitiveDataClass {
  @Override
  public PrimitiveDataClassBrick makeBrick(Object val, CompoundDataClassBrick outerBrick, DCHolder dcHolder) {
    CursorPositionDCHolder cursorPositionDCHolder = (CursorPositionDCHolder) dcHolder;
    DataForm targetDF = null;
    Object res = null;
    Integer valAsInt = (Integer)val;
    if(valAsInt != null) {
      if(valAsInt >= 0) {
        targetDF = cursorPositionDCHolder.javaIntDF;
        res = val;
      }
    }
    return PrimitiveDataClassBrick.make(this, outerBrick, DataFormBrick.make(targetDF, res));
  }

  @Override
  public boolean isValidInput(Object val) {
    return false;
  }
}
