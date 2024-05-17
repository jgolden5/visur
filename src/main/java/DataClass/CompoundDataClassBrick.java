package DataClass;

import java.util.HashMap;
import java.util.Map;

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

  public Result putSafe(DataClassBrick dcb) {
    putInner(dcb);
    return Result.make();
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
      r = dc.calcInternal(name, this);
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
      if(inner.isComplete()) {
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

  public void putInner(DataClassBrick innerVal) {
    String innerName = innerVal.getName();
    inners.put(innerName, innerVal);
  }

}
