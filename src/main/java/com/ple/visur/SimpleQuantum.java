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
  public CursorPosition move(String regex, String[] contentLines, CursorPosition pos, MovementVector m) {
    CursorPosition endingPos = pos;
    String line = "abcdef";
    boolean xShouldDecrement = m.dx < 0 && pos.x > 0;
    boolean xShouldIncrement = m.dx > 0 && pos.x < line.length();
    if(xShouldIncrement || xShouldDecrement) {
      String portionOfLineToSearch;
      if(xShouldIncrement) {
        portionOfLineToSearch = line.substring(pos.x);
      } else {
        portionOfLineToSearch = line.substring(0, pos.x);
      }
      int absDx = (m.dx > 0) ? m.dx : -m.dx;
      if(xShouldIncrement) {
        regex = ".{" + absDx + "}(.)"; //.{2}(.)
      } else {
        regex = "(.{" + absDx + "}$)"; //(.{2}.$)
      }
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(portionOfLineToSearch);
      if(matcher.find()) {
        if(xShouldIncrement) {
          endingPos.x = pos.x + matcher.start(1);
        } else {
          endingPos.x = matcher.start(1);
        }
      } else if(xShouldIncrement) {
        endingPos.x = line.length();
      }
    }
    return endingPos;
  }
}
