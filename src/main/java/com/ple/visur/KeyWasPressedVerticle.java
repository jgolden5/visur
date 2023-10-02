package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

import static com.ple.visur.ModelIntKey.*;

public class KeyWasPressedVerticle extends AbstractVisurVerticle {

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.keyWasPressed.name(), this::handle);
  }

  public void handle(Message event) {
    JsonObject keyJson = new JsonObject((String)event.body());
    final String key = keyJson.getString("key");
    mapKeys(key);
    boolean modelChanged = true;
    if(modelChanged) {
      bus.send(BusEvent.modelChange.name(), null);
    }
  }

  public Future<Void> keyPress(String key) {
    return Future.succeededFuture();
  }

  public void mapKeys(String key) {
    if (key.equals("h")) {
      int x = modelInt.get(cursorX.name());
      if(x > 0) {
        x--;
      }
      modelInt.put(cursorX.name(), x);
    } else if (key.equals("j")) {
      int y = modelInt.get(cursorY.name());
      System.out.println("y before y increment = " + modelInt.get(cursorY.name()));
      final Integer height = modelInt.get(canvasHeight.name());
      if(y < height - 1) {
        y++;
        System.out.println("y after y increment = " + modelInt.get(cursorY.name()));
      }
      modelInt.put(cursorY.name(), y);
    } else if (key.equals("k")) {
      int y = modelInt.get(cursorY.name());
      if(y > 0) {
        y--;
      }
      modelInt.put(cursorY.name(), y);
    } else if (key.equals("l")) {
      int x = modelInt.get(cursorX.name());
      final Integer width = modelInt.get(canvasWidth.name());
      if(x < width - 1) {
        x++;
      }
      modelInt.put(cursorX.name(), x);
    }
    System.out.println(key + " key pressed");
  }

}
