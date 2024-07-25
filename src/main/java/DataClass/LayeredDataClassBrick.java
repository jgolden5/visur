package DataClass;

import java.util.ArrayList;

public class LayeredDataClassBrick extends DataClassBrick {

  LayeredDataClassBrick(DataClass dc, String name) {
    super(dc, null, name);
  }

  ArrayList<CompoundDataClassBrick> layers = new ArrayList<>();

  @Override
  public boolean isComplete() {
    for(CompoundDataClassBrick layer : layers) {
      if(!layer.isComplete()) {
        return false;
      }
    }
    return true;
  }

}
