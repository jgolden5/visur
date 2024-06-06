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
    VisurCommand command;
    if(quantumName != null) {
      command = ccs.compile("\"" + quantumName + "\" quantumStart");
    } else {
      EditorMode editorMode = emc.getEditorMode();
      String editorModeAsString = "\"" + editorMode.name() + "\"";
      command = ccs.compile(editorModeAsString + " changeSubmode");
    }
    return command;
  }

}
