package DataClass;


public class PrimitiveDataClassBrick extends DataClassBrick {
    private DataFormBrick val;
    private PrimitiveDataClassBrick(DataClass dc, CompoundDataClassBrick outer, DataFormBrick val) {
        super(dc, outer);
        this.val = val;
    }
    public static PrimitiveDataClassBrick make(DataClass dc, CompoundDataClassBrick outer, DataFormBrick val) {
        return new PrimitiveDataClassBrick(dc, outer, val);
    }
    public DataFormBrick getVal() {
        return val;
    }
}
