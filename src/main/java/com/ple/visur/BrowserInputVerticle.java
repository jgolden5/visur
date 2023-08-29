package com.ple.visur;

import io.vertx.core.Future;
import io.vertx.core.impl.future.SucceededFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserInputVerticle implements BrowserInputService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BrowserInputVerticle.class);
  @Override
  public Future<Void> keyPress(String key) {
    LOGGER.debug("Key pressed");
    System.out.println(key + " key pressed");
    return Future.succeededFuture();
  }
}
