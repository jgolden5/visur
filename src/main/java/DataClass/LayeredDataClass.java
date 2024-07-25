package DataClass;

import java.util.ArrayList;
import java.util.Map;

public abstract class LayeredDataClass implements DataClass {

  ArrayList<CompoundDataClass> layers = new ArrayList<>();

  @Override
  public LayeredDataClassBrick makeBrick(String name, CompoundDataClassBrick outer) {
    return null;
  }

  public void putLayer(CompoundDataClass cdc) {
    layers.add(cdc);
  }

  public CompoundDataClass getLayer(int i) {
    return layers.get(i);
  }

  public Result<DataClassBrick> calc(String name, LayeredDataClassBrick thisAsBrick) {
    Result<DataClassBrick> r = Result.make(null, "no layers exist");
    for(CompoundDataClass layer : layers) {
      r = layer.calcInternal(name, thisAsBrick);
    }
    return r;
  }

  public abstract ConflictsCheckResult conflictsCheck(LayeredDataClassBrick brick, String targetName, Object targetVal);
  public void removeConflictingLayers(LayeredDataClassBrick thisAsBrick, String targetName, Object targetVal) {
    ConflictsCheckResult ccr = conflictsCheck(thisAsBrick, targetName, targetVal);
    if(ccr != ConflictsCheckResult.no) {
      for(CompoundDataClassBrick layer : thisAsBrick.layers) {
        if(layer.getName().equals(targetName) || layer.containsName(targetName)) {
          continue;
        }
        layer.remove();
      }
    }
  }

}
