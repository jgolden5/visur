package DataClass;

public abstract class PrimitiveDataClass implements DataClass {
    public abstract PrimitiveDataClassBrick makeBrick(Object val, CompoundDataClassBrick outerBrick, DCHolder dcHolder);
}
