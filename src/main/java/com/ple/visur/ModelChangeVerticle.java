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
    final String initialContentLines = "Trog is an ancient god of anger and violence." +
      "\nIn return, worshippers of Trog gain the ability to go berserk at will in combat, and will be granted assistance in the form of powerful weapons and mighty allies." +
      "\nFollowers are absolutely forbidden the use of spell magic." +
      "\nTrog offers various powers to help followers in battle." +
      "\nFollowers will gain the ability to go berserk at will, and to rapidly heal their wounds while fortifying their willpower." +
      "\nLater, followers may call in powerful raging allies." +
      "\nEventually, Trog will gift followers with weapons, often with antimagic effects.";
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
