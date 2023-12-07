package com.ple.visur;

import io.vertx.core.Vertx;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.core.eventbus.EventBus;
import io.vertx.rxjava3.core.shareddata.LocalMap;
import io.vertx.rxjava3.core.shareddata.SharedData;

import java.util.HashMap;

import static com.ple.visur.EditorMode.editing;
import static com.ple.visur.EditorModelKey.*;
import static com.ple.visur.EditorModelKey.editorMode;

public abstract class AbstractVisurVerticle extends AbstractVerticle {
  final EventBus bus;

  AbstractVisurVerticle() {
    init(Vertx.currentContext().owner(), Vertx.currentContext());
    bus = vertx.eventBus();
  }

}
