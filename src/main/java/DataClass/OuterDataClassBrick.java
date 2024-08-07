package DataClass;

import java.util.ArrayList;
import java.util.Stack;

public abstract class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    super(dc, outers, name);
  }

  public Result<Object> getOrCalc(Stack<String> innerToOuterBrickNames) {
    Result<Object> r = Result.make(null, "no complete outer exists");
    if(isComplete()) {
      return calc(innerToOuterBrickNames);
    } else if(getOuters() != null) {
      for (OuterDataClassBrick outer : getOuters()) {
        innerToOuterBrickNames.push(getName());
        r = outer.getOrCalc(innerToOuterBrickNames);
        if(r.getError() == null) break;
      }
    }
    return r;
  }

  public abstract Result<Object> calc(Stack<String> innerToOuterBricks);

  public abstract Result<PrimitiveDataClassBrick> get(String targetName);

  public abstract void conflictsCheckAndRemove(String name, Object val);

}
