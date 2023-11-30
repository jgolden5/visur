package com.ple.visur;

import io.vertx.core.Vertx;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.eventbus.EventBus;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import javax.xml.crypto.Data;
import java.awt.*;

//one place for the shared verticle stuff to be

public abstract class AbstractVisurVerticle extends AbstractVerticle {
  final EventBus bus;
  final LocalMap<EditorModelKey, Object> editorModel = vertx.sharedData().getLocalMap("editorModel");

  protected final EditorModelService editorModelService;
  protected final CursorMovementService cursorMovementService;

  AbstractVisurVerticle() {
    init(Vertx.currentContext().owner(), Vertx.currentContext());
    bus = vertx.eventBus();
    editorModelService = EditorModelService.make(editorModel);
    cursorMovementService = CursorMovementService.make(editorModelService);
    Operator operator = new Operator();
  }

}
