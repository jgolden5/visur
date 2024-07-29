package DataClass;

import java.util.ArrayList;
import java.util.HashMap;

public interface OuterDataClass extends DataClass {

  OuterDataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers, PrimitiveDataClassBrick... reusablePDCBs);

  Result<DataClassBrick> calcInternal(String name, DataClassBrick brick);
  ConflictsCheckResult conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal);

  void removeConflicts(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal);
}
