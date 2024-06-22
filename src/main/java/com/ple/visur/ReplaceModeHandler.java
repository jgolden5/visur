package com.ple.visur;

public class ReplaceModeHandler implements KeymapHandler {
  public static InsertModeHandler make() {
    return new InsertModeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeyPressed keyPressed) {
    CommandCompileService ccs = CommandCompileService.make();
    String key = keyPressed.getKey();
    if(key.length() == 1) {
      return ccs.compile("\"" + key + "\" replaceChar 1 0 relativeMove");
    } else if(key.equals("Enter")) {
      return ccs.compile("0 1 relativeMove");
    } else {
      return null;
    }
  }
}
