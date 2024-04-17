package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.SharedData;

public class ServiceHolder {



  public static ServiceHolder init(SharedData sharedData) {
    return new ServiceHolder(sharedData);
  }

  ServiceHolder(SharedData sharedData) {
    editorContentService = EditorContentService.make();
    editorModelService = EditorModelService.make(sharedData);
    commandCompileService = CommandCompileService.make();
    initializerService = InitializerService.make(editorModelService);
    commandExecutionService = CommandExecutionService.make();
    commandStateService = CommandStateService.make();
    cursorMovementService = CursorMovementService.make();
    modeSwitchService = ModeSwitchService.make();
    insertCharService = InsertCharService.make();
  }

  public static EditorContentService editorContentService;
  public static EditorModelService editorModelService;
  public static CommandCompileService commandCompileService;
  public static CommandExecutionService commandExecutionService;
  public static CommandStateService commandStateService;
  public static CursorMovementService cursorMovementService;
  public static ModeSwitchService modeSwitchService;
  public static InitializerService initializerService;
  public static InsertCharService insertCharService;
  //OperatorService is an interface, so I'm guessing it doesn't belong here, but all classes that use it will
}
