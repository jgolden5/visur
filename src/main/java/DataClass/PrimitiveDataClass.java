package DataClass;

public abstract class PrimitiveDataClass extends DataClass {
    public abstract PrimitiveDataClassBrick makeBrick(String name, Object val, CompoundDataClassBrick outerBrick, DCHolder dcHolder);
    public abstract boolean isValidInput(Object val); //determines whether a brick can be made from the value passed into makeBrick
}
