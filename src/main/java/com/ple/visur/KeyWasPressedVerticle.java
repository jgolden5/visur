package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

public class KeyWasPressedVerticle extends AbstractVisurVerticle {

  @Override
  public void start() {
    InitializerService.make(ServiceHolder.editorModelService);
    vertx.eventBus().consumer(BusEvent.keyWasPressed.name(), this::handle);
  }

  public void handle(Message event) {
    EditorModelService ems = ServiceHolder.editorModelService;
    JsonObject keyJson = new JsonObject((String) event.body());
    final String key = keyJson.getString("key");
    KeyPressed keyPressed = KeyPressed.from(key);
    ems.putKeyPressed(keyPressed);

    boolean modelChanged = ems.handleKeyPress(keyPressed);

    if(modelChanged) {
      bus.send(BusEvent.modelChange.name(), null);
    }

  }

  public Future<Void> keyPress(String key) {
    return Future.succeededFuture();
  }

}

