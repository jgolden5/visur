package CursorPositionDC;

import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.DCHolder;
import DataClass.DataClassBrick;
import com.ple.visur.Result;

import java.util.HashMap;

public class CursorPositionDC extends CompoundDataClass {
  public CursorPositionDC(int minimumRequiredSetValues) {
    super(minimumRequiredSetValues);
  }

  @Override
  public CompoundDataClassBrick makeBrick(DCHolder dcHolder, CompoundDataClassBrick outer) {
    return CompoundDataClassBrick.make(outer, this, new HashMap<>());
  }

  @Override
  public Result<DataClassBrick> calculateInnerBrick(String name, CompoundDataClassBrick compoundDataClassBrick, CursorPositionDCHolder cursorPositionDCHolder) {
    Result<DataClassBrick> res = Result.make(null, null);
    if(name.equals("cxcy")) {

    } else if(name.equals("a")) {
      
    } else if(name.equals("ni")) {
      res.putError("newline indices brick is required to be set and is therefore uncalculable");
    } else {
      res.putError("inner brick not recognized");
    }
    return res;
  }

}
