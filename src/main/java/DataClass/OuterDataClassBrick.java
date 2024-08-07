package DataClass;

import java.util.ArrayList;
import java.util.Stack;

public abstract class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    super(dc, outers, name);
  }

  public Result<Object> getOrCalc(String targetName, Stack<DataClassBrick> innerToOuterBricks) {
    Result<Object> r = Result.make();
    for(OuterDataClassBrick outer : getOuters()) {
      if(outer.isComplete()) {
        innerToOuterBricks.push(this);
        outer.getOrCalc(targetName, innerToOuterBricks);
      }
    }
    return r;
  }

  public abstract Result<Object> calc(String name);

  public abstract Result<PrimitiveDataClassBrick> get(String targetName);

  public abstract void conflictsCheckAndRemove(String name, Object val);

}
