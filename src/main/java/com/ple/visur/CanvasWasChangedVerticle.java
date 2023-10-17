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
    editorModelService.putCanvasWidth(width);
    final Integer height = canvasJson.getInteger("height");
    editorModelService.putCanvasHeight(height);
    int lineNumber = 1;
    for(String line : dataModelService.getContentLines()) {
      System.out.println("Line " + lineNumber + ": " + line);
      lineNumber++;
    }
  }

}
