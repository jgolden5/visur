package DataClass;

import java.util.ArrayList;

public interface DataClass {
  DataClassBrick makeBrick(String name, ArrayList<OuterDataClassBrick> outers);
}
