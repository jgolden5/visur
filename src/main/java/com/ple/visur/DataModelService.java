package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.SharedData;

import static com.ple.visur.ModelIntKey.*;
import static com.ple.visur.ModelStringArrayKey.contentLines;

public class DataModelService {

  private final SharedData sharedData;

  public String[] getContentLines() {
    return (String[])sharedData.getLocalMap("modelStringArray").get(contentLines.name());
  }

  public void putContentLines(String[] lines) {
    sharedData.getLocalMap("modelStringArray").put(contentLines.name(), lines);
  }

  DataModelService(SharedData sharedData) {
    this.sharedData = sharedData;
  }

}
