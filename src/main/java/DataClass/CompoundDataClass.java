package DataClass;

import java.util.HashMap;
import java.util.Map;

public abstract class CompoundDataClass implements OuterDataClass {
  HashMap<String, DataClass> inners = new HashMap<>();
  int minimumRequiredSetValues;
  public CompoundDataClass(int minimumRequiredSetValues) {
    this.minimumRequiredSetValues = minimumRequiredSetValues;
  }

  @Override
  public abstract CompoundDataClassBrick makeBrick(String name, CompoundDataClassBrick outer);

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
   * @param thisAsBrick source of all relevant brick data
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
