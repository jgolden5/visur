package com.ple.visur;

public class ChangeCursorQuantumHandler implements KeymapHandler {
  public static ChangeCursorQuantumHandler make() {
    return new ChangeCursorQuantumHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeyPressed keyPressed) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    CommandCompileService ccs = ServiceHolder.commandCompileService;
    KeyToQuantumName keyToQuantumName = emc.getKeyToQuantumName();
    String key = keyPressed.getKey();
    String quantumName = keyToQuantumName.get(key);
    if(quantumName == null) {
      return null;
    } else {
      String sentence = "\"" + quantumName + "\" changeCursorQuantum";
      return ccs.compile(sentence);
    }
  }

}
