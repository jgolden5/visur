package DataClass;

public abstract class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, CompoundDataClassBrick outer, String name) {
    super(dc, outer, name);
  }

  public abstract Result<DataClassBrick> calc(String innerName);
  public abstract void removeConflicts(String name, Object val);
  public abstract ConflictsCheckResult conflictsCheck(String name, Object val);

}
