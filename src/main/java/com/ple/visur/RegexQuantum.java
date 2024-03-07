package com.ple.visur;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexQuantum implements Quantum {
  String name;
  Pattern pattern;

  public RegexQuantum(String name, String regexSource) {
    this.name = name;
    pattern = Pattern.compile(regexSource);
  }

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int x, int y, boolean includeTail) {
    boolean lowerBoundFound = false; //check for lowerBound first
    boolean upperBoundFound = false; //if lowerBoundFound, check for upperBound
    int[] bounds = new int[]{x, x};
    while(!(lowerBoundFound && upperBoundFound)) {
      if(!lowerBoundFound) {
        if(bounds[0] > 0) {
          bounds[0]--;
        } else {
          lowerBoundFound = true;
          continue;
        }
      } else {
        if(bounds[1] < editorContent.length()) {
          bounds[1]++;
        } else {
          upperBoundFound = true;
          continue;
        }
      }
      String strToMatch = editorContent.substring(bounds[0], bounds[1]);
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
  public CursorPosition move(String editorContent, ArrayList<Integer> newlineIndices, CursorPosition startingPos, MovementVector mv, int[] bounds) {
    CursorPosition destination = startingPos;
    int iterator = mv.dx > 0 ? 1 : -1;
    while(mv.dx != 0) {
      destination.x = mv.dx > 0 ? bounds[1] : bounds[0];
      boolean startingXIsOutOfBounds = contentBoundsReached(mv.dx, destination.x, editorContent);
      boolean keepGoing = !startingXIsOutOfBounds;
      boolean matchFound;
      while(keepGoing) {
        String strToMatch = getStrToMatch(mv.dx, destination.x, editorContent);
        matchFound = matchFound(strToMatch);
        if(!matchFound && !contentBoundsReached(mv.dx, destination.x, editorContent)) {
          destination.x += iterator;
        } else {
          keepGoing = false;
        }
      }
      if(!startingXIsOutOfBounds) {
        int[] newBounds = getBoundaries(editorContent, newlineIndices, destination.x, destination.y, false);
        bounds = newBounds;
        destination.x = bounds[0];
      }
      mv.dx -= iterator;
    }
    destination.y = getY(editorContent, newlineIndices, destination.x);
    System.out.println("destination x = " + destination.x);
    System.out.println("destination y = " + destination.y);
    return destination;
  }

  public String getName() {
    return name;
  }

  private boolean contentBoundsReached(int dx, int x, String contentLines) {
    if(dx > 0) {
      return x >= contentLines.length() - 1;
    } else if(dx < 0) {
      return x <= 0;
    } else {
      return true;
    }
  }

  private boolean matchFound(String strToMatch) {
    Matcher matcher = pattern.matcher(strToMatch);
    return matcher.matches();
  }

  private String getStrToMatch(int dx, int x, String contentLines) {
    if(contentBoundsReached(dx, x, contentLines)) {
      return "";
    } else if(dx > 0) {
      return contentLines.substring(x, x + 1);
    } else { //dx < 0
      return contentLines.substring(x - 1, x);
    }
  }

  private int getY(String editorContent, ArrayList<Integer> newlineIndices, int x) { //eventually change to binary search
    boolean yFound = false;
    int y = 0;
    boolean newlineCharIsAtEndOfLine = editorContent.charAt(editorContent.length() - 1) == '\n';
    while(!yFound) {
      if(y < newlineIndices.size()) {
        if (x > newlineIndices.get(y)) {
          y++;
        } else {
          yFound = true;
        }
      } else if(!newlineCharIsAtEndOfLine) {
        yFound = true;
      }
    }
    return y;
  }

}
