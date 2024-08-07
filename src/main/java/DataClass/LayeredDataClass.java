package DataClass;

import java.util.ArrayList;
import java.util.Stack;

public abstract class LayeredDataClass extends OuterDataClass {

  ArrayList<CompoundDataClass> layers = new ArrayList<>();

  public void putLayer(CompoundDataClass cdc) {
    layers.add(cdc);
  }

  public CompoundDataClass getLayer(int i) {
    return layers.get(i);
  }


  @Override
  public Result<Object> calcInternal(Stack<String> innerToOuterBrickNames, OuterDataClassBrick thisAsBrick) {
    Result<Object> r = Result.make(null, "no layers exist");
    for(CompoundDataClass layer : layers) {
      r = layer.calcInternal(innerToOuterBrickNames, thisAsBrick);
    }
    return r;
  }

}
