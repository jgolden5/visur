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
    EditorModelService ems = ServiceHolder.editorModelService;
    int contentY = ems.getGlobalVar("contentY").getInt();
    CursorPosition endingPos = startingPos;
    String strToMatch = contentLines[contentY];
    //comments prevent potential infinite loop for stable commit
//    while(!strToMatch.equals("")) {
//      Matcher matcher = pattern.matcher(strToMatch);
//      if(matcher.matches()) {
//        strToMatch.substring(matcher.start());
//      }
//    }
    return endingPos;
  }
}
