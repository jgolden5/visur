package DataClass;

import java.util.ArrayList;
import java.util.HashMap;

public interface OuterDataClass extends DataClass {

  @Override
  DataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers);

  Result<DataClassBrick> calcInternal(String name, DataClassBrick brick);
  ConflictsCheckResult conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal);

  void removeConflicts(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal);
}
