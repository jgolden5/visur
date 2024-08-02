package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.OuterDataClassBrick;
import DataClass.PrimitiveDataClassBrick;
import DataClass.Result;

import java.util.ArrayList;

public class LLFromCYDC extends CompoundDataClass {
  public LLFromCYDC(int requiredSetValues) {
    super(requiredSetValues);
  }

  @Override
  public OuterDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs) {
    return null;
  }

  @Override
  public Result<Object> calcInternal(String name, OuterDataClassBrick thisAsBrick) {
    return null;
  }
}
