package DataClass;

import java.util.ArrayList;
import java.util.Stack;

public abstract class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    super(dc, outers, name);
  }

  public Result<Object> getOrCalc(Stack<String> innerToOuterBrickNames) {
    Result<Object> r = Result.make();
    for(OuterDataClassBrick outer : getOuters()) {
      if(!outer.isComplete()) {
        if(outer.getOuters().size() > 0) {
          for(OuterDataClassBrick outerOuter : outer.getOuters()) {
            innerToOuterBrickNames.push(getName());
            outerOuter.getOrCalc(innerToOuterBrickNames);
          }
        }
      } else {
        return outer.calc(innerToOuterBrickNames);
      }
    }
    return r;
  }

  public abstract Result<Object> calc(Stack<String> innerToOuterBricks);

  public abstract Result<PrimitiveDataClassBrick> get(String targetName);

  public abstract void conflictsCheckAndRemove(String name, Object val);

}
