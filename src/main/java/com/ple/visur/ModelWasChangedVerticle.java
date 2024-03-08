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
    EditorModelService ems = ServiceHolder.editorModelService;
    if(view == null) {
      view = new View();
      view.ca = 0;
      view.contentY = 0;
    } else {
      view.ca = ems.getGlobalVar("ca").getInt();
      view.contentY = ems.getGlobalVar("contentY").getInt();
    }
    view.editorContent = ServiceHolder.editorModelService.getEditorContent();
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }


  public JsonObject toJson() {
    EditorModelService ems = ServiceHolder.editorModelService;
    JsonObject output = new JsonObject();
    output.put("ca", ems.getGlobalVar("ca").getInt());
    output.put("contentY", ems.getGlobalVar("contentY").getInt());
    output.put("editorContent", ems.getEditorContent());
    output.put("quantumStart", ems.getQuantumStart());
    output.put("quantumEnd", ems.getQuantumEnd());
    output.put("editorMode", ems.getEditorMode());
    output.put("currentQuantum", ems.getCurrentQuantum().getName());
    output.put("isInCommandState", ems.getIsInCommandState());
    output.put("commandStateContent", ems.getCommandStateContent());
    output.put("commandCursor", ems.getCommandCursor());
    return output;
  }

}
