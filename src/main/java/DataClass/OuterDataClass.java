package DataClass;

public interface OuterDataClass extends DataClass {

  DataClassBrick makeBrick(String name, CompoundDataClassBrick outer);

  Result<DataClassBrick> calcInternal(String name, DataClassBrick brick);
  ConflictsCheckResult conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal);

  void removeConflicts(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal);
}
