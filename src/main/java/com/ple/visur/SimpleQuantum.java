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
    boolean lowerBoundFound = false; //check for lowerBound first
    boolean upperBoundFound = false; //if lowerBoundFound, check for upperBound
    int[] bounds = new int[]{contentX, contentX};
    while(!(lowerBoundFound && upperBoundFound)) {
      if(bounds[0] == 0) {
        lowerBoundFound = true;
      }
      if(bounds[1] == currentContentLine.length()) {
        upperBoundFound = true;
      }
      if(lowerBoundFound) {
        if(!upperBoundFound) {
          bounds[1]++;
        } else {
          break;
        }
      } else {
        bounds[0]--;
      }
      String strToMatch = currentContentLine.substring(bounds[0], bounds[1]);
      Matcher matcher = pattern.matcher(strToMatch);
      if(!matcher.matches()) {
        if(!lowerBoundFound) {
          lowerBoundFound = true;
          bounds[0]++;
        } else {
          upperBoundFound = true;
          bounds[1]--;
        }
      }
    }
    return bounds;
  }

  @Override
  public CursorPosition move(String[] contentLines, MovementVector mv, int[] quantumBounds) {
    CursorPosition endingPos = null;
    int startingX = quantumBounds[1];
    //search for pattern after startingX
    return endingPos;
  }

}
