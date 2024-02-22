package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleQuantum implements Quantum {
  Pattern pattern;

  public SimpleQuantum(String regexSource) {
    pattern = Pattern.compile(regexSource);
  }

  @Override
  public int[] getBoundaries(String[] contentLines, int contentX, int contentY) {
    String currentContentLine = contentLines[contentY];
    int[] bounds = new int[]{contentX, currentContentLine.length()};
    boolean keepGoing = true;
    while(keepGoing) {
      String strToMatch = currentContentLine.substring(bounds[0], bounds[1]);
      Matcher matcher = pattern.matcher(strToMatch);
      if(matcher.matches()) {
        if(bounds[0] > 0) {
          bounds[0]--;
        } else {
          keepGoing = false;
        }
      } else if(matcher.find()) {
        bounds[0] = matcher.start() + contentX;
        bounds[1] = matcher.end() + contentX;
      } else {
        keepGoing = false;
      }
    }
    return bounds;
  }

  @Override
  public CursorPosition move(String[] contentLines, CursorPosition startingPos, MovementVector mv) {
    EditorModelService ems = ServiceHolder.editorModelService;
    int contentY = ems.getGlobalVar("contentY").getInt();
    CursorPosition endingPos = startingPos;
    String strToMatch = contentLines[contentY];
    int[] bounds = new int[]{ems.getQuantumStart(), ems.getQuantumEnd()};
    if(bounds != null) {
      System.out.println("start bound = " + bounds[0]);
      System.out.println("end bound = " + bounds[1]);
    } else {
      System.out.println("bounds = null");
    }
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
