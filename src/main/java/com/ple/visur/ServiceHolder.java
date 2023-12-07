package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.SharedData;

public class ServiceHolder {
  public static ServiceHolder init(SharedData sharedData) {
    return new ServiceHolder(sharedData);
  }

  ServiceHolder(SharedData sharedData) {
    editorModelService = EditorModelService.make(sharedData);
    cursorMovementService = CursorMovementService.make();
    initializerService = InitializerService.make(editorModelService);
  }

  public static EditorModelService editorModelService;
  public static CursorMovementService cursorMovementService;
  public static InitializerService initializerService;
  //OperatorService is an interface, so I'm guessing it doesn't belong here, but all classes that use it will
}
