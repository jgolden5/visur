package DataClass;

public abstract class DataClass {
  public Result<DataClassBrick> calc(String name, DataClassBrick dcb, DCHolder dcHolder) {
    return calcInternal(name, dcb, dcHolder);
  }
  public abstract Result<DataClassBrick> calcInternal(String name, DataClassBrick dcb, DCHolder dcHolder);
}
