package com.ple.visur;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface BrowserInputService {

  Future<Void> keyPress(String key);

}
