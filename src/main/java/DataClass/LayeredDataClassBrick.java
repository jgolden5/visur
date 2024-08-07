package DataClass;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

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
  public Result<PrimitiveDataClassBrick> get(String targetName) {
    return null;
  }

  @Override
  public Result<Object> calc(Stack<DataClassBrick> innerToOuterBricks) {
    Result r = Result.make();
    int i = 0;
    while(r.getVal() == null && i < layers.size()) {
      r = layers.get(i).getCDC().calcInternal(name, this);
      i++;
    }
    return r;
  }

  @Override
  public Result remove() {
    for(CompoundDataClassBrick layer : layers) {
      layer.remove();
    }
    return Result.make();
  }

  @Override
  public void removeConflicts(String targetName, Object targetVal) {
    //all layers in ldcb always get set, therefore this method is not needed
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
