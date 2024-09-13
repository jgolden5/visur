package DataClass;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    super(dc, outers, name);
  }

  public Result<Object> getOrCalc(String targetName) {
    Result<Object> r = Result.make(null, "no complete outer exists");
    if(isComplete()) {
      return calc(targetName);
    } else if(getOuters() != null) {
      for (OuterDataClassBrick outer : getOuters()) {
        r = outer.getOrCalc(targetName);
        if(r.getError() == null) break;
      }
    }
    return r;
  }

  public abstract Result<Object> calc(String targetName);

  public abstract Result<PrimitiveDataClassBrick> get(String targetName);

  public abstract int getNumberOfSetValues();

  public abstract int getNumberOfNonEmpties();

}
