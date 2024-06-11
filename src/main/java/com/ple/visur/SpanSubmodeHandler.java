package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpanSubmodeHandler implements KeymapHandler {
  public static SpanSubmodeHandler make() {
    return new SpanSubmodeHandler();
  }

  @Override
  public VisurCommand toVisurCommand(KeyPressed keyPressed) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    CommandCompileService ccs = ServiceHolder.commandCompileService;
    String key = keyPressed.getKey();
    Pattern pattern = Pattern.compile("\\d");
    Matcher matcher = pattern.matcher(key);
    String sentence = "";
    if(matcher.matches()) {
      int keyAsInt = Integer.parseInt(key);
      sentence += "" + keyAsInt + " setSpan ";
    }
    sentence += "removeSubmode";
    return ccs.compile(sentence);
  }
}
