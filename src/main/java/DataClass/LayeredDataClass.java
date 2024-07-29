package DataClass;

import java.util.ArrayList;
import java.util.Map;

public abstract class LayeredDataClass extends OuterDataClass {

  ArrayList<CompoundDataClass> layers = new ArrayList<>();

  public void putLayer(CompoundDataClass cdc) {
    layers.add(cdc);
  }

  public CompoundDataClass getLayer(int i) {
    return layers.get(i);
  }


  @Override
  public Result<DataClassBrick> calcInternal(String name, DataClassBrick thisAsBrick) {
    Result<DataClassBrick> r = Result.make(null, "no layers exist");
    for(CompoundDataClass layer : layers) {
      r = layer.calcInternal(name, thisAsBrick);
    }
    return r;
  }

}
