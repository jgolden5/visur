package DataClass;

import java.util.HashMap;

public class CompoundDataClassBrick extends DataClassBrick {
  private CompoundDataClass cdc;
  HashMap<String, DataClassBrick> inners;
  private CompoundDataClassBrick(String name, CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      super(cdc, outer, name);
      this.cdc = cdc;
      this.inners = inners;
  }
  public static CompoundDataClassBrick make(String name, CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      return new CompoundDataClassBrick(name, outer, cdc, inners);
  }
  public DataClassBrick getInner(String name) {
      return inners.get(name);
  }
  public CompoundDataClass getCDC() {
    return cdc;
  }

  public Result<DataClassBrick> getOrCalc(String name) {
    DataClassBrick targetInner = getInner(name);
    Result<DataClassBrick> r;
    PrimitiveDataClassBrick targetInnerAsPDCB = (PrimitiveDataClassBrick) targetInner;
    //if targetInner's value is set, return result whose value equals getInner(name)
    if(targetInnerAsPDCB.getDFB() == null) {
      r = calc(name);
    } else {
      r = Result.make(targetInner, null);
    }
    CompoundDataClassBrick outer = getOuter();
    if(r.getError() != null && outer != null) {
      r = outer.getOrCalc(name);
    }
    return r;
  }

  public Result putInner(String name, Object valOfInnerBrick) {
    String error = null;
    if(inners.containsKey(name)) {
      DataClassBrick inner = inners.get(name);
      if(inner instanceof PrimitiveDataClassBrick) {
        PrimitiveDataClassBrick innerAsPDCB = (PrimitiveDataClassBrick) inner;
        PrimitiveDataClass innerPDC = innerAsPDCB.getPDC();
        PrimitiveDataClassBrick innerBrick = innerPDC.makeBrick(name, valOfInnerBrick, this);
        inners.put(name, innerBrick);
      } else {
        error = "inner is cdcb and therefore unsettable"; //this should not be possible, because putSafe should only be able to be called on pdcb, and therefore, the inner of an outer that was called by a pdcb should always be a pdcb, and if not, an error occurred
      }
    } else {
      error = "name not recognized";
    }
    return Result.make(null, error);
  }

  @Override
  public boolean isComplete() {
    int numberOfSetValues = 0;
    for(DataClassBrick inner : inners.values()) {
      if(inner.isComplete()) {
        numberOfSetValues++;
      }
    }
    return numberOfSetValues >= cdc.minimumRequiredSetValues;
  }

  public boolean isComplete(String nameToForceCount) {
    int numberOfSetValues = 0;
    for(DataClassBrick inner : inners.values()) {
      if(inner.isComplete() || inner.getName().equals(nameToForceCount)) {
        numberOfSetValues++;
      }
    }
    return numberOfSetValues >= cdc.minimumRequiredSetValues;
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

  public CompoundDataClassBrick initInners(HashMap<String, DataClassBrick> cursorPositionDCBInners) {
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

  public void conflictsForce(String targetName, Object targetVal) {
    getCDC().conflictsForce(this, targetName, targetVal);
    CompoundDataClassBrick outerBrick = getOuter();
    if(outerBrick != null) {
      outerBrick.conflictsForce(targetName, targetVal);
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
    boolean conflictsExist = cdc.conflictsCheck(this, name, val);
    while(conflictsExist || outer == null) {
      conflictsExist = outer.conflictsCheck(name, val);
    }
    return conflictsExist;
  }

}
