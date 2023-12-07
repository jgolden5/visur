package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;
import io.vertx.rxjava3.core.shareddata.LocalMap;

public class KeyWasPressedVerticle extends AbstractVisurVerticle {

  @Override
  public void start() {
    InitializerService.make(ServiceHolder.editorModelService);
    vertx.eventBus().consumer(BusEvent.keyWasPressed.name(), this::handle);
  }

  public void handle(Message event) {
    JsonObject keyJson = new JsonObject((String) event.body());
    final String key = keyJson.getString("key");
    final EditorMode editorMode = ServiceHolder.editorModelService.getEditorMode();
    final KeyPressed keyPressed = KeyPressed.from(key);
    ServiceHolder.editorModelService.putKeyPressed(KeyPressed.from(keyPressed.getKey()));

    boolean modelChanged = true;
    if(modelChanged) {
      bus.send(BusEvent.modelChange.name(), null);
    }

  }

  public Future<Void> keyPress(String key) {
    return Future.succeededFuture();
  }

}

