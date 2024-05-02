package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.DataClassBrick;
import com.ple.visur.Result;

public class CXCYADC extends CompoundDataClass {
  public CXCYADC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer) {
    return null;
  }

  @Override
  public Result<DataClassBrick> calculateInnerBrick(String name, CompoundDataClassBrick compoundDataClassBrick, CursorPositionDCHolder cursorPositionDCHolder) {
    return null;
  }
}
