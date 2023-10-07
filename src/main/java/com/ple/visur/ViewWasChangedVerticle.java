package com.ple.visur;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

import static com.ple.visur.ModelIntKey.*;
import static com.ple.visur.ModelStringKey.*;

public class ViewWasChangedVerticle extends AbstractVisurVerticle {

  View view;

  @Override
  public void start() {
    editorModelService.setCursorX(0);
    editorModelService.setCursorY(0);
    modelString.put(content.name(), "good morning world" +
      "\ngood afternoon world" +
      "\ngood night world");
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
    } else {
      view.cursorX = editorModelService.getCursorX();
      view.cursorY = editorModelService.getCursorY();
    }
    view.content = modelString.get(content.name());
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }


  public JsonObject toJson() {
    JsonObject output = new JsonObject();
    output.put(cursorX.name(), view.cursorX);
    output.put(cursorY.name(), view.cursorY);
    output.put(content.name(), view.content);
    return output;
  }

}
