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

  public void calcInLayers(String name, LayeredDataClassBrick thisAsBrick) {
    for(CompoundDataClass layer : layers) {
      layer.calcInternal(name, thisAsBrick);
    }
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
