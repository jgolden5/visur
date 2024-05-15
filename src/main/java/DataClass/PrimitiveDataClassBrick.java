package DataClass;


public class PrimitiveDataClassBrick extends DataClassBrick {
  private final PrimitiveDataClass pdc;
  private DataFormBrick dfb;
    private PrimitiveDataClassBrick(CompoundDataClassBrick outer, PrimitiveDataClass pdc, DataFormBrick dfb) {
        super(pdc, outer);
        this.pdc = pdc;
        this.dfb = dfb;
    }
    public static PrimitiveDataClassBrick make(CompoundDataClassBrick outer, PrimitiveDataClass pdc, DataFormBrick val) {
        return new PrimitiveDataClassBrick(outer, pdc, val);
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

  public Result putSafe() {
    if(!outer.isComplete()) {
      return outer.putSafe(this);
    } else {
      return Result.make();
    }
  }

  @Override
  public Result forcePut(String name) {
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
