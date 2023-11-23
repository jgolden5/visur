package com.ple.visur;

import io.vertx.core.Vertx;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.eventbus.EventBus;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

public abstract class AbstractVisurVerticle extends AbstractVerticle {
  EventBus bus;
  LocalMap<EditorModelKey, Object> editorModelService;
  LocalMap<EditorModelKey, Object> dataModelService;

  AbstractVisurVerticle() {
    init(Vertx.currentContext().owner(), Vertx.currentContext());
    bus = vertx.eventBus();
    SharedData sharedData = vertx.sharedData();

    editorModel = EditorModelService.make(sharedData);
    dataModelService = DataModelService.make(sharedData);
  }

}
