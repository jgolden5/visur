package DataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
      inners.put(name, innerBrick);
    } else {
      error = "name not recognized";
    }
    return Result.make(null, error);
  }

  public CompoundDataClass getCDC() {
    return cdc;
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

  @Override
  public boolean isEmpty() {
    for(DataClassBrick inner : inners.values()) {
      if(inner instanceof PrimitiveDataClassBrick) {
        if(inner.isComplete()) {
          return false;
        }
      } else if(inner instanceof CompoundDataClassBrick) {
        CompoundDataClassBrick innerAsCDCB = ((CompoundDataClassBrick)inner);
        for(DataClassBrick innerOfInner : innerAsCDCB.inners.values()) {
          if(!innerOfInner.isEmpty()) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public CompoundDataClassBrick getInitializedBrickFromInners(HashMap<String, DataClassBrick> cursorPositionDCBInners) {
    inners = cursorPositionDCBInners;
    return this;
  }

  @Override
  public Result<PrimitiveDataClassBrick> get(String targetName) {
    Result<PrimitiveDataClassBrick> r = Result.make();
    for(Map.Entry inner : inners.entrySet()) {
      DataClassBrick innerBrick = (DataClassBrick) inner.getValue();
      if(inner.getKey().equals(targetName) && innerBrick instanceof PrimitiveDataClassBrick) {
        r.putVal((PrimitiveDataClassBrick) innerBrick);
      }
      if(r.getVal() != null) break;
    }
    if(r.getVal() == null) {
      r.putError("inner not found with name \"" + targetName + "\"");
    }
    return r;
  }

  public Result<Object> calc(String targetName) {
    return getCDC().calcInternal(targetName, this);
  }

  @Override
  public Result remove() {
    if (inners.size() > 0) {
      for (Map.Entry<String, DataClassBrick> inner : inners.entrySet()) {
        inner.getValue().remove();
      }
    }
    return Result.make();
  }

  @Override
  public void removeConflicts(String targetName, Object targetVal) {
    getCDC().removeConflicts(this);
    OuterDataClassBrick outerDCB = getOuterContainingTargetName(targetName).getVal();
    if(outerDCB != null) {
      outerDCB.removeConflicts(targetName, targetVal);
    }
  }

  @Override
  public int getNumberOfNonEmpties() {
    int nonEmptyInners = 0;
    for(DataClassBrick inner : inners.values()) {
      if(!inner.isEmpty()) {
        nonEmptyInners++;
      }
    }
    return nonEmptyInners;
  }

  @Override
  public void removePossibleConflicts(HashSet<DataClassBrick> dcbsAlreadyChecked) {
    int nonEmpties = getNumberOfNonEmpties();
    int required = getRequiredSetValues();
    if(nonEmpties > required && !dcbsAlreadyChecked.contains(this)) {
      dcbsAlreadyChecked.add(this);
      removeInnersAndAllOutersOfInners(dcbsAlreadyChecked);
    } else if(getOuters() != null) {
      dcbsAlreadyChecked.add(this);
      for(OuterDataClassBrick outer : getOuters()) {
        outer.removePossibleConflicts(dcbsAlreadyChecked);
      }
    }
  }

  private void removeInnersAndAllOutersOfInners(HashSet<DataClassBrick> dcbsAlreadyChecked) {
    for (DataClassBrick inner : inners.values()) {
      if (!dcbsAlreadyChecked.contains(inner)) {
        if (inner instanceof PrimitiveDataClassBrick) {
          dcbsAlreadyChecked.add(inner);
          PrimitiveDataClassBrick innerAsPDCB = (PrimitiveDataClassBrick) inner;
          if(!innerAsPDCB.isReadOnly()) {
            innerAsPDCB.remove();
            innerAsPDCB.removePossibleConflicts(dcbsAlreadyChecked);
          }
        } else if (inner instanceof CompoundDataClassBrick) {
          dcbsAlreadyChecked.add(inner);
          CompoundDataClassBrick innerAsCDCB = (CompoundDataClassBrick) inner;
          innerAsCDCB.removeInnersAndAllOutersOfInners(dcbsAlreadyChecked);
        }
      }
    }
  }

  @Override
  public int getNumberOfSetValues() {
    int numberOfSetInners = 0;
    for(DataClassBrick inner : inners.values()) {
      if(inner.isComplete()) {
        numberOfSetInners++;
      }
    }
    return numberOfSetInners;
  }

  public int getRequiredSetValues() {
    return getCDC().requiredSetValues;
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
