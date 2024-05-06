package DataClass;

public abstract class DataClass {
  public abstract Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick, DCHolder dcHolder);
}
