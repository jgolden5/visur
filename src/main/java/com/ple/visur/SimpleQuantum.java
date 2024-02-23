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
  public CursorPosition move(String[] contentLines, CursorPosition startingPos, MovementVector mv, int[] quantumBounds) {
    CursorPosition destination = startingPos;
    String currentLine = contentLines[startingPos.y];
    destination.x = quantumBounds[1];
    while(true) {
      if(destination.x + mv.dx > currentLine.length() || destination.x + mv.dx < 0) {
        if(mv.dx > 0) {
          if (destination.y < contentLines.length - 1) {
            destination.y++;
            destination.x = 0;
          }
        } else {
          if(destination.y > 0) {
            destination.y--;
            destination.x = contentLines[destination.y].length();
          }
        }
        break;
      }
      String strToMatch = currentLine.substring(destination.x, destination.x + 1);
      Matcher matcher = pattern.matcher(strToMatch);
      if(matcher.matches()) {
        break;
      } else {
        destination.x += mv.dx;
      }
    }
    return destination;
  }

}
