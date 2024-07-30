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

  /**
   * initialize Result r var = outerDCB.getOrCalc(name)
   * initialize pdcb var = r.getVal
   * if r.getError is null, do the following 2 lines:
   * if pdcb.isComplete, initialize dfb var = DFB.make(pdc.defaultDF, pdcb.getVal), and call putDFB(dfb)
   * else putDFB(null)
   * return r
   * @return result with either calculated value and no error, or no value and an error message
   */
  public Result<PrimitiveDataClassBrick> getOrCalc() {
    Result<PrimitiveDataClassBrick> r = Result.make();
    ArrayList<OuterDataClassBrick> outerDCBs = getOuters();
    int i = 0;
    while(r.getVal() == null && i < outerDCBs.size()) {
      OuterDataClassBrick outerDCB = outerDCBs.get(i);
      r = outerDCB.getOrCalc(name);
      if (r.getError() == null) {
        PrimitiveDataClassBrick pdcb = r.getVal();
        if (pdcb.isComplete()) {
          DataFormBrick dfb = DataFormBrick.make(getPDC().defaultDF, pdcb.getVal());
          putDFB(dfb);
        } else {
          putDFB(null);
        }
      }
      i++;
    }
    return r;
  }


  public void put(Object val) {
    if(!isReadOnly) {
      ArrayList<OuterDataClassBrick> outers = getOuters();
      for (OuterDataClassBrick outer : outers) {
        outer.conflictsCheckAndRemove(getName(), val);
      }
    }
    DataFormBrick thisDFBVal = DataFormBrick.make(pdc.defaultDF, val);
    putDFB(thisDFBVal);
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
