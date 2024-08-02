package DataClass;

import java.util.ArrayList;

public abstract class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    super(dc, outers, name);
  }

  public abstract Result<Object> getOrCalc(String targetName);

  public abstract Result<Object> calc(String name);

  public abstract Result<PrimitiveDataClassBrick> get(String targetName);

  public abstract void conflictsCheckAndRemove(String name, Object val);

}
