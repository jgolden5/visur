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

  public Result<Object> getOrCalc() {
    Result<Object> r = Result.make();
    //1
    //check if this is complete
    if(isComplete()) {
      //if true, return Result.make(this, null)
      r = Result.make(this, null);
    } else {
      //else, enter loop described in step 2
      //2
      //make a loop for all outers of this
      for(OuterDataClassBrick outer : getOuters()) {
        //if(outer.isComplete)
        if(outer.isComplete()) {
          //r = outer.getInner(getName()).calc()
          r = calc(outer);
        }
      }
        //after loop = if(r.getVal() == null)
      if(r.getVal() == null) {
        //go to loop in step 3
        //3
        //for ODCB outer in outers
        for(OuterDataClassBrick outer : getOuters()) {
          //(*note that the type of outer will always be cdcb, so it can be typecast to cdcb)
          CompoundDataClassBrick outerAsCDCB = (CompoundDataClassBrick) outer;
          //for DCB inner in outer.inners
          //(*note that dcb will always be pdcb and must be typecasted as such)
          for(Map.Entry<String, DataClassBrick> innerEntry : outerAsCDCB.inners.entrySet()) {
            PrimitiveDataClassBrick inner = (PrimitiveDataClassBrick) innerEntry.getValue();
            //if !inner.isComplete()
            if(!inner.isComplete()) {
              //r = inner.getOrCalc()
              r = inner.getOrCalc();
            }

          }

        }
      }

    }

    //4

    //5 return r
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
