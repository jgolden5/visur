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
    final String initialContentLines = "xxxxx" + //5x
      "\nyyyyyyyy" + //8y
      "\nzzzz"; //4z
//    final String initialContentLines = "Okawaru is a dangerous and powerful god of battle." +
//      "\nFollowers are expected to constantly prove themselves in combat, and may channel Okawaru's might to enhance their prowess." +
//      "\nOkawaru demands that followers prove themselves by their own strength alone, and so worshippers are forbidden from gaining allies by any means." +
//      "\nOkawaru pays little heed to easy victories, but will reward worshippers for heroic feats against mighty foes." +
//      "\n\"Bring me glory in combat!\"";
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
