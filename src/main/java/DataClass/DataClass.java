package DataClass;

public abstract class DataClass {
  public Result<DataClassBrick> calc(DataClassBrick dcb) {
    return calcInternal(dcb);
  }
  public abstract Result<DataClassBrick> calcInternal(DataClassBrick dcb);
}
