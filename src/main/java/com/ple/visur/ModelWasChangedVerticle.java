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
      view.ca = 0;
    } else {
      view.ca = ems.getCA();
    }
    view.editorContent = ServiceHolder.editorModelCoupler.getEditorContent();
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }


  public JsonObject toJson() {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    JsonObject output = new JsonObject();
    output.put("ca", emc.getCA());
    output.put("editorContent", emc.getEditorContent());
    output.put("quantumStart", emc.getQuantumStart());
    output.put("quantumEnd", emc.getQuantumEnd());
    output.put("editorMode", emc.getEditorMode());
    output.put("currentQuantum", emc.getCurrentQuantum().getName());
    output.put("isInCommandState", emc.getIsInCommandState());
    output.put("commandStateContent", emc.getCommandStateContent());
    output.put("commandCursor", emc.getCommandCursor());
    return output;
  }

}
