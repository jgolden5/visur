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
  public abstract ConflictsCheckResult conflictsCheck(OuterDataClassBrick thisAsBrick, String targetName, Object targetVal);

  @Override
  public void removeConflicts(OuterDataClassBrick brick, String targetName, Object targetVal) {
    LayeredDataClassBrick thisAsBrick = (LayeredDataClassBrick)brick;
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
