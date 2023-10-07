package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava3.core.eventbus.Message;

import static com.ple.visur.ModelIntKey.*;
import static com.ple.visur.ModelStringKey.*;

public class KeyWasPressedVerticle extends AbstractVisurVerticle {

  @Override
  public void start() {
    vertx.eventBus().consumer(BusEvent.keyWasPressed.name(), this::handle);
  }

  public void handle(Message event) {
    System.out.println("x, y to absolute index = " + xyToAbsoluteIndex(editorModelService.getCursorX(), editorModelService.getCursorY()));
    JsonObject keyJson = new JsonObject((String)event.body());
    final String key = keyJson.getString("key");
    mapKeys(key);
    boolean modelChanged = true;
    if(modelChanged) {
      bus.send(BusEvent.modelChange.name(), null);
    }
  }

  public Future<Void> keyPress(String key) {
    return Future.succeededFuture();
  }

  public void mapKeys(String key) {
//    int x = modelInt.get(cursorX.name());
    int x = editorModelService.getCursorX();
    int y = editorModelService.getCursorY();
    if (key.equals("h")) {
      if(x > 0) {
        x--;
      }
//      modelInt.put(cursorX.name(), x);
      editorModelService.setCursorX(x);
    } else if (key.equals("j")) {
      System.out.println("y before y increment = " + editorModelService.getCursorY());
      final Integer height = modelInt.get(canvasHeight.name());
      if(y < height - 1) {
        y++;
        System.out.println("y after y increment = " + editorModelService.getCursorY());
//        modelInt.put(cursorY.name(), y);
        editorModelService.setCursorY(y);
      }
    } else if (key.equals("k")) {
      if(y > 0) {
        y--;
      }
//      modelInt.put(cursorY.name(), y);
      editorModelService.setCursorY(y);
    } else if (key.equals("l")) {
      final Integer width = modelInt.get(canvasWidth.name());
      System.out.println("x = " + x);
      if(x < width - 1) {
        x++;
      }
//      modelInt.put(cursorX.name(), x);
      editorModelService.setCursorX(x);
    }
    System.out.println(key + " key pressed");
  }

  public int xyToAbsoluteIndex(int x, int y) {
    int contentLength = modelString.get(content.name()).length();
    int numberOfChars = 0;
    int lineStartIndex = 0;
    String substr = "";
    int previousLineLength = 0;
    for(int i = 0; i < contentLength; i++) {
      if(modelString.get(content.name()).charAt(i) == '\n') {
        substr = modelString.get(content.name()).substring(lineStartIndex, i);
        previousLineLength = substr.length();
        lineStartIndex = i;
      } else if(i == previousLineLength + x + y) {
        return i;
      }
    }
   return -1;
  }

}
