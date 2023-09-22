package com.ple.visur;

import io.vertx.core.Future;

public class CanvasWasChangedVerticle extends AbstractVisurVerticle implements BrowserInputService {
  public Future<Void> keyPress(String key) {
    mapKeys(key);
    boolean modelChanged = true;
    if(modelChanged) {
      bus.send(BusEvent.modelChange.name(), null);
    }
    return Future.succeededFuture();
  }
  public void mapKeys(String key) {
    if(key.equals("h")) {
      int x = modelInt.get("cursorX");
      x--;
      modelInt.put("cursorX", x);
    } else if(key.equals("j")) {
      int y = modelInt.get("cursorY");
      System.out.println("y before y increment = " + modelInt.get("cursorY"));
      y++;
      modelInt.put("cursorY", y);
      System.out.println("y after y increment = " + modelInt.get("cursorY"));
    } else if(key.equals("k")) {
      int y = modelInt.get("cursorY");
      y--;
      modelInt.put("cursorY", y);
    } else if(key.equals("l")) {
      int x = modelInt.get("cursorX");
      x++;
      modelInt.put("cursorX", x);
    }
    System.out.println(key + " key pressed");
  }
}
