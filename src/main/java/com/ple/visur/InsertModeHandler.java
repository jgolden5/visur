package com.ple.visur;

public class InsertModeHandler implements KeymapHandler {

  public static InsertModeHandler make() {
    return new InsertModeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeyPressed keyPressed) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    CommandCompileService ccs = CommandCompileService.make();
    String key = keyPressed.getKey();
    return ccs.compile(key + " insertChar");
  }

}
