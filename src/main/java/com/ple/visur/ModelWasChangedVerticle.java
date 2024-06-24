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
    vertx.eventBus().send(BusEvent.viewWasChanged.name(), toJson());
  }

  public JsonObject toJson() {
    JsonObject output = new JsonObject();
    BrickVisurVar bvv = (BrickVisurVar)emc.getGlobalVar("ca");
    EditorSubmode editorSubmode = emc.getEditorSubmode();
    output.put("ca", bvv.getVal());
    output.put("editorContent", emc.getEditorContent());
    output.put("span", emc.getSpan());
    output.put("cursorQuantumStart", emc.getCursorQuantumStart());
    output.put("cursorQuantumEnd", emc.getCursorQuantumEnd());
    output.put("editorMode", emc.getEditorMode());
    output.put("editorSubmode", editorSubmode);
    output.put("cursorQuantum", emc.getCursorQuantum().getName());
    output.put("scopeQuantum", emc.getScopeQuantum().getName());
    output.put("isAtQuantumStart", emc.getIsAtQuantumStart());
    output.put("isInCommandState", emc.getIsInCommandState());
    output.put("commandStateContent", emc.getCommandStateContent());
    output.put("commandCursor", emc.getCommandCursor());
    if(editorSubmode.name().equals("search")) {
      ExecutionDataStack eds = emc.getExecutionDataStack();
      String searchTarget = "";
      if(eds.size() > 0) {
        searchTarget = (String) eds.peek();
      }
      searchTarget += "_";
      output.put("searchTarget", searchTarget);
    }
    return output;
  }

}
