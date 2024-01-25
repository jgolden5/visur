package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.SharedData;

public class ServiceHolder {

  public static ServiceHolder init(SharedData sharedData) {
    return new ServiceHolder(sharedData);
  }

  ServiceHolder(SharedData sharedData) {
    editorModelService = EditorModelService.make(sharedData);
    commandService = CommandService.make();
    cursorMovementService = CursorMovementService.make();
    modeSwitchService = ModeSwitchService.make();
    initializerService = InitializerService.make(editorModelService);
    insertCharService = InsertCharService.make();
  }

  public static EditorModelService editorModelService;
  public static CommandService commandService;
  public static CursorMovementService cursorMovementService;
  public static ModeSwitchService modeSwitchService;
  public static InitializerService initializerService;
  public static InsertCharService insertCharService;
  //OperatorService is an interface, so I'm guessing it doesn't belong here, but all classes that use it will
}
