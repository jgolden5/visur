package com.ple.visur;

import io.vertx.core.json.*;
import io.vertx.rxjava3.core.eventbus.Message;

import java.util.Arrays;

import static com.ple.visur.ModelIntKey.*;
import static com.ple.visur.ModelStringArrayKey.*;


public class ModelChangeVerticle extends AbstractVisurVerticle {

  View view;

  @Override
  public void start() {
    editorModelService.putCursorX(0);
    editorModelService.putCursorY(0);
    editorModelService.putInterlinearX(0);
    final String initialContentLines = "xxxx" + //4x
      "\nyyyyy" + //5y
      "\nz" + //z
      "\naaaa"; //4a
    dataModelService.putContentLines(initialContentLines.split("\n"));
    vertx.eventBus().consumer(BusEvent.modelChange.name(), event -> {
      handleChange(event);
    });
  }

  public void handleChange(Message<Object> event) {
    if(view == null) {
      view = new View();
      view.cursorX = 0;
      view.cursorY = 0;
    } else {
      view.cursorX = editorModelService.getCursorX();
      view.cursorY = editorModelService.getCursorY();
    }
    view.contentLines = dataModelService.getContentLines();
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }


  public JsonObject toJson() {
    JsonObject output = new JsonObject();
    output.put(cursorX.name(), view.cursorX);
    output.put(cursorY.name(), view.cursorY);
    output.put(contentLines.name(), new JsonArray(Arrays.asList(view.contentLines)));
    return output;
  }

}
