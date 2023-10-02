package com.ple.visur;

import io.vertx.core.Future;
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
    modelInt.put("canvasWidth", width);
    final Integer height = canvasJson.getInteger("height");
    modelInt.put("canvasHeight", height);
    System.out.println("canvas width = " + width);
    System.out.println("canvas height = " + height);
  }

}
