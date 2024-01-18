package com.ple.visur;

import io.vertx.core.json.*;
import io.vertx.rxjava3.core.eventbus.Message;

import java.util.Arrays;

public class ModelWasChangedVerticle extends AbstractVisurVerticle {

  View view;

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.modelWasChanged.name(), event -> {
      handleChange(event);
    });
  }

  public void handleChange(Message<Object> event) {
    System.out.println("Model Changed event was received");
    if(view == null) {
      view = new View();
      view.contentLineX = 0;
      view.contentLineY = 0;
    } else {
      view.contentLineX = ServiceHolder.editorModelService.getContentX();
      view.contentLineY = ServiceHolder.editorModelService.getContentY();
    }
    view.contentLines = ServiceHolder.editorModelService.getEditorContentLines();
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }


  public JsonObject toJson() {
    EditorModelService ems = ServiceHolder.editorModelService;
    JsonObject output = new JsonObject();
    output.put("canvasX", ems.getCanvasX());
    output.put("canvasY", ems.getCanvasY());
    output.put("contentLines", new JsonArray(Arrays.asList(view.contentLines)));
    output.put("editorMode", ems.getEditorMode());
    output.put("isInCommandState", ems.getIsInCommandState());
    return output;
  }

}
