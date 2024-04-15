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
    public abstract Object convertTo(DataForm toDF, Object fromDFVal, DCHolder dcHolder);
    public abstract Object convertFrom(DataForm fromDF, Object toDFVal);
    public DataForm[] getConvertibleForms() {
        return convertibleForms;
    }
    public void putConvertibleForm(DataForm cdf) {
        for(int i = 0; i < convertibleForms.length; i++) {
            if (!convertibleFormsContainDF(cdf)) {
                if (convertibleForms[i] == null) {
                    convertibleForms[i] = cdf;
                }
            } else {
                System.out.println("Error: convertible form already set");
            }
        }
    }
    private boolean convertibleFormsContainDF(DataForm df) {
        for(int i = 0; i < convertibleForms.length; i++) {
            if(convertibleForms[i] != null && convertibleForms[i].equals(df)) {
                return true;
            }
        }
        return false;
    }
}
