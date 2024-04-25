package CursorPositionDC;

import DataClass.DCHolder;
import DataClass.DataForm;
import DataClass.DataFormBrick;

import java.util.ArrayList;
import java.util.Optional;

public class IntArrayListDF extends DataForm {

  public IntArrayListDF(int numberOfConvertibleForms) {
    super(numberOfConvertibleForms);
  }

  @Override
  public Optional<DataFormBrick> makeBrick(Object val) {
    Optional<DataFormBrick> res = Optional.empty();
    if(val instanceof ArrayList) {
      ArrayList valAsArrayList = (ArrayList) val;
      if(valAsArrayList.size() > 0) {
        if(valAsArrayList.get(0) instanceof Integer) {
          res = Optional.of(DataFormBrick.make(this, val));
        }
      } else {
        res = Optional.of(DataFormBrick.make(this, val));
      }
    }
    return res;
  }

  @Override
  public Object getValAs(DCHolder dcHolder) {
    return null;
  }

  @Override
  public Object convertTo(DataForm toDF, Object fromDFVal, DCHolder dcHolder) {
    return null;
  }

  @Override
  public Object convertFrom(DataForm fromDF, Object toDFVal) {
    return null;
  }
}
