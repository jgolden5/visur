package com.ple.visur;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

public class CanvasWasChangedVerticle extends AbstractVisurVerticle {

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.canvasWasChanged.name(), this::handle);
  }

  public void handle(Message event) {
    JsonObject canvasJson = new JsonObject((String)event.body());
    final Integer width = canvasJson.getInteger("width");
    final EditorModelService ems = ServiceHolder.editorModelService;
    ems.putCanvasWidth(width);
    final Integer height = canvasJson.getInteger("height");
    ems.putCanvasHeight(height);
    int[] newlineIndices = ems.getNewlineIndices();
    for(int i = 0; i <= newlineIndices.length; i++) {
      String line = ems.getContentLineAtIndex(i);
      System.out.println("Line " + i + ": " + line);
    }
    vertx.eventBus().send(BusEvent.modelWasChanged.name(), "true");
  }

}
