package DataClass;

import java.util.ArrayList;

public abstract class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    super(dc, outers, name);
  }

  public abstract Result<PrimitiveDataClassBrick> getOrCalc(String targetName);

  public abstract Result<PrimitiveDataClassBrick> calc(String name);

  public abstract Result<PrimitiveDataClassBrick> get(String targetName);

  public abstract void conflictsCheckAndRemove(String name, Object val);

}
