package com.ple.visur;

import io.vertx.core.json.*;
import io.vertx.rxjava3.core.eventbus.Message;

public class ModelWasChangedVerticle extends AbstractVisurVerticle {

  View view;
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.modelWasChanged.name(), event -> {
      handleChange(event);
    });
  }

  public void handleChange(Message<Object> event) {
    if(view == null) {
      view = new View();
      view.ca = 0;
    } else {
      BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
      view.ca = (int)caBVV.getVal();
    }
    view.editorContent = emc.getEditorContent();
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }


  public JsonObject toJson() {
    JsonObject output = new JsonObject();
    BrickVisurVar bvv = (BrickVisurVar)emc.getGlobalVar("ca");
    output.put("ca", bvv.getVal());
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
