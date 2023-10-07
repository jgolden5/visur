package com.ple.visur;

import io.vertx.core.Vertx;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.eventbus.EventBus;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import static com.ple.visur.ModelIntKey.*;
import static com.ple.visur.ModelStringKey.*;

public abstract class AbstractVisurVerticle extends AbstractVerticle {

  SharedData sharedData;
  LocalMap<String, String> modelString;
  LocalMap<String, Integer> modelInt;
  EventBus bus;
  EditorModelService editorModelService;

  AbstractVisurVerticle() {
    init(Vertx.currentContext().owner(), Vertx.currentContext());
    bus = vertx.eventBus();
    sharedData = vertx.sharedData();
    modelString = sharedData.getLocalMap("modelString");
    modelInt = sharedData.getLocalMap("modelInt");
    editorModelService = new EditorModelService(sharedData);
  }

}
