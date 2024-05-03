package DataClass;

public abstract class DataClass {
  public Result<DataClassBrick> calc(DataClassBrick dcb, DCHolder dcHolder) {
    return calcInternal(dcb, dcHolder);
  }
  public abstract Result<DataClassBrick> calcInternal(DataClassBrick dcb, DCHolder dcHolder);
}
