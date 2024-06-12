package com.ple.visur;

import io.vertx.core.json.*;
import io.vertx.rxjava3.core.eventbus.Message;

import java.util.Stack;

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
    updateSpan();
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }

  private void updateSpan() {
    if(emc.getCursorQuantumStart() == emc.getCursorQuantumEnd()) {
      emc.putSpan(0);
    }
  }

  public JsonObject toJson() {
    JsonObject output = new JsonObject();
    BrickVisurVar bvv = (BrickVisurVar)emc.getGlobalVar("ca");
    output.put("ca", bvv.getVal());
    output.put("editorContent", emc.getEditorContent());
    output.put("span", emc.getSpan());
    output.put("cursorQuantumStart", emc.getCursorQuantumStart());
    output.put("cursorQuantumEnd", emc.getCursorQuantumEnd());
    output.put("editorMode", emc.getEditorMode());
    output.put("editorSubmode", emc.getEditorSubmode());
    output.put("cursorQuantum", emc.getCursorQuantum().getName());
    output.put("isAtQuantumStart", emc.getIsAtQuantumStart());
    output.put("isInCommandState", emc.getIsInCommandState());
    output.put("commandStateContent", emc.getCommandStateContent());
    output.put("commandCursor", emc.getCommandCursor());
    return output;
  }

}
