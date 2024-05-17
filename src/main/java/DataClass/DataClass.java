package DataClass;

public interface DataClass {
  DataClassBrick makeBrick();
  DataClassBrick makeBrick(String name, CompoundDataClassBrick outer);
  Result<DataClassBrick> calcInternal(String name, CompoundDataClassBrick outerAsBrick);
}
