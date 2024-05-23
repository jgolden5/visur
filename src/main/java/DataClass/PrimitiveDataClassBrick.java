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

    public Result<DataClassBrick> calc() {
      return getOuter().calc(getName());
    }

  public Result<DataClassBrick> getOrCalc() {
    return outer.getOrCalc(name);
  }

  public Result putSafe(Object val) {
    Result r;
    CompoundDataClassBrick outerDCB = getOuter();
    boolean conflictsExist = outerDCB.conflictsCheck(name, val);
    if(conflictsExist) {
      r = Result.make(null, "inners conflict");
    } else {
      DataFormBrick newValAsDFB = DataFormBrick.make(getPDC().defaultDF, val);
      putDFB(newValAsDFB);
      outerDCB.putInner(getName(), val);
      r = Result.make();
    }
    return r;
  }

  public Result putForce(Object val) {
    CompoundDataClassBrick outerDCB = getOuter();
    outerDCB.conflictsForce(getName(), val);
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
