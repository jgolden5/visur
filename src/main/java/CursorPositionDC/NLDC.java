package CursorPositionDC;

import DataClass.*;

public class NLDC extends PrimitiveDataClass {

  public NLDC(DataForm defaultDF) {
    super(defaultDF);
  }

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    return null;
  }

  @Override
  public boolean isValidInput(Object val) {
    return false;
  }

  @Override
  public PrimitiveDataClassBrick makeBrick(String name, Object val, CompoundDataClassBrick outer) {
    return null;
  }

}
