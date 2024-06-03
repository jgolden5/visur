package com.ple.visur;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexQuantum implements Quantum {
  String name;
  Pattern pattern;
  EditorModelCoupler emc = ServiceHolder.editorModelCoupler;

  public RegexQuantum(String name, String regexSource) {
    this.name = name;
    pattern = Pattern.compile(regexSource);
  }

  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    BrickVisurVar caBVV = (BrickVisurVar)emc.getGlobalVar("ca");
    int ca = (int)caBVV.getVal();
    boolean lowerBoundFound = false; //check for lowerBound first
    boolean upperBoundFound = false; //if lowerBoundFound, check for upperBound
    int[] bounds = new int[]{ca, ca};
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

  /** iteratively loop through every dx and dy variable passed into the move method via mv.dx and/or mv.dy
   * make a start var whose value is ca before movement
   * while mv.dx != 0, call moveLeftRight method (all the same inputs + start var).
     * assign return value of moveLeftRight to caDestination var, which will be plugged into moveUpDown for start param
   * likewise, while mv.dy != 0, call moveUpDown method (this time using caDestination as its start parameter)
     * also assign return value of moveUpDown to caDestination
   * return the resulting caDestination var
   * @param editorContent source of content which cursor is moving on
   * @param newlineIndices indices where newline chars occur
   * @param mv vector where movement occurs on both the x and y axes
   * @param bounds current quantum bounds
   * @return resulting ca coordinate from move method
   */
  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv, int[] bounds) {
    int caDestination = (int)ServiceHolder.editorModelCoupler.getGlobalVar("ca").getVal();
    while(mv.dx != 0) {
      caDestination = moveLeftRight(caDestination, editorContent, newlineIndices, mv, bounds);
    }
    while(mv.dy != 0) {
      caDestination = moveUpDown(caDestination, editorContent, newlineIndices, mv, bounds);
    }
    return caDestination;
  }

  private int moveLeftRight(int start, String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv, int[] bounds) {
    int destination = start;
    int iterator = mv.dx > 0 ? 1 : -1;
    while(mv.dx != 0) {
      int destination = mv.dx > 0 ? bounds[1] : bounds[0];
      boolean startingXIsOutOfBounds = contentBoundsReached(mv.dx, destination, editorContent);
      boolean keepGoing = !startingXIsOutOfBounds;
      boolean matchFound;
      while (keepGoing) {
        String strToMatch = getStrToMatch(mv.dx, destination, editorContent);
        matchFound = matchFound(strToMatch);
        if (!matchFound && !contentBoundsReached(mv.dx, destination, editorContent)) {
          destination += iterator;
        } else {
          keepGoing = false;
        }
      }
      if (!startingXIsOutOfBounds) {
        BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
        caBVV.putVal(destination);
        int[] newBounds = getBoundaries(editorContent, newlineIndices, false);
        bounds = newBounds;
        destination = bounds[0];
      } else {
        destination = start;
      }
      mv.dx -= iterator;
    }
    return destination;
  }

  private int moveUpDown(int caDestination, String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv, int[] bounds) {
    return 0;
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
