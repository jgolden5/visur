package DataClass;


import java.util.ArrayList;
import java.util.Map;

public class PrimitiveDataClassBrick extends DataClassBrick {
  private final PrimitiveDataClass pdc;
  private DataFormBrick dfb;
  private boolean isReadOnly;

  private PrimitiveDataClassBrick(String name, ArrayList<OuterDataClassBrick> outers, DataFormBrick dfb, PrimitiveDataClass pdc, boolean isReadOnly) {
    super(pdc, outers, name);
    this.pdc = pdc;
    this.dfb = dfb;
    this.isReadOnly = isReadOnly;
  }
  public static PrimitiveDataClassBrick make(String name, ArrayList<OuterDataClassBrick> outers, DataFormBrick dfb, PrimitiveDataClass pdc, boolean isReadOnly) {
    return new PrimitiveDataClassBrick(name, outers, dfb, pdc, isReadOnly);
  }
  public DataFormBrick getDFB() {
    return dfb;
  }
  public PrimitiveDataClass getPDC() {
    return pdc;
  }

  public Result<PrimitiveDataClassBrick> getOrCalc(ArrayList<DataClassBrick> dcbsAlreadySearched) {
    Result<PrimitiveDataClassBrick> r = Result.make();
    Object resultingVal = null;
    if(isComplete()) {
      r = Result.make(this, null);
    } else {
      for(OuterDataClassBrick outer : getOuters()) {
        if(outer.isComplete()) {
          resultingVal = calc(outer).getVal();
        }
      }
      if(r.getVal() == null) {
        for(OuterDataClassBrick outer : getOuters()) {
          CompoundDataClassBrick outerAsCDCB = (CompoundDataClassBrick) outer;
          for(Map.Entry<String, DataClassBrick> innerEntry : outerAsCDCB.inners.entrySet()) {
            PrimitiveDataClassBrick inner = (PrimitiveDataClassBrick) innerEntry.getValue();
            if(!(dcbsAlreadySearched.contains(inner) && inner.isComplete())) {
              r = inner.getOrCalc(dcbsAlreadySearched);
              dcbsAlreadySearched.add(inner);
            }
          }
        }
      }
    }
    if(r.getVal() == null) {
      for(OuterDataClassBrick outer : getOuters()) {
        resultingVal = outer.getOrCalc(getName()).getVal();
        if(r.getVal() != null) {
          break;
        }
      }
    }
    if(resultingVal != null) {
      DataFormBrick dfb = DataFormBrick.make(pdc.defaultDF, resultingVal);
      putDFB(dfb);
      r = Result.make(this, null);
    }
    return r;
  }

  private Result<Object> calc(OuterDataClassBrick outer) {
    return outer.calc(getName());
  }

  private void putVal(Object val) {
    DataFormBrick thisDFBVal = DataFormBrick.make(pdc.defaultDF, val);
    putDFB(thisDFBVal);
  }

  public void put(Object val) {
    if(!isReadOnly) {
      ArrayList<OuterDataClassBrick> outers = getOuters();
      for (OuterDataClassBrick outer : outers) {
        outer.conflictsCheckAndRemove(getName(), val);
      }
    }
    putVal(val);
  }

  @Override
  public boolean isComplete() {
    return getDFB() != null;
  }

  public Object getVal() {
    return getDFB().getVal();
  }

  public Result<Object> get() {
    Object v = null;
    String error = null;
    if (getDFB() != null) {
      v = getDFB().getVal();
    } else {
      error = "value not set";
    }
    return Result.make(v, error);
  }

  public void putDFB(DataFormBrick newDFB) {
    dfb = newDFB;
  }

  @Override
  public boolean containsName(String targetName) {
    return getName().equals(targetName);
  }

  @Override
  public Result remove() {
    String error = null;
    if(!getIsReadOnly()) {
      putDFB(null);
    } else {
      error = "pdcb " + getName() + " is read-only";
    }
    return Result.make(null, error);
  }

  @Override
  public void removeConflicts(String name, Object val) {
    for(OuterDataClassBrick outer : outers) {
      outer.removeConflicts(name, val);
    }
  }

  public boolean getIsReadOnly() {
    return isReadOnly;
  }

}
