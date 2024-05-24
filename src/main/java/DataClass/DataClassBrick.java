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
      thisAsPDCB.putDFB(null);
    } else if(this instanceof CompoundDataClassBrick) {
      CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) this;
      for(Map.Entry<String, DataClassBrick> inner : thisAsCDCB.inners.entrySet()) {
        inner.getValue().remove();
      }
    } else {
      return Result.make(null,"data type for dcb is too generic");
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
