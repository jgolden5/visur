package DataClass;

import java.util.ArrayList;
import java.util.Map;

public class LayeredDataClassBrick extends OuterDataClassBrick {

  private LayeredDataClass ldc;
  ArrayList<CompoundDataClassBrick> layers;

  public static LayeredDataClassBrick make(String name, DataClass ldc, ArrayList<CompoundDataClassBrick> layers) {
    return new LayeredDataClassBrick(name, ldc, layers);
  }

  LayeredDataClassBrick(String name, DataClass ldc, ArrayList<CompoundDataClassBrick> layers) {
    super(ldc, null, name);
    this.ldc = (LayeredDataClass) ldc;
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

  @Override
  public Result<DataClassBrick> calc(String innerName) {
    Result r = getLDC().calcInternal(innerName, this);
    if(r == null && getOuters() != null) {
      OuterDataClassBrick outerBrick = getOuterContainingTargetName(innerName).getVal();
      return outerBrick.calc(innerName);
    } else {
      return r;
    }
  }

  @Override
  public void removeConflicts(String targetName, Object targetVal) {
    //all layers in ldcb always get set, therefore this method is not needed
  }


  @Override
  public boolean conflictsCheck(String name, Object val) {
    return getLDC().conflictsCheck(this);
  }

  @Override
  public void conflictsCheckAndRemove(String name, Object val) {

  }

  @Override
  public boolean containsName(String targetName) {
    boolean containsName = false;
    if(getName().equals(targetName)) {
      containsName = true;
    } else {
      for(CompoundDataClassBrick layer : layers) {
        if (layer.containsName(targetName)) {
          containsName = true;
          break;
        }
      }
    }
    return containsName;
  }

}
