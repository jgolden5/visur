package com.ple.visur;

import io.vertx.core.Vertx;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.eventbus.EventBus;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import javax.xml.crypto.Data;

//one place for the shared verticle stuff to be

public abstract class AbstractVisurVerticle extends AbstractVerticle {
  final EventBus bus;
  final LocalMap<EditorModelKey, Object> dataModel;

  protected final EditorModelService editorModelService;
  protected final DataModelService dataModelService;

  AbstractVisurVerticle() {
    init(Vertx.currentContext().owner(), Vertx.currentContext());
    bus = vertx.eventBus();
    SharedData sharedData = vertx.sharedData();
    editorModel = sharedData.getLocalMap("editorModel");
    editorModelService = EditorModelService.make(editorModel);

    EditorModelService editorModel = EditorModelService.make(sharedData);
    DataModelService dataModel = DataModelService.make(sharedData);
    sharedData.getLocalMap("editorModel").put(editorModel);
  }

}
