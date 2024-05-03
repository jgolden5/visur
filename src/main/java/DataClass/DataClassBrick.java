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

    public Result<DataClassBrick> calc() {
      return dc.calc(this);
    }

    public abstract boolean isComplete();

}
