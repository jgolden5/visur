package DataClass;

import java.util.ArrayList;
import java.util.Map;

public abstract class DataClassBrick {
  public final DataClass dc;
  public final ArrayList<OuterDataClassBrick> outers;
  public String name;

  public DataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    this.dc = dc;
    this.outers = outers;
    this.name = name;
  }

  public ArrayList<OuterDataClassBrick> getOuters() {
    return outers;
  }

  public Result<OuterDataClassBrick> getOuterContainingTargetName(String targetName) {
    Result r = Result.make(null, "target was not found");
    for(OuterDataClassBrick outer : outers) {
      if(outer.containsName(targetName)) {
        r = Result.make(outer, null);
        break;
      }
    }
    return r;
  }

  public void putOuter(OuterDataClassBrick outer) {
    outers.add(outer);
  }

  /**
   * if this is pdcb, set dfb of this to null
   * else if this is cdcb, loop through this.inners and call remove on each inner
   * else return result with an error message "data type for dcb is too generic"
   * return Result with null value and error
   * @return whether removal was successful (in error message of Result)
   */
  public Result remove() {
    if(this instanceof PrimitiveDataClassBrick) {
      PrimitiveDataClassBrick thisAsPDCB = (PrimitiveDataClassBrick) this;
      if(!thisAsPDCB.getIsReadOnly()) {
        thisAsPDCB.putDFB(null);
      }
    } else if(this instanceof CompoundDataClassBrick) {
      CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) this;
      for (Map.Entry<String, DataClassBrick> inner : thisAsCDCB.inners.entrySet()) {
        inner.getValue().remove();
      }
    } else if(this instanceof LayeredDataClassBrick) {
      LayeredDataClassBrick thisAsLDCB = (LayeredDataClassBrick) this;
      for(CompoundDataClassBrick layer : thisAsLDCB.layers) {
        layer.remove();
      }
    } else {
      return Result.make(null,"data type for dcb is too generic");
    }
    return Result.make();
  }

  public abstract void removeConflicts(String name, Object val);

  public abstract boolean isComplete();

  public String getName() {
                          return name;
                                      }

  private void putName(String name) {
                                   this.name = name;
    }

  public boolean containsName(String targetName) {
    boolean containsName = false;
    if(getName().equals(targetName)) {
      containsName = true;
    } else if(this instanceof CompoundDataClassBrick) {
      CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) this;
      for(Map.Entry<String, DataClassBrick> inner : thisAsCDCB.inners.entrySet()) {
        if (inner.getKey().equals(targetName)) {
          containsName = true;
          break;
        }
      }
    } else if(this instanceof LayeredDataClassBrick) {
      LayeredDataClassBrick thisAsLDCB = (LayeredDataClassBrick) this;
      for(CompoundDataClassBrick layer : thisAsLDCB.layers) {
        if (layer.containsName(targetName)) {
          containsName = true;
          break;
        }
      }
    }
    return containsName;
  }

}
