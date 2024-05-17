package DataClass;

public interface DataClass {
  Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick);
  DataClassBrick makeBrick();
  DataClassBrick makeBrick(String name, CompoundDataClassBrick outer);
}
