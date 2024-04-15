package DataClass;

public abstract class DataClassBrick {
    private final DataClass dc;
    public final CompoundDataClassBrick outer;

    DataClassBrick(DataClass dc, CompoundDataClassBrick outer) {
        this.dc = dc;
        this.outer = outer;
    }

    public CompoundDataClassBrick getOuter() {
        return outer;
    }

    public DataClass getDC() {
        return dc;
    }

}
