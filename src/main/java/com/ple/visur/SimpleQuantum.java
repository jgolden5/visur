package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleQuantum implements Quantum {
  Pattern pattern;

  public SimpleQuantum(String regexSource) {
    pattern = Pattern.compile(regexSource);
  }

  @Override
  public int[] getBoundaries() {
    int[] bounds = new int[2];
    //get bounds
    return bounds;
  }

  @Override
  public CursorPosition move(CursorPosition pos, String[] contentLines, MovementVector m) {
    CursorPosition endingCursorPosition;
    EditorModelService ems = ServiceHolder.editorModelService;
    String currentLine = ems.getCurrentContentLine();
    Matcher matcher = pattern.matcher(currentLine);
    boolean xShouldIncrement = m.dx > 0;
    boolean yShouldIncrement = m.dy > 0;
    if(xShouldIncrement) {

    } else {

    }
    if(matcher.find()) {
      int x = matcher.start();
    }
    return endingCursorPosition;
  }
}
