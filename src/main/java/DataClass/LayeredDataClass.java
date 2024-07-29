package DataClass;

import java.util.ArrayList;
import java.util.Map;

public abstract class LayeredDataClass implements OuterDataClass {

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

  @Override
  public boolean conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal) {
    return false;
  }

  @Override
  public void removeConflicts(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal) {

  }

}
