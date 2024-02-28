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
  public CursorPosition move(String[] contentLines, CursorPosition startingPos, MovementVector mv, int[] bounds) {
    MovementVector currentMv = mv;
    CursorPosition destination = startingPos;
    int iterator = currentMv.dx > 0 ? 1 : -1;
    while(currentMv.dx != 0 || currentMv.dy != 0) {
      destination.x = bounds[1];
      String currentLine = contentLines[destination.y];
      boolean keepGoing = !contentBoundsReached(currentMv.dx, destination.x, destination.y, contentLines);
      boolean matchFound;
      while(keepGoing) {
        String strToMatch = getStrToMatch(currentMv.dx, destination.x, destination.y, contentLines, false);
        matchFound = matchFound(strToMatch);
        if(!matchFound) {
          if(currentLineBoundsReached(currentMv.dx, destination.x, currentLine)) {
            destination = getDestinationOnFollowingLine(currentMv.dx, destination.y, contentLines, destination);
          } else {
            destination.x++;
          }
        }
        keepGoing = !matchFound && !contentBoundsReached(currentMv.dx, destination.x, destination.y, contentLines);
      }
      currentMv.dx -= iterator;
    }
    return destination;
  }

  public boolean currentLineBoundsReached(int dx, int x, String currentLine) {
    if(dx > 0) {
      return x >= currentLine.length();
    } else if(dx < 0) {
      return x <= 0;
    } else {
      return true;
    }
  }

  public boolean contentBoundsReached(int dx, int x, int y, String[] contentLines) {
    if(dx > 0) {
      return x == contentLines[y].length() && y + 1 >= contentLines.length;
    } else if(dx < 0) {
      return x == 0 && y - 1 <= 0;
    } else {
      return true;
    }
  }

  private boolean matchFound(String strToMatch) {
    Matcher matcher = pattern.matcher(strToMatch);
    return matcher.matches();
  }

  private String getStrToMatch(int dx, int x, int y, String[] contentLines, boolean calledRecursively) {
    String currentLine = contentLines[y];
    if(dx == 0 || contentBoundsReached(dx, x, y, contentLines)) {
      return "";
    } else if(dx > 0) {
      if(currentLineBoundsReached(dx, x, currentLine) && !calledRecursively) {
        return getStrToMatch(dx - 1, 0, y + 1, contentLines, true);
      } else {
        return currentLine.substring(x, x + 1);
      }
    } else { //dx < 0
      if(currentLineBoundsReached(dx, x, currentLine) && !calledRecursively) {
        String previousLine = contentLines[y - 1];
        return getStrToMatch(dx + 1, previousLine.length(), y - 1, contentLines, true);
      } else {
        return currentLine.substring(x, x + 1);
      }
    }
  }

  private CursorPosition getDestinationOnFollowingLine(int dx, int y, String[] contentLines, CursorPosition prev) {
    CursorPosition newDestination = prev;
    if(dx > 0) {
      newDestination.y++;
      newDestination.x = 0;
    } else if(dx < 0) {
      newDestination.y--;
      newDestination.x = contentLines[newDestination.y].length();
    }
    return newDestination;
  }

}
