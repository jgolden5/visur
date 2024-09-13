package com.ple.visur;

import CursorPositionDC.EditorContentService;
import io.vertx.rxjava3.core.shareddata.SharedData;

public class ServiceHolder {



  public static ServiceHolder init(SharedData sharedData) {
    return new ServiceHolder(sharedData);
  }

  ServiceHolder(SharedData sharedData) {
    editorContentService = EditorContentService.make();
    editorModelCoupler = EditorModelCoupler.make(sharedData);
    commandCompileService = CommandCompileService.make();
    initializerService = InitializerService.make(editorModelCoupler);
    commandExecutionService = CommandExecutionService.make();
    commandStateService = CommandStateService.make();
    modeSwitchService = ModeSwitchService.make();
  }

  public static EditorContentService editorContentService;
  public static EditorModelCoupler editorModelCoupler;
  public static CommandCompileService commandCompileService;
  public static CommandExecutionService commandExecutionService;
  public static CommandStateService commandStateService;
  public static ModeSwitchService modeSwitchService;
  public static InitializerService initializerService;
  //OperatorService is an interface, so I'm guessing it doesn't belong here, but all classes that use it will
}
