package CursorPositionDC;

import DataClass.*;

import java.util.ArrayList;

public class LLCYAndNLDC extends CompoundDataClass {
  public LLCYAndNLDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    return null;
  }

  @Override
  public Result<Object> calcInternal(String name, OuterDataClassBrick thisAsBrick) {
    return null;
  }
}
