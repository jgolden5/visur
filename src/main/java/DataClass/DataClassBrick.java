package DataClass;

public abstract class DataClassBrick {
    public final DataClass dc;
    public final CompoundDataClassBrick outer;
    private String name;

    DataClassBrick(DataClass dc, CompoundDataClassBrick outer) {
      this.dc = dc;
      this.outer = outer;
    }

    public CompoundDataClassBrick getOuter() {
        return outer;
    }

    public abstract Result<DataClassBrick> getOrCalc(String name, DCHolder dcHolder);

    public Result remove() {
      return getOuter().removeInner(getName());
    }

    public abstract Result put(String name); //name is unused in the case of PDCB, for put and forcePut

    public abstract Result forcePut(String name);

    public abstract boolean isComplete();

    public String getName() {
      return name;
    }

    public void putName(String name) {
      this.name = name;
    }

}
