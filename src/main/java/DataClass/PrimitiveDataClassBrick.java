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

  @Override
  public Result<DataClassBrick> getOrCalc(String name, DCHolder dcHolder) {
    return outer.getOrCalc(name, dcHolder);
  }

  public Result putSafe(Object val) {
    Result r;
    CompoundDataClassBrick outerDCB = getOuter();
    outerDCB.putInner(name, val);
    if(!outerDCB.getCDC().conflicts(outerDCB)) {
      r = Result.make();
    } else {
      outerDCB.getInner(name).remove();
      r = Result.make(null, "inners conflict");
    }
    return r;
  }

  public Result putForce(String name) {
    return null;
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

  protected void putDFB(DataFormBrick newDFB) {
    dfb = newDFB;
  }
}
