package DataClass;


import java.util.ArrayList;

public class PrimitiveDataClassBrick extends DataClassBrick {
  private final PrimitiveDataClass pdc;
  private DataFormBrick dfb;
  private boolean isReadOnly;

  private PrimitiveDataClassBrick(String name, ArrayList<OuterDataClassBrick> outers, DataFormBrick dfb, PrimitiveDataClass pdc, boolean isReadOnly) {
    super(pdc, outers, name);
    this.pdc = pdc;
    this.dfb = dfb;
    this.isReadOnly = isReadOnly;
  }
  public static PrimitiveDataClassBrick make(String name, ArrayList<OuterDataClassBrick> outers, DataFormBrick dfb, PrimitiveDataClass pdc, boolean isReadOnly) {
    return new PrimitiveDataClassBrick(name, outers, dfb, pdc, isReadOnly);
  }
  public DataFormBrick getDFB() {
    return dfb;
  }
  public PrimitiveDataClass getPDC() {
    return pdc;
  }

  /** get the value stored in this if set, or calculate it in the case that this is unset (!isComplete())
   * loop through every outer of this, and if outer in outers contains this.name, check if said inner is set
   * if inner of outer contains this.name,
     * check if inner.isComplete;
       * if so, r.putVal(inner)
   * else
     * call calc on every outer until either an inner is found and able to be calculated or there are no more outers
   * return whatever result is found. If no result is found either from get or calc, the value of r is null and
   *  an error message is displayed reflecting which step failed (get or calc)
   * @return Result object which contains a value if one was found, and an error message if no value was found,
   * either through simple fetching or calculation
   */
  public Result<PrimitiveDataClassBrick> getOrCalc() {
    Result r = Result.make();
    return r;
  }


  public void put(Object val) {
    if(!isReadOnly) {
      ArrayList<OuterDataClassBrick> outers = getOuters();
      for (OuterDataClassBrick outer : outers) {
        outer.conflictsCheckAndRemove(getName(), val);
      }
    }
    DataFormBrick thisDFBVal = DataFormBrick.make(pdc.defaultDF, val);
    putDFB(thisDFBVal);
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

  public void putDFB(DataFormBrick newDFB) {
    dfb = newDFB;
  }

  @Override
  public boolean containsName(String targetName) {
    return getName().equals(targetName);
  }

  @Override
  public Result remove() {
    String error = null;
    if(!getIsReadOnly()) {
      putDFB(null);
    } else {
      error = "pdcb " + getName() + " is read-only";
    }
    return Result.make(null, error);
  }

  @Override
  public void removeConflicts(String name, Object val) {
    for(OuterDataClassBrick outer : outers) {
      outer.removeConflicts(name, val);
    }
  }

  public boolean getIsReadOnly() {
    return isReadOnly;
  }

}
