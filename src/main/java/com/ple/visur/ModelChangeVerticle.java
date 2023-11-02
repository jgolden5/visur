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
    final String initialContentLines = "Hello world?" +
      "\nGoodbye world." +
      "\nWho's there?";
//    final String initialContentLines = "Jiyva is the ancient deity of the slimes." +
//      "\nFollowers are expected to support the spread and satiation of their fellow slimes by allowing them to consume items, and are forbidden from harming any slimes." +
//      "\nEspecially favoured followers will become as shapeless as their god, mutating wildly." +
//      "\nJiyva will gradually fill the dungeon with jellies, which will rapidly consume nearby items as well as slowly consuming items from elsewhere in the dungeon." +
//      "\nAs they spread slime across more of the dungeon, Jiyva will grant followers increasingly rapid health and magic regeneration, and will begin mutating them to better reflect Jiyva's image." +
//      "\nFollowers will later become able to call forth acidic ooze from the walls of the dungeon, and to turn monsters to slime with a touch.";
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
