package DataClass;

public abstract class DataClassBrick {
    public final CompoundDataClassBrick outer;
    public String name;

    DataClassBrick(CompoundDataClassBrick outer) {
        this.outer = outer;
    }

    public CompoundDataClassBrick getOuter() {
        return outer;
    }

    public abstract boolean isComplete();

}
