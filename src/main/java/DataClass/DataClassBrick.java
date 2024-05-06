package DataClass;

public abstract class DataClassBrick {
    public final DataClass dc;
    public final CompoundDataClassBrick outer;
    public String name;

    DataClassBrick(DataClass dc, CompoundDataClassBrick outer) {
      this.dc = dc;
      this.outer = outer;
    }

    public CompoundDataClassBrick getOuter() {
        return outer;
    }

    public abstract boolean isComplete();

}
