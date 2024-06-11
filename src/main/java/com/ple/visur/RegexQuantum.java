package com.ple.visur;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexQuantum extends Quantum {
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
   * make var firstCharMatches
   * if start < editorContent.length - 1
     * set firstMatchIsChar = Matcher.matches(firstCharInContent)
   * else
     * firstMatchIsChar = false
   * bounds[0] = getLeftBound
   * bounds[1] = getRightBound
   * if bounds[0] == bounds[1], set span = 0
   * @param editorContent
   * @param newlineIndices
   * @param includeTail
   * @return
   */
  @Override
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, boolean includeTail) {
    int[] bounds = new int[2];
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int start = (int)caBVV.getVal();
    boolean firstOrPreviousCharMatches = false;
    if(start < editorContent.length() - 1) {
      Matcher matcher = pattern.matcher(editorContent.substring(start, start + 1));
      firstOrPreviousCharMatches = matcher.matches();
    }
    if(!firstOrPreviousCharMatches && start > 0) {
      Matcher matcher = pattern.matcher(editorContent.substring(start - 1, start));
      firstOrPreviousCharMatches = matcher.matches();
    }
    int leftBound = getLeftBound(firstOrPreviousCharMatches, start, editorContent);
    int rightBound = getRightBound(leftBound, editorContent);
    if(leftBound == rightBound) {
      emc.putSpan(0);
    }
    bounds[0] = leftBound;
    bounds[1] = rightBound;
    return bounds;
  }

  /**
   * make var matchIndex
   * if firstCharMatches, search left for nonmatch
   * else
     * search right for match
     * if NO MATCH is found, search left for match AND THEN left for nonmatch
   * if no match is still found, return start
   * else return matchIndex
   * @param start
   * @param editorContent
   * @return
   */
  private int getLeftBound(boolean firstCharMatches, int start, String editorContent) {
    int leftBound;
    if(firstCharMatches) {
      leftBound = goLeftUntilFound(false, start, editorContent);
    } else {
      leftBound = goRightUntilFound(true, start, editorContent);
      if(leftBound == start) {
        leftBound = goLeftUntilFound(true, start, editorContent);
        leftBound = goLeftUntilFound(false, leftBound, editorContent);
      }
    }
    return leftBound;
  }

  /**
   * make var matchIndex
   * search right for nonmatch
   * if no match is found, return start
   * else return matchIndex
   * @param start
   * @param editorContent
   * @return
   */
  private int getRightBound(int start, String editorContent) {
    return goRightUntilFound(false, start, editorContent);
  }

  private int goLeftUntilFound(boolean matchDesired, int start, String editorContent) {
    int leftBound = start;
    boolean searchConditionFound = false;
    while(!searchConditionFound && leftBound > 0) {
      String strToMatch = editorContent.substring(leftBound - 1, leftBound);
      Matcher matcher = pattern.matcher(strToMatch);
      boolean searchCondition = matchDesired ? matcher.matches() : !matcher.matches();
      if (searchCondition) {
        searchConditionFound = true;
      } else {
        leftBound--;
      }
    }
    if(leftBound == 0 && !matchDesired) {
      searchConditionFound = true;
    }
    if(!searchConditionFound) {
      leftBound = start;
    }
    return leftBound;
  }

  private int goRightUntilFound(boolean matchDesired, int startingIndex, String editorContent) {
    int rightBound = startingIndex;
    boolean searchConditionFound = false;
    while(!searchConditionFound && rightBound < editorContent.length() - 1) {
      String strToMatch = editorContent.substring(rightBound, rightBound + 1);
      Matcher matcher = pattern.matcher(strToMatch);
      boolean searchCondition = matchDesired ? matcher.matches() : !matcher.matches();
      if(searchCondition) {
        searchConditionFound = true;
      } else {
        rightBound++;
      }
    }
    if(rightBound == editorContent.length() - 1 && !matchDesired) {
      searchConditionFound = true;
    }
    if(!searchConditionFound) {
      rightBound = startingIndex;
    }
    return rightBound;
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
