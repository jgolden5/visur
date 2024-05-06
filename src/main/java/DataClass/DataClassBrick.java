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

    public Result<DataClassBrick> calc(String name, DCHolder dcHolder) {
      Result r;
      CompoundDataClassBrick outerBrick = getOuter();
      CompoundDataClass outerDC = outerBrick.getCDC();
      boolean canSet = true;
      if(outerDC != null) {
        CompoundDataClassBrick outerOuterBrick = outerBrick.getOuter();
        canSet = outerDC.checkCanSet(outerBrick, outerOuterBrick, dcHolder);
      }
      CompoundDataClassBrick outerOuterBrick = outerBrick.getOuter();
      if(canSet) {
        r = dc.calcInternal(name, this, dcHolder);
        if (r.getError() != null && outer != null) {
          r = outer.calc(name, dcHolder);
        }
      } else {
        r = Result.make(null, "can't set");
      }
      return r;
    }

    public abstract boolean isComplete();

}
