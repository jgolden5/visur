package com.ple.visur;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface BrowserInputService {

  static BrowserInputService create(Vertx vertx) {
    return new BrowserInputVerticle();
  }

  static BrowserInputService createProxy(Vertx vertx, String address) {
    return new BrowserInputServiceVertxEBProxy(vertx, address);
  }

  Future<Void> keyPress();

  // Actual service operations here...

}
