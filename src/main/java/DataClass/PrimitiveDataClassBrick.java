package DataClass;


import CursorPositionDC.CursorPositionDCHolder;
import CursorPositionDC.WholeNumberDC;

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

  public Result putSafe(PrimitiveDataClass pdc, String innerName, Object val, CompoundDataClassBrick outerBrick, DCHolder dcHolder) {
    boolean conflicts = outer.getCDC().conflicts(outer);
    if(!conflicts) {
      PrimitiveDataClassBrick resultingBrick = pdc.makeBrick(innerName, val, outerBrick, dcHolder);
      outerBrick.putInner(resultingBrick);
      return Result.make();
    } else {
      return Result.make(null, "values conflict");
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
