package DataClass;

import java.util.Objects;

public class DataFormBrick {

    private DataForm df;
    private Object val;

    public static DataFormBrick make(DataForm df, Object val) {
        return new DataFormBrick(df, val);
    }

    public DataFormBrick(DataForm df, Object val) {
        this.df = df;
        this.val = val;
    }

    public DataFormBrick convertTo(DataForm toDF, DCHolder dcHolder) {
        Object newVal = df.convertTo(toDF, val, dcHolder);
        return make(toDF, newVal);
    }
    DataFormBrick convertFrom(DataForm fromDF) {
        Object newVal = df.convertFrom(fromDF, val);
        return make(fromDF, newVal);
    }

    public Object getVal() {
        return val;
    }

    public DataForm getDF() {
        return df;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataFormBrick that)) return false;
        return Objects.equals(df, that.df) && Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(df, val);
    }
}
