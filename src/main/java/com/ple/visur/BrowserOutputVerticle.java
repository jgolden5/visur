package com.ple.visur;

import io.reactivex.rxjava3.core.Completable;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

public class BrowserOutputVerticle extends AbstractVisurVerticle {

  View view;

  public void handleChange(Message<Object> event) {
    System.out.println("model change message received by output verticle");
    //step 7: if modelChange event occurred, the view is drawn/redrawn according to changes
    if(view == null) {
      view = new View();
      view.cursorX = 0;
      view.cursorY = 0;
      view.content = "hello world";
    } else {
      view.cursorX = modelInt.get("cursorX");
      view.cursorY = modelInt.get("cursorY");
      view.content = modelString.get("content");
    }
    //step 8: if view was changed, send ViewWasChanged event to event bus
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson()); //message will equal changed view based on json
  }

  @Override
  public void start() {
    //step 6: BrowserOutputVerticle listens for modelChange event
    vertx.eventBus().consumer(BusEvent.modelChange.name(), event -> {
      handleChange(event);
    });
  }


  public JsonObject toJson() {
    JsonObject output = new JsonObject();
    output.put("cursorX", view.cursorX);
    output.put("cursorY", view.cursorY);
    output.put("content", view.content);
    return output;
  }

}
