package com.ple.visur;

public class QuantumStartSubmodeHandler implements KeymapHandler {

  public static QuantumStartSubmodeHandler make() {
    return new QuantumStartSubmodeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeyPressed keyPressed) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    CommandCompileService ccs = ServiceHolder.commandCompileService;
    String key = keyPressed.getKey();
    KeyToQuantumName keyToQuantumName = emc.getKeyToQuantumName();
    String quantumName = keyToQuantumName.get(key);
    String sentence = "";
    if(quantumName != null) {
      sentence += "\"" + quantumName + "\" quantumStart ";
    }
    sentence += "removeSubmode";
    return ccs.compile(sentence);
  }

}
