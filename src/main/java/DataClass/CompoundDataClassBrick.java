package DataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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

  public Result<Object> calc(Stack<DataClassBrick> innerToOuterBricks) {
    Result r = getCDC().calcInternal(innerName, this);
    int i = 0;
    while(r.getVal() == null && i < outers.size()) {
      OuterDataClassBrick outerDCB = outers.get(i);
      if (outerDCB != null) {
        r = outerDCB.calc(innerToOuterBricks);
      }
      i++;
    }
    return r;
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
  public void conflictsCheckAndRemove(String name, Object val) {
    boolean conflictsFound = false;
    for(OuterDataClassBrick outer : outers) {
      if(outer instanceof LayeredDataClassBrick) {
        outer.remove();
      } else if(outer instanceof CompoundDataClassBrick) {
        CompoundDataClassBrick outerAsCDCB = (CompoundDataClassBrick) outer;
        for(DataClassBrick inner : outerAsCDCB.inners.values()) {
          if(!inner.equals(this)) {
            inner.remove();
          }
        }
      }
      conflictsFound = true;
      break;
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
