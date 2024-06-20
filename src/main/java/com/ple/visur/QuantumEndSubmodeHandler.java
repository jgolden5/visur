package com.ple.visur;

public class QuantumEndSubmodeHandler implements KeymapHandler {
  public static QuantumEndSubmodeHandler make() {
    return new QuantumEndSubmodeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeyPressed keyPressed) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    CommandCompileService ccs = ServiceHolder.commandCompileService;
    String key = keyPressed.getKey();
    KeyToQuantumName keyToQuantumName = emc.getKeyToQuantumName();
    String quantumName = keyToQuantumName.get(key);
    String sentence = "";
    int span = emc.getSpan();
    if(quantumName != null && span > 0) {
      sentence += "\"" + quantumName + "\" quantumEnd ";
    }
    sentence += "removeSubmode";
    return ccs.compile(sentence);
  }
}
