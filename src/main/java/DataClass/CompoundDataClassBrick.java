package DataClass;

import java.util.HashMap;

public class CompoundDataClassBrick extends DataClassBrick {
  private CompoundDataClass cdc;
  HashMap<String, DataClassBrick> inners;
  private CompoundDataClassBrick(CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      super(cdc, outer);
      this.cdc = cdc;
      this.inners = inners;
  }
  public static CompoundDataClassBrick make(CompoundDataClassBrick outer, CompoundDataClass cdc, HashMap<String, DataClassBrick> inners) {
      return new CompoundDataClassBrick(outer, cdc, inners);
  }
  public DataClassBrick getInner(String name) {
      return inners.get(name);
  }
  public Result put(String innerName, DataClassBrick innerVal) {
    String error = null;
    if(innerVal != null) {
      CompoundDataClass thisCDC = getCDC();
      if(!thisCDC.brickCanBeSet(innerName, inners)) {
        error = "putInner failed, too many values set";
      }
      innerVal.putName(innerName);
    }
    if(error == null || outer == null) {
      inners.put(innerName, innerVal);
      return Result.make(null, error);
    } else {
      return outer.put(innerName, innerVal);
    }
  }

  public CompoundDataClass getCDC() {
    return cdc;
  }

  @Override
  public Result<DataClassBrick> getOrCalc(String name, DCHolder dcHolder) {
    DataClassBrick inner = getInner(name);
    Result<DataClassBrick> r;
    //if inner's value is set, return result whose value equals getInner(name)
    if(inner == null) {
      r = calc(name, dcHolder);
    } else {
      r = Result.make(inner, null);
    }
    CompoundDataClassBrick outer = getOuter();
    if(r.getError() != null && outer != null) {
      r = outer.getOrCalc(name, dcHolder);
    }
    return r;
  }

  @Override
  public Result put(String name) {
    return null;
  }

  @Override
  public Result forcePut(String name) {
    return null;
  }

  public Result<DataClassBrick> calc(String name, DCHolder dcHolder) {
    Result r;
    CompoundDataClassBrick outerBrick = getOuter();
    boolean canSet = cdc.checkCanSet(this, outerBrick, dcHolder);
    if(canSet) {
      r = dc.calcInternal(name, this, dcHolder);
      if (r.getError() != null && outer != null) {
        r = outer.calc(name, dcHolder);
      }
    } else {
      r = Result.make(null, "can't set");
    }
    return r;
  }

  @Override
  public boolean isComplete() {
    int numberOfSetValues = 0;
    for(DataClassBrick inner : inners.values()) {
      if(inner != null && inner.isComplete()) {
        numberOfSetValues++;
      }
    }
    return numberOfSetValues >= cdc.minimumRequiredSetValues;
  }

  public Result removeInner(String name, DCHolder dcHolder) {
    DataClassBrick inner = inners.get(name);
    if(inner instanceof PrimitiveDataClassBrick) {
      PrimitiveDataClassBrick innerPDCB = (PrimitiveDataClassBrick) inner;
      PrimitiveDataClassBrick newInner = innerPDCB.getPDC().makeBrick(null, inner.getOuter(), dcHolder);
      inners.put(name, newInner);
    }
    return Result.make();
  }
}
