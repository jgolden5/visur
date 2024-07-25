package DataClass;

import java.util.HashMap;

public class CompoundDataClassBrick extends OuterDataClassBrick {
  private CompoundDataClass cdc;
  HashMap<String, DataClassBrick> inners;

  private CompoundDataClassBrick(String name, CompoundDataClassBrick outer, DataClass cdc, HashMap<String, DataClassBrick> inners) {
    super(cdc, outer, name);
    this.cdc = (CompoundDataClass) cdc;
    this.inners = inners;
  }

  public static CompoundDataClassBrick make(String name, CompoundDataClassBrick outer, DataClass cdc, HashMap<String, DataClassBrick> inners) {
    return new CompoundDataClassBrick(name, outer, cdc, inners);
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

  public boolean isComplete(String nameToForceCount) {
    int numberOfSetValues = 0;
    for(DataClassBrick inner : inners.values()) {
      if(inner.isComplete() || inner.getName().equals(nameToForceCount)) {
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

  public CompoundDataClassBrick initInnersAndReturnBrick(HashMap<String, DataClassBrick> cursorPositionDCBInners) {
    inners = cursorPositionDCBInners;
    return this;
  }

  public Result<DataClassBrick> calc(String innerName) {
    Result r = getCDC().calcInternal(innerName, this);
    if(r == null && getOuter() != null) {
      return getOuter().calc(innerName);
    } else {
      return r;
    }
  }

  /**
   * call cdc.conflictsForce(this, targetName, targetVal)
   * if outerDCB != null, call outerDCB.conflictsForce(targetName, targetVal) so all conflicts get removed
   * @param targetName name of brick to assign targetVal to
   * @param targetVal value which will be assigned to brick at targetName
   */
  public void conflictsForce(String targetName, Object targetVal) {
    getCDC().removeConflicts(this, targetName, targetVal);
    CompoundDataClassBrick outerDCB = getOuter();
    if(outerDCB != null) {
      outerDCB.conflictsForce(targetName, targetVal);
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
  public ConflictsCheckResult conflictsCheck(String name, Object val) {
    ConflictsCheckResult ccr = cdc.conflictsCheck(this, name, val);
    if(ccr == ConflictsCheckResult.no && outer != null) {
      ccr = outer.conflictsCheck(name, val);
    }
    return ccr;
  }

}
