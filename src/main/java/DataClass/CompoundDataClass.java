package DataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class CompoundDataClass implements OuterDataClass {
  HashMap<String, DataClass> inners = new HashMap<>();
  int requiredSetValues;
  public CompoundDataClass(int requiredSetValues) {
    this.requiredSetValues = requiredSetValues;
  }

  public DataClass getInner(String innerName) {
    return inners.get(innerName);
  }
  public void putInner(String innerName, DataClass innerVal) {
    inners.put(innerName, innerVal);
  }

  @Override
  public abstract Result<DataClassBrick> calcInternal(String name, DataClassBrick outerAsBrick);

  @Override
  public abstract ConflictsCheckResult conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal);

  /**
   * if conflictsCheck(thisAsBrick, targetName, targetVal)...
   * loop through every inner in thisAsBrick...
   * if !inner.equals targetName AND !inner.innerKeys.contains(targetName) call inner.remove()
   * @param brick source of all relevant brick data
   * @param targetName name of brick inner which targetVal is being added to
   * @param targetVal value which will be assigned to targetName brick
   */
  @Override
  public void removeConflicts(OuterDataClassBrick brick, String targetName, Object targetVal) {
    CompoundDataClassBrick thisAsBrick = (CompoundDataClassBrick)brick;
    ConflictsCheckResult ccr = conflictsCheck(thisAsBrick, targetName, targetVal);
    if(ccr != ConflictsCheckResult.no) {
      for(Map.Entry<String, DataClassBrick> inner : thisAsBrick.inners.entrySet()) {
        if(inner.equals(targetName) || inner.getValue().containsName(targetName)) {
          continue;
        }
        inner.getValue().remove();
      }
    }
  }

}
