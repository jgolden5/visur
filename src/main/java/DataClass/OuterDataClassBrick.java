package DataClass;

public class OuterDataClassBrick extends DataClassBrick {

  OuterDataClassBrick(DataClass dc, CompoundDataClassBrick outer, String name) {
    super(dc, outer, name);
  }

  @Override
  public boolean isComplete() {
    return false;
  }

}
