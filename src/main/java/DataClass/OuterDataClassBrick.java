package DataClass;

import java.util.ArrayList;

public abstract class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    super(dc, outers, name);
  }

  public abstract Result<PrimitiveDataClassBrick> get(String targetName);

  public Result<PrimitiveDataClassBrick> getOrCalc(String targetName) {
    Result<PrimitiveDataClassBrick> r = get(targetName);
    OuterDataClass odc = (OuterDataClass) dc;
    r = odc.calcInternal(targetName, this);
    return r;
  }

  public abstract Result<DataClassBrick> calc(String innerName);
  public abstract void conflictsCheckAndRemove(String name, Object val);

}
