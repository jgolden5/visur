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

    public Result<DataClassBrick> calc(DCHolder dcHolder) {
      Result r;
      CompoundDataClassBrick outerBrick = getOuter();
      CompoundDataClass outerDC = outerBrick.getCDC();
      boolean canSet;
      if(outerDC != null) {
        CompoundDataClassBrick outerOuterBrick = outerBrick.getOuter();
        canSet = outerDC.checkCanSet(outerBrick, outerOuterBrick, dcHolder);
      } else {
        canSet = true;
      }
      CompoundDataClassBrick outerOuterBrick = outerBrick.getOuter();
      if(canSet) {
        r = dc.calcInternal(this, dcHolder);
        if (r.getError() != null && outer != null) {
          r = outer.calc(dcHolder);
        }
      } else {
        r = Result.make(null, "can't set");
      }
      return r;
    }

    public abstract boolean isComplete();

}
