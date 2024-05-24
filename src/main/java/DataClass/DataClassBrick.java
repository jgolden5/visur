package DataClass;

import java.util.Map;

public abstract class DataClassBrick {
  public final DataClass dc;
  public final CompoundDataClassBrick outer;
  public String name;

  DataClassBrick(DataClass dc, CompoundDataClassBrick outer, String name) {
    this.dc = dc;
    this.outer = outer;
    this.name = name;
  }

  public CompoundDataClassBrick getOuter() {
    return outer;
  }

  public Result remove() {
    if(this instanceof PrimitiveDataClassBrick) {
      getOuter().removeInner(getName());
      PrimitiveDataClassBrick thisAsPDCB = (PrimitiveDataClassBrick) this;
      thisAsPDCB.putDFB(null);
    } else if(this instanceof CompoundDataClassBrick) {
      CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick)this;
      for(String innerName : thisAsCDCB.inners.keySet()) {
        thisAsCDCB.removeInner(innerName);
      }
    } else {
      return Result.make(null, "no outer found for primitiveDataClassBrick");
    }
    return Result.make();
  }

  public abstract boolean isComplete();

  public String getName() {
                          return name;
                                      }

  public void putName(String name) {
                                   this.name = name;
    }

  /**
   * checks if either this or this.inners has a name equal to targetName
   * @param targetName name to be checked in this or this.inners
   * @return whether or not targetName exists in this or this.inners
   */
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
    }
    return containsName;
  }

}
