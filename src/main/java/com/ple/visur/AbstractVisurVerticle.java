package com.ple.visur;

import io.vertx.core.Vertx;

import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.eventbus.EventBus;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import static com.ple.visur.ModelIntKey.*;

public abstract class AbstractVisurVerticle extends AbstractVerticle {

  SharedData sharedData;
  LocalMap<String, String> modelString;
  LocalMap<String, Integer> modelInt;
  EventBus bus;
  AbstractVisurVerticle() {
    init(Vertx.currentContext().owner(), Vertx.currentContext());
    bus = vertx.eventBus();
    sharedData = vertx.sharedData();
    modelString = sharedData.getLocalMap("modelString");
    modelInt = sharedData.getLocalMap("modelInt");
    modelInt.put(cursorX.name(), 0);
    modelInt.put(cursorY.name(), 0);
    modelString.put("content", "hello");
  }
}
