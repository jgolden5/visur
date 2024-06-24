package com.ple.visur;

public class SearchSubmodeHandler implements KeymapHandler {
  public static SearchSubmodeHandler make() {
    return new SearchSubmodeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeyPressed keyPressed) {
    CommandCompileService ccs = CommandCompileService.make();
    String key = keyPressed.getKey();
    if(key.length() == 1) {
      ExecutionDataStack eds = ServiceHolder.editorModelCoupler.getExecutionDataStack();
      String searchTarget = "";
      if(eds.size() > 0) {
        searchTarget = (String) eds.pop();
      }
      searchTarget += key;
      return ccs.compile("\"" + searchTarget + "\"");
    } else if(key.equals("Enter")) {
      return ccs.compile("\"character\" changeQuantum search \"navigate\" pushSubmode");
    } else if(key.equals("Escape")) {
      return ccs.compile("\"navigate\" pushSubmode");
    } else {
      return null;
    }
  }
}
