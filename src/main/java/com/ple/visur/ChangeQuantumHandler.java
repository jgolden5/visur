package com.ple.visur;

public class ChangeQuantumHandler implements KeymapHandler {
  public static ChangeQuantumHandler make() {
    return new ChangeQuantumHandler();
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
      String sentence = "\"" + quantumName + "\" changeQuantum";
      return ccs.compile(sentence);
    }
  }

}
