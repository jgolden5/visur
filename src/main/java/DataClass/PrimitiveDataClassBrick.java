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
    boolean previousValIsUnset = !isComplete();
    Object oldVal = null;
    if(!previousValIsUnset) {
      oldVal = getVal();
    }
    outerDCB.putInner(name, val);
    if(!outerDCB.getCDC().conflicts(outerDCB)) {
      DataFormBrick newValAsDFB = DataFormBrick.make(getPDC().defaultDF, val);
      putDFB(newValAsDFB);
      r = Result.make();
    } else {
      if(previousValIsUnset) {
        putDFB(null);
      } else {
        putSafe(oldVal);
      }
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

  public void putDFB(DataFormBrick newDFB) {
    dfb = newDFB;
  }

}
