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
    if(quantumName != null) {
      sentence += "\"" + quantumName + "\" quantumEnd ";
    }
    EditorMode editorMode = emc.getEditorMode();
    String editorModeAsString = "\"" + editorMode.name() + "\"";
    sentence += editorModeAsString + " changeSubmode";
    return ccs.compile(sentence);
  }
}
