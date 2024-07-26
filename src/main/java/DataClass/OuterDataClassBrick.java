package DataClass;

import java.util.ArrayList;

public abstract class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    super(dc, outers, name);
  }

  public abstract Result<DataClassBrick> calc(String innerName);
  public abstract void removeConflicts(String name, Object val);
  public abstract ConflictsCheckResult conflictsCheck(String name, Object val);

}
