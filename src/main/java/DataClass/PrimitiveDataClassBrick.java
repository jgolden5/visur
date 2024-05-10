package DataClass;


public class PrimitiveDataClassBrick extends DataClassBrick {
  private final PrimitiveDataClass pdc;
  private DataFormBrick val;
    private PrimitiveDataClassBrick(CompoundDataClassBrick outer, PrimitiveDataClass pdc, DataFormBrick val) {
        super(pdc, outer);
        this.pdc = pdc;
        this.val = val;
    }
    public static PrimitiveDataClassBrick make(CompoundDataClassBrick outer, PrimitiveDataClass pdc, DataFormBrick val) {
        return new PrimitiveDataClassBrick(outer, pdc, val);
    }
    public DataFormBrick getDFB() {
        return val;
    }
    public PrimitiveDataClass getPDC() {
      return pdc;
    }

  @Override
  public Result<DataClassBrick> getOrCalc(String name, DCHolder dcHolder) {
    return outer.getOrCalc(name, dcHolder);
  }

  @Override
  public Result put(String name) {
//    if(canSet) {
      return outer.put(name);
//    }
  }

  @Override
  public Result forcePut(String name) {
    return null;
  }

  @Override
  public boolean isComplete() {
    return true;
  }
}
