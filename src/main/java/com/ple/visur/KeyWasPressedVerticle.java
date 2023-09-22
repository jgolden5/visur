package com.ple.visur;

import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyWasPressedVerticle extends AbstractVisurVerticle implements BrowserInputService {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeyWasPressedVerticle.class);


  //step 3: browserInputVerticle listens for keyWasPressed event (also needs work)
  @Override
  public Future<Void> keyPress(String key) {
    mapKeys(key);
    boolean modelChanged = true;
    //step 5: if the model was changed, browserInputVerticle sends modelChange event to event bus
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
      //step 4: when browserInputVerticle hears that keyWasPressed occurred, it changes the model
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
    LOGGER.debug("Key pressed");
    System.out.println(key + " key pressed");
  }
}
