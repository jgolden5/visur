package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import static com.ple.visur.DataModelKey.*;

public class DataModelService {

  final LocalMap<DataModelKey, Object> dataModel;

  private DataModelService(LocalMap<DataModelKey, Object> dataModel) {
    this.dataModel = dataModel;
  }

  public static DataModelService make(LocalMap<EditorModelKey, Object> dataModel) {
    if(dataModel.isEmpty()) {
      dataModel = setInitialValues(dataModel);
    }
    return new DataModelService(dataModel);
  }

  private static LocalMap<DataModelKey, Object> setInitialValues(LocalMap<DataModelKey, Object> dataModel) {
    dataModel.put(dataContentLines, "test content");
  }


  public String[] getDataContentLines() {
    return (String[])dataModel.get(dataContentLines);
  }

  public void putDataContentLines(String[] lines) {
    dataModel.put(dataContentLines, lines);
  }

  private DataModelService(SharedData sharedData) {
    this.sharedData = sharedData;
  }

}
