package com.ple.visur;

import io.vertx.core.json.*;
import io.vertx.rxjava3.core.eventbus.Message;

public class ModelWasChangedVerticle extends AbstractVisurVerticle {

  View view;

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.modelWasChanged.name(), event -> {
      handleChange(event);
    });
  }

  public void handleChange(Message<Object> event) {
    EditorModelCoupler ems = ServiceHolder.editorModelCoupler;
    if(view == null) {
      view = new View();
      view.cx = 0;
      view.cy = 0;
    } else {
      view.cx = ems.getCX();
      view.cy = ems.getCY();
    }
    view.editorContent = ServiceHolder.editorModelCoupler.getEditorContent();
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }


  public JsonObject toJson() {
    EditorModelCoupler ems = ServiceHolder.editorModelCoupler;
    JsonObject output = new JsonObject();
    output.put("cx", ems.getCX());
    output.put("cy", ems.getCY());
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
