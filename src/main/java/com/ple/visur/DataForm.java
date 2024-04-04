package com.ple.visur;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DataForm {
  String name;
  DataClass dc;
  HashMap<DataForm, Converter> converters;

  public static DataForm make(String name, DataClass dc) {
    return new DataForm(name, dc, new HashMap<>());
  }

  private DataForm(String name, DataClass dc, HashMap<DataForm, Converter> converters) {
    this.name = name;
    this.dc = dc;
    this.converters = converters;
  }

  public Object convert(DataForm toDF, Object dfbVal) {
    if(toDF.equals(this)) return dfbVal;
    Object newVal = null;
    if(getConverter(toDF) != null) {
      Converter converter = getConverter(toDF);
      newVal = converter.convert(dfbVal);
    } else if(canConvertTo(toDF, new ArrayList<>())) {
      for(var entry : converters.entrySet()) {
        Converter currentConverter = entry.getValue();
        newVal = currentConverter.convert(dfbVal);
        DataForm currentDataForm = entry.getKey();
        newVal = currentDataForm.convert(toDF, newVal);
        if(newVal != null) break;
      }
    }
    return newVal;
  }

  public void putConverter(DataForm toDF, Converter converter) {
    converters.put(toDF, converter);
  }

  public Converter getConverter(DataForm toDF) {
    return converters.get(toDF);
  }

  public boolean canConvertTo(DataForm toDF, ArrayList<DataForm> targetsNotToSearchAgain) {
    boolean canConvert;
    targetsNotToSearchAgain.add(this);
    if(converters.get(toDF) != null) {
      boolean currentTargetWasAlreadySearched = targetsNotToSearchAgain.contains(toDF);
      if(!currentTargetWasAlreadySearched) {
        return true;
      }
    }
    for (var entry : converters.entrySet()) {
      DataForm currentDataForm = entry.getKey();
      boolean currentTargetWasAlreadySearched = targetsNotToSearchAgain.contains(currentDataForm);
      if (!currentTargetWasAlreadySearched) {
        canConvert = currentDataForm.canConvertTo(toDF, targetsNotToSearchAgain);
        if (canConvert) return true;
      }
    }
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DataForm dataForm)) return false;
    return Objects.equals(name, dataForm.name) && Objects.equals(dc, dataForm.dc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dc);
  }
}
