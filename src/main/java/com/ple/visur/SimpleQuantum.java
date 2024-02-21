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
  public CursorPosition move(String[] contentLines, CursorPosition startingPos, MovementVector mv) {
    CursorPosition endingPos = startingPos;
    for(int i = 0; i < mv.dx; i++) {

    }
    return endingPos;
  }
}
