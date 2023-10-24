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
    editorModelService.putInterlinearY(0);
    final String initialContentLines = "xxxxx" + //5x
      "\nyyyyyyyy" + //8y
      "\nzzzz"; //4z
//    final String initialContentLines = "While it seems inconceivable that the divine could be shackled, Ashenzari is just that: bound to the sky for eternity, the unbudging god is all-knowing, all-seeing." +
//      "\nDevoted worshippers are allowed to grasp shreds of this knowledge and foresight, but be warned: to Ashenzari, power and blessing and curse are all the same thing." +
//      "\nAshenzari exhorts followers to curse their possessions, periodically offering these curses to worshipers as they explore." +
//      "\nCursed equipment cannot be enchanted and only be removed by shattering it forever. However, these curses enhance specific skills." +
//      "\nThey will also please Ashenzari, who will reveal the invisible and grant clarity of mind." +
//      "\nThe truly devout will gain a fragment of Ashenzari's astral sight, letting them see through walls.";
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
