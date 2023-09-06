package com.ple.visur;

import io.reactivex.rxjava3.core.Completable;
import io.vertx.core.Promise;
import io.vertx.rxjava3.core.eventbus.Message;

public class BrowserOutputVerticle extends AbstractVisurVerticle {
  public void handleChange(Message<Object> event) {
    System.out.println("change message received by output verticle");
  }

  @Override
  public void start() {
    System.out.println("browser output start");
    vertx.eventBus().consumer(BusEvent.modelChange.name(), event -> {
      System.out.println("browser output message received");
      handleChange(event);
    });
  }

}
