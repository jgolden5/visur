package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserInputVerticle extends AbstractVisurVerticle implements BrowserInputService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BrowserInputVerticle.class);


  @Override
  public Future<Void> keyPress(String key) {
    if(key.equals("j")) {
      int y = modelInt.get("cursorY");
      System.out.println("y before y increment = " + modelInt.get("cursorY"));
      y++;
      modelInt.put("cursorY", y);
      System.out.println("y after y increment = " + modelInt.get("cursorY"));

    }
    LOGGER.debug("Key pressed");
    System.out.println(key + " key pressed");
    bus.send(BusEvent.modelChange.name(), null);
    return Future.succeededFuture();
  }


}
