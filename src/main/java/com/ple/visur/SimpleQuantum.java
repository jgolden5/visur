package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleQuantum implements Quantum {
  Pattern pattern;

  public SimpleQuantum(String regexSource) {
    pattern = Pattern.compile(regexSource);
  }

  @Override
  public int[] getBoundaries(String[] contentLines, int x, int y) {
    String currentContentLine = contentLines[y];
    boolean lowerBoundFound = false; //check for lowerBound first
    boolean upperBoundFound = false; //if lowerBoundFound, check for upperBound
    int[] bounds = new int[]{x, x};
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
    CursorPosition destination = startingPos;
    int iterator = mv.dx > 0 ? 1 : -1;
    while(mv.dx != 0 || mv.dy != 0) {
      destination.x = mv.dx > 0 ? bounds[1] : bounds[0];
      String currentLine = contentLines[destination.y];
      boolean startingXIsOutOfBounds = contentBoundsReached(mv.dx, destination.x, destination.y, contentLines);
      boolean keepGoing = !startingXIsOutOfBounds;
      boolean matchFound;
      while (keepGoing) {
        if (currentLineBoundsReached(mv.dx, destination.x, currentLine)) {
          destination = getDestinationOnFollowingLine(mv.dx, contentLines, destination);
        }
        String strToMatch = getStrToMatch(mv.dx, destination.x, destination.y, contentLines);
        matchFound = matchFound(strToMatch);
        if (!matchFound && !contentBoundsReached(mv.dx, destination.x, destination.y, contentLines)) {
          destination.x += iterator;
        } else {
          keepGoing = false;
        }
      }
      if(!startingXIsOutOfBounds) {
        int[] newBounds = getBoundaries(contentLines, destination.x, destination.y);
        bounds = newBounds;
        destination.x = bounds[0];
      }
      mv.dx -= iterator;
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
      return x == 0 && y - 1 < 0;
    } else {
      return true;
    }
  }

  private boolean matchFound(String strToMatch) {
    Matcher matcher = pattern.matcher(strToMatch);
    return matcher.matches();
  }

  private String getStrToMatch(int dx, int x, int y, String[] contentLines) {
    String currentLine = contentLines[y];
    if(contentBoundsReached(dx, x, y, contentLines)) {
      return "";
    } else if(dx > 0) {
      return currentLine.substring(x, x + 1);
    } else { //dx < 0
      return currentLine.substring(x - 1, x);
    }
  }

  private String getFollowingLine(int dx, int y, String[] contentLines) {
    /* note that contentBounds are guaranteed NOT to have been reached yet at
    this point, therefore y can be incremented/decremented without problems */
    if(dx > 0) {
      return contentLines[y + 1];
    } else if(dx < 0) {
      return contentLines[y - 1];
    } else {
      return "";
    }
  }

  private CursorPosition getDestinationOnFollowingLine(int dx, String[] contentLines, CursorPosition prev) {
    CursorPosition newDestination = prev;
    if(dx > 0) {
      newDestination.y++;
      newDestination.x = 0;
    } else if(dx < 0) {
      newDestination.y--;
      int endOfPreviousLine = contentLines[newDestination.y].length();
      newDestination.x = endOfPreviousLine;
    }
    return newDestination;
  }

}
