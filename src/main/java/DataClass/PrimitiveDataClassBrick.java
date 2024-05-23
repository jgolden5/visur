package DataClass;


public class PrimitiveDataClassBrick extends DataClassBrick {
  private final PrimitiveDataClass pdc;
  private DataFormBrick dfb;
    private PrimitiveDataClassBrick(String name, DataFormBrick dfb, PrimitiveDataClass pdc, CompoundDataClassBrick outer) {
        super(pdc, outer, name);
        this.pdc = pdc;
        this.dfb = dfb;
    }
    public static PrimitiveDataClassBrick make(String name, DataFormBrick dfb, PrimitiveDataClass pdc, CompoundDataClassBrick outer) {
        return new PrimitiveDataClassBrick(name, dfb, pdc, outer);
    }
    public DataFormBrick getDFB() {
        return dfb;
    }
    public PrimitiveDataClass getPDC() {
      return pdc;
    }

  public Result<DataClassBrick> getOrCalc() {
    Result<DataClassBrick> r = outer.getOrCalc(name);
    PrimitiveDataClassBrick pdcb = (PrimitiveDataClassBrick) r.getVal();
    if(pdcb.getDFB() != null) {
      DataFormBrick dfb = DataFormBrick.make(getPDC().defaultDF, pdcb.getVal());
      putDFB(dfb);
    } else {
      putDFB(null);
    }
    return r;
  }

  /**
   * make result var
   * call outerDCB.conflictsCheck(name, val), and assign the result to conflictsExist boolean var
   * if conflicts exist, set result var value to null with an error message stating the inners conflict
   * else, set this brick's value to dfb(javaIntDF, val), and set result var val & error to null
   * return the result var
   *
   * @param val the value attempting to be set (only succeeds if this would create no conflicts)
   * @return Result, may or may not contain an error message stating that inners conflict
   */
  public Result putSafe(Object val) {
    Result result;
    boolean conflictsExist = outer.conflictsCheck(name, val);
    if(conflictsExist) {
      result = Result.make(null, "inners conflict");
    } else {
      DataFormBrick thisDFBVal = DataFormBrick.make(pdc.defaultDF, val);
      putDFB(thisDFBVal);
      result = Result.make();
    }
    return result;
  }

  public Result putForce(Object val) {
    CompoundDataClassBrick outerDCB = getOuter();
    outerDCB.conflictsForce(getName(), val);
    DataFormBrick dfb = DataFormBrick.make(getPDC().defaultDF, val);
    putDFB(dfb);
    return outerDCB.putInner(getName(), val);
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
