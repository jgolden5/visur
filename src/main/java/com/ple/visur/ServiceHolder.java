package com.ple.visur;

import io.vertx.rxjava3.core.shareddata.SharedData;

public class ServiceHolder {


  public static ServiceHolder init(SharedData sharedData) {
    return new ServiceHolder(sharedData);
  }

  ServiceHolder(SharedData sharedData) {
    editorModelService = EditorModelService.make(sharedData);
    commandCompileService = CommandCompileService.make();
    initializerService = InitializerService.make(editorModelService);
    dfdcInitializerService = DFDCInitializerService.make();
    commandExecutionService = CommandExecutionService.make();
    commandStateService = CommandStateService.make();
    cursorMovementService = CursorMovementService.make();
    modeSwitchService = ModeSwitchService.make();
    insertCharService = InsertCharService.make();
  }

  public static EditorModelService editorModelService;
  public static CommandCompileService commandCompileService;
  public static CommandExecutionService commandExecutionService;
  public static CommandStateService commandStateService;
  public static CursorMovementService cursorMovementService;
  public static ModeSwitchService modeSwitchService;
  public static InitializerService initializerService;
  public static DFDCInitializerService dfdcInitializerService;
  public static InsertCharService insertCharService;
  //OperatorService is an interface, so I'm guessing it doesn't belong here, but all classes that use it will
}
