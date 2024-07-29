package DataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompoundDataClassBrick extends OuterDataClassBrick {
  private CompoundDataClass cdc;
  HashMap<String, DataClassBrick> inners;

  private CompoundDataClassBrick(String name, ArrayList<OuterDataClassBrick> outers, DataClass cdc, HashMap<String, DataClassBrick> inners) {
    super(cdc, outers, name);
    this.cdc = (CompoundDataClass) cdc;
    this.inners = inners;
  }

  public static CompoundDataClassBrick make(String name, ArrayList<OuterDataClassBrick> outers, DataClass cdc, HashMap<String, DataClassBrick> inners) {
    return new CompoundDataClassBrick(name, outers, cdc, inners);
  }

  public DataClassBrick getInner(String name) {
      return inners.get(name);
  }

  public Result putInner(String name, DataClassBrick innerBrick) {
    String error = null;
    if(inners.containsKey(name)) {
      DataClassBrick inner = inners.get(name);
      inners.put(name, innerBrick);
    } else {
      error = "name not recognized";
    }
    return Result.make(null, error);
  }

  public CompoundDataClass getCDC() {
    return cdc;
  }

  /**
   * getInner and assign to targetInner var
   * if targetInner is unset, set r = calc(name)
   * calc method will set value equal to newly calculated value and only an error message if calc fails
   * else, r = Result.make(targetInner, null)
   * return r
   * @param name
   * @return
   */
  public Result<DataClassBrick> getOrCalc(String name) {
    DataClassBrick targetInner = getInner(name);
    Result r;
    if(targetInner.isComplete()) {
      r = Result.make(targetInner, null);
    } else {
      r = calc(name);
    }
    return r;
  }

  @Override
  public boolean isComplete() {
    int numberOfSetValues = 0;
    for(DataClassBrick inner : inners.values()) {
      if(inner.isComplete()) {
        numberOfSetValues++;
      }
    }
    return numberOfSetValues >= cdc.requiredSetValues;
  }

  public Result removeInner(String name) {
    DataClassBrick inner = inners.get(name);
    if(inner instanceof PrimitiveDataClassBrick) {
      PrimitiveDataClassBrick innerAsPDCB = (PrimitiveDataClassBrick) inner;
      innerAsPDCB.putDFB(null);
      inners.put(name, innerAsPDCB);
    } else if(inner instanceof CompoundDataClassBrick){
      CompoundDataClassBrick innerAsCDCB = (CompoundDataClassBrick) inner;
      for(String innerInnerName : innerAsCDCB.inners.keySet()) {
        innerAsCDCB.removeInner(innerInnerName);
      }
    }
    return Result.make();
  }

  public CompoundDataClassBrick getInitializedBrickFromInners(HashMap<String, DataClassBrick> cursorPositionDCBInners) {
    inners = cursorPositionDCBInners;
    return this;
  }

  public Result<DataClassBrick> calc(String innerName) {
    Result r = getCDC().calcInternal(innerName, this);
    if(r == null && getOuters() != null) {
      OuterDataClassBrick outerContainingTargetName = getOuterContainingTargetName(innerName).getVal();
      return outerContainingTargetName.calc(innerName);
    } else {
      return r;
    }
  }

  @Override
  public void removeConflicts(String targetName, Object targetVal) {
    getCDC().removeConflicts(this, targetName, targetVal);
    OuterDataClassBrick outerDCB = getOuterContainingTargetName(targetName).getVal();
    if(outerDCB != null) {
      outerDCB.removeConflicts(targetName, targetVal);
    }
  }

  /**
   * call cdc.conflictsCheck(this, name, val), and assign that result to conflictsExist boolean var
   * recursively call conflictsCheck(name, val) until either a conflict is found OR outer brick == null
   * be sure to assign the return value of the above to conflictsExist var after EVERY recursive search attempt
   * return conflictsExist var
   * @param name used for cdc.conflictsCheck
   * @param val ^
   * @return whether a conflict would hypothetically exist after calling putInner(name, val)
   */
  public boolean conflictsCheck(String name, Object val) {
    return getCDC().conflictsCheck(this, name, val);
  }

  @Override
  public void conflictsCheckAndRemove(String name, Object val) {
    boolean conflictsFound = false;
    for(OuterDataClassBrick outer : outers) {
      if (outer.conflictsCheck(getName(), val)) {
        outer.remove();
        conflictsFound = true;
        break;
      }
    }
    if(!conflictsFound) {
      for(OuterDataClassBrick outer : outers) {
        outer.conflictsCheckAndRemove(name, val);
      }
    }
  }

  @Override
  public boolean containsName(String targetName) {
    boolean containsName = false;
    if(getName().equals(targetName)) {
      containsName = true;
    } else {
      for(Map.Entry<String, DataClassBrick> inner : inners.entrySet()) {
        if (inner.getValue().containsName(targetName)) {
          containsName = true;
          break;
        }
      }
    }
    return containsName;
  }

}
