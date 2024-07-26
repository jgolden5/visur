package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;

public class CADC extends PrimitiveDataClass {

  public CADC(DataForm defaultDF) {
    super(defaultDF);
  }

  @Override
  public DataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers) {
    return null;
  }

  @Override
  public boolean isValidInput(Object val) {
    return false;
  }

  @Override
  public PrimitiveDataClassBrick makeBrick(String name, Object val, ArrayList<OuterDataClassBrick> outers) {
    return null;
  }

}
