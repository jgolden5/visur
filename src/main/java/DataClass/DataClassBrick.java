package DataClass;

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

}
