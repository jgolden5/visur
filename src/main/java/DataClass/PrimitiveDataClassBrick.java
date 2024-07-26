package DataClass;


import java.util.ArrayList;

public class PrimitiveDataClassBrick extends DataClassBrick {
  private final PrimitiveDataClass pdc;
  private DataFormBrick dfb;
    private PrimitiveDataClassBrick(String name, ArrayList<OuterDataClassBrick> outers, DataFormBrick dfb, PrimitiveDataClass pdc) {
        super(pdc, outers, name);
        this.pdc = pdc;
        this.dfb = dfb;
    }
    public static PrimitiveDataClassBrick make(String name, ArrayList<OuterDataClassBrick> outers, DataFormBrick dfb, PrimitiveDataClass pdc) {
        return new PrimitiveDataClassBrick(name, outers, dfb, pdc);
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
  public Result<DataClassBrick> getOrCalc() {
    //fix
//    OuterDataClassBrick outerDCB = getOuters();
//    Result<DataClassBrick> r = outerDCB.getOrCalc(name);
//    if(r.getError() == null) {
//      PrimitiveDataClassBrick pdcb = (PrimitiveDataClassBrick) r.getVal();
//      if(pdcb.isComplete()) {
//        DataFormBrick dfb = DataFormBrick.make(getPDC().defaultDF, pdcb.getVal());
//        putDFB(dfb);
//      } else {
//        putDFB(null);
//      }
//    }
//    return r;
    return null;
  }

  /**
   * call outerDCB.conflictsForce(name, val) to unset previously set values which conflict with val
   * return outerDCB.putInner(name, val), which will return a Result which contains an error only if putInner fails
   * @param val the value which will be set
   */
  public void put(Object val) {
    //rework
//    OuterDataClassBrick outerDCB = getOuterContainingTargetName(nameOfOuterToSet).getVal();
//    outerDCB.removeConflicts(name, val);
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

}
