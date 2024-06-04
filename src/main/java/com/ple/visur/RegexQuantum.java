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

  public String getName() {
    return name;
  }

  /**
   * declare bounds var = empty int array
   * call emc.getGlobalVar to get ca val from caBVV
   * use ca as starting index for searching in editorContent for regex pattern
   * check for first match in string by searching backwards until a non-match is found or contentLimitReached
   * if currentIndex is a match, set that to bounds[0], else, keep searching, and set first match to bounds[0]
   * keep searching after bounds[0] is found until a nonmatch is found
   * set bounds[1] = first nonmatch found (after lowerBoundsFound) - 1
   * return bounds
   * @param editorContent
   * @param newlineIndices
   * @param includeTail
   * @return
   */
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    int[] bounds = new int[2];
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int currentIndex = (int)caBVV.getVal();
    bounds[0] = getLeftBound(currentIndex, editorContent);
    bounds[1] = getRightBound(bounds[0], editorContent);
    return bounds;
  }

  /**
   * set leftBound = startingIndex
   * if startingIndex <= 0, leftBound is found
   * if first search resulted in a match, we are searching for the first nonmatch, else, search for first match
   * if no match is found searching backwards (meaning a limit was hit before a match was found), search forwards for a match
   * if no match is found in the entire editorContent, return -1, which means the quantum should be impossible to switch to
   * @param startingIndex
   * @param editorContent
   * @return
   */
  private int getLeftBound(int startingIndex, String editorContent) {
    int leftBound = startingIndex;
    if(leftBound > 0) {
      leftBound = goLeftUntilFound("nonmatch", leftBound, editorContent);
      if(leftBound == startingIndex) {
        leftBound = goRightUntilFound("match", leftBound, editorContent);
      }
      if(leftBound == -1) {
        leftBound = startingIndex;
        leftBound = goLeftUntilFound("match", leftBound, editorContent);
        leftBound = goLeftUntilFound("nonmatch", leftBound, editorContent);
      }
    }
    return leftBound;
  }

  private int goLeftUntilFound(String searchTarget, int startingIndex, String editorContent) {
    int leftBoundMatch = startingIndex;
    boolean searchConditionFound = false;
    while(!searchConditionFound && leftBoundMatch > 0) {
      String strToMatch = editorContent.substring(leftBoundMatch - 1, leftBoundMatch);
      Matcher matcher = pattern.matcher(strToMatch);
      boolean searchCondition = searchTarget.equals("match") ? matcher.matches() : !matcher.matches();
      if(searchCondition) {
        searchConditionFound = true;
      } else {
        leftBoundMatch--;
      }
    }
    if(searchConditionFound || leftBoundMatch == 0) {
      return leftBoundMatch;
    } else {
      return -1;
    }
  }

  private int goRightUntilFound(String searchTarget, int startingIndex, String editorContent) {
    int rightBoundMatch = startingIndex;
    boolean searchConditionFound = false;
    while(!searchConditionFound && rightBoundMatch <= editorContent.length() - 1) {
      String strToMatch = editorContent.substring(rightBoundMatch, rightBoundMatch + 1);
      Matcher matcher = pattern.matcher(strToMatch);
      boolean searchCondition = searchTarget.equals("match") ? matcher.matches() : !matcher.matches();
      if(searchCondition) {
        searchConditionFound = true;
      } else {
        rightBoundMatch++;
      }
    }
    if(searchConditionFound || rightBoundMatch < editorContent.length() - 1 && searchTarget.equals("match") || searchTarget.equals("nonmatch")) {
      return rightBoundMatch;
    } else {
      return -1;
    }
  }

  private int getRightBound(int startingIndex, String editorContent) { //this assumes currentIndex is ALWAYS accurate
    return goRightUntilFound("nonmatch", startingIndex, editorContent);
  }

  /** iteratively loop through every dx and dy variable passed into the move method via mv.dx and/or mv.dy
   * make a start var whose value is ca before movement
   * if mv.dx != 0, call moveLeftRight method (all the same inputs + start var).
     * assign return value of moveLeftRight to caDestination var, which will be plugged into moveUpDown for start param
   * likewise, if mv.dy != 0, call moveUpDown method (this time using caDestination as its start parameter)
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
    int currentIndex = bounds[0];
    if(!contentLimitReached(mv, bounds[1], editorContent)) {
      if (mv.dx != 0) {
        currentIndex = mv.dx > 0 ? bounds[1] : bounds[0];
        currentIndex = moveLeftRight(currentIndex, editorContent, mv);
      }
      if (mv.dy != 0) {
        currentIndex = bounds[0];
        currentIndex = moveUpDown(currentIndex, editorContent, newlineIndices, mv, bounds);
      }
    }
    return currentIndex;
  }

  /** goes from endpoint of previous bound to beginning of next bound after quantum movement
   * incrementer var = mv.dx > 0 ? 1 : -1
   * loop through every character in editorContent, checking for match
   * if match is found, return that match index and end search, else, return original startIndex index
   * return caDestination
   * @param startIndex starting ca coordinate
   * @param editorContent
   * @param mv
   * @return resulting index of regex search movement
   */
  private int moveLeftRight(int startIndex, String editorContent, MovementVector mv) {
    int incrementer = mv.dx > 0 ? 1 : -1;
    int current = startIndex;
    boolean matchFound = false;
    boolean contentBoundsReached = false;
    boolean keepGoing = true;
    while(keepGoing) {
      if(contentLimitReached(mv, current, editorContent)) {
        contentBoundsReached = true;
        current = startIndex;
      } else {
        char currentChar = mv.dx > 0 ? editorContent.charAt(current) : editorContent.charAt(current - 1);
        String currentCharAsString = String.valueOf(currentChar);
        Matcher matcher = pattern.matcher(currentCharAsString);
        if (matcher.matches()) {
          matchFound = true;
        } else {
          current += incrementer;
        }
      }
      keepGoing = !(matchFound || contentBoundsReached);
    }
    return current;
  }

  /** moves up or down essentially like a character quantum.
 * The only difference is that getBoundaries is called afterwards at the resulting cx cy coordinate
   * construct a CharacterQuantum var called cq
   * if mv.dy > 0, call cq.moveDown, else call cq.moveUp. Assign the result to ca var
   * return ca
   * @param startingCA
   * @param editorContent
   * @param newlineIndices
   * @param mv
   * @param bounds
   * @return absolute position after movement (aka ca var)
   */
  private int moveUpDown(int startingCA, String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv, int[] bounds) {
    CharacterQuantum cq = new CharacterQuantum();
    return cq.move(editorContent, newlineIndices, mv, bounds);
  }

  private boolean contentLimitReached(MovementVector mv, int currentIndex, String editorContent) {
    if(mv.dx > 0) {
      return currentIndex > editorContent.length() - 1;
    } else if(mv.dx < 0) {
      return currentIndex <= 0;
    } else if(mv.dy != 0) {
      return false;
    } else {
      return true;
    }
  }

}
