package DataClass;

public abstract class PrimitiveDataClass implements DataClass {
    public abstract PrimitiveDataClassBrick makeBrick(Object val, CompoundDataClassBrick outerBrick);
    public abstract boolean isValidInput(Object val); //determines whether a brick can be made from the value passed into makeBrick
}
