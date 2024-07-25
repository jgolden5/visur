package DataClass;

import java.util.ArrayList;

public class LayeredDataClassBrick extends DataClassBrick {

  private LayeredDataClass ldc;
  ArrayList<CompoundDataClassBrick> layers;

  public static LayeredDataClassBrick make(String name, LayeredDataClass ldc, ArrayList<CompoundDataClassBrick> layers) {
    return new LayeredDataClassBrick(name, ldc, layers);
  }

  LayeredDataClassBrick(String name, LayeredDataClass ldc, ArrayList<CompoundDataClassBrick> layers) {
    super(ldc, null, name);
    this.ldc = ldc;
    this.layers = layers;
  }

  public CompoundDataClassBrick getLayer(int i) {
    return layers.get(i);
  }

  public void putLayer(CompoundDataClassBrick cdcb) {
    layers.add(cdcb);
  }

  public LayeredDataClass getLDC() {
    return ldc;
  }

  public Result<DataClassBrick> getOrCalc(String name) {
    Result r = Result.make(null, "name not contained in layers");
    for(CompoundDataClassBrick layer : layers) {
      if (layer.containsName(name)) {
        r = layer.getOrCalc(name);
      }
    }
    return r;
  }

  @Override
  public boolean isComplete() {
    for(CompoundDataClassBrick layer : layers) {
      if(!layer.isComplete()) {
        return false;
      }
    }
    return true;
  }

  public LayeredDataClassBrick initLayersAndReturnBrick(ArrayList<CompoundDataClassBrick> layers) {
    this.layers = layers;
    return this;
  }

  public Result<DataClassBrick> calc(String innerName) {
    Result r = getLDC().calc(innerName, this);
    if(r == null && getOuter() != null) {
      return getOuter().calc(innerName);
    } else {
      return r;
    }
  }

}
