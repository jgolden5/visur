package com.ple.visur;

public class ChangeScopeSubmodeHandler implements KeymapHandler {

  public static ChangeScopeSubmodeHandler make() {
    return new ChangeScopeSubmodeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeyPressed keyPressed) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    CommandCompileService ccs = CommandCompileService.make();
    String sentence = "";
    KeyToQuantumName keyToQuantumName = emc.getKeyToQuantumName();
    String scopeName = keyToQuantumName.get(keyPressed.getKey());
    if(scopeName != null) {
      sentence += "\"" + scopeName + "\" changeScope ";
    }
    sentence += "\"navigate\" pushSubmode";
    return ccs.compile(sentence);
  }

}
