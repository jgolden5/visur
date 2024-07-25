package DataClass;

import java.util.ArrayList;

public abstract class LayeredDataClass implements DataClass {

  ArrayList<CompoundDataClass> layers = new ArrayList<>();

  @Override
  public DataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    return null;
  }

  public void putLayer(CompoundDataClass cdc) {
    layers.add(cdc);
  }

  public CompoundDataClass getLayer(int i) {
    return layers.get(i);
  }

  public void calc(String name, LayeredDataClassBrick thisAsBrick) {
    for(CompoundDataClass layer : layers) {
      layer.calcInternal(name, thisAsBrick);
    }
  }

}
