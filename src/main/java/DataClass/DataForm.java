package DataClass;

import java.util.Optional;

public abstract class DataForm {
    private DataForm[] convertibleForms;
    public DataForm(int numberOfConvertibleForms) {
        this.convertibleForms = new DataForm[numberOfConvertibleForms];
    }
    public abstract Optional<DataFormBrick> makeBrick(Object val);
    protected boolean canConvertTo(DataForm toDF) {
        for(int i = 0; i < convertibleForms.length; i++) {
            if(convertibleForms[i].equals(toDF)) {
                return true;
            }
        }
        for(int i = 0; i < convertibleForms.length; i++) {
            return convertibleForms[i].canConvertTo(toDF);
        }
        return false;
    }
  public abstract Object getValAs(DCHolder dcHolder);
  public abstract Object convertTo(DataForm toDF, Object fromDFVal, DCHolder dcHolder);
    public abstract Object convertFrom(DataForm fromDF, Object toDFVal);
  private boolean convertibleFormsContainDF(DataForm df) {
    for(int i = 0; i < convertibleForms.length; i++) {
      if(convertibleForms[i] != null && convertibleForms[i].equals(df)) {
        return true;
      }
    }
    return false;
  }

  public DataForm[] getConvertibleForms() {
        return convertibleForms;
    }
    public void putConvertibleForm(DataForm convertibleDF) {
      boolean convertibleDFSetInternally = false;
      for(int i = 0; i < convertibleForms.length; i++) {
        if (convertibleForms[i] == null) {
          convertibleForms[i] = convertibleDF;
        }
        if(convertibleDFSetInternally) {
          break;
        }
      }
      if(!convertibleDFSetInternally) {
        System.out.println("Error: convertible form already set");
      }
    }

}
