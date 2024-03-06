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
    if(view == null) {
      view = new View();
      view.contentX = 0;
      view.contentY = 0;
    } else {
      EditorModelService ems = ServiceHolder.editorModelService;
      view.contentX = ems.getGlobalVar("contentX").getInt();
      view.contentY = ems.getGlobalVar("contentY").getInt();
    }
    view.editorContent = ServiceHolder.editorModelService.getEditorContent();
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }


  public JsonObject toJson() {
    EditorModelService ems = ServiceHolder.editorModelService;
    JsonObject output = new JsonObject();
    output.put("contentX", ems.getGlobalVar("contentX").getInt());
    output.put("contentY", ems.getGlobalVar("contentY").getInt());
    output.put("editorContent", ems.getEditorContent());
    output.put("editorMode", ems.getEditorMode());
    output.put("isInCommandState", ems.getIsInCommandState());
    output.put("commandStateContent", ems.getCommandStateContent());
    output.put("commandCursor", ems.getCommandCursor());
    return output;
  }

}
