package com.ple.visur;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

import static com.ple.visur.ModelStringKey.*;

public class CanvasWasChangedVerticle extends AbstractVisurVerticle {
  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.canvasWasChanged.name(), this::handle);
  }

  public void handle(Message event) {
    JsonObject canvasJson = new JsonObject((String)event.body());
    final Integer width = canvasJson.getInteger("width");
    modelInt.put("canvasWidth", width);
    final Integer height = canvasJson.getInteger("height");
    modelInt.put("canvasHeight", height);
    editorModelService.setContentLines(modelString.get(content.name()).split("\n"));
    int lineNumber = 1;
    for(String line : editorModelService.getContentLines()) {
      System.out.println("Line " + lineNumber + ": " + line);
      lineNumber++;
    }
    System.out.println("canvas width = " + width);
    System.out.println("canvas height = " + height);
  }

}
