package com.ple.visur;

import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyWasPressedVerticle extends AbstractVisurVerticle implements BrowserInputService {

  @Override
  public Future<Void> keyPress(String key) {
    return Future.succeededFuture();
  }

}
