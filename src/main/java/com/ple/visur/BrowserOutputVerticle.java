package com.ple.visur;

import io.reactivex.rxjava3.core.Completable;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

import static com.ple.visur.ModelIntKey.*;

public class BrowserOutputVerticle extends AbstractVisurVerticle {

  View view;

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.modelChange.name(), event -> {
      handleChange(event);
    });
  }

  public void handleChange(Message<Object> event) {
    System.out.println("model change message received by output verticle");
    if(view == null) {
      view = new View();
      view.cursorX = 0;
      view.cursorY = 0;
      view.content = "hello world";
    } else {
      view.cursorX = modelInt.get(cursorX.name());
      view.cursorY = modelInt.get(cursorY.name());
      view.content = modelString.get("content");
    }
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson()); //message will equal changed view based on json
  }


  public JsonObject toJson() {
    JsonObject output = new JsonObject();
    output.put(cursorX.name(), view.cursorX);
    output.put(cursorY.name(), view.cursorY);
    output.put("content", view.content);
    return output;
  }

}
