package com.ple.visur;

import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserInputVerticle implements BrowserInputService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BrowserInputVerticle.class);
  @Override
  public Future<Void> keyPress() {
    LOGGER.debug("Key pressed");
    System.out.println("Key pressed");
    return null;
  }
}
