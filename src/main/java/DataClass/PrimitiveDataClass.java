package DataClass;

public abstract class PrimitiveDataClass implements DataClass {
  public DataForm defaultDF;
  public PrimitiveDataClass(DataForm defaultDF) {
    this.defaultDF = defaultDF;
  }
  public abstract boolean isValidInput(Object val); //determines whether a brick can be made from the value passed into makeBrick
  public abstract PrimitiveDataClassBrick makeBrick(String name, Object val, CompoundDataClassBrick outer);
}
