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
      currentIndex = mv.dx > 0 ? bounds[1]: bounds[0];
      if (mv.dx != 0) {
        currentIndex = moveLeftRight(currentIndex, editorContent, newlineIndices, mv, bounds);
      }
      if (mv.dy != 0) {
        currentIndex = moveUpDown(currentIndex, editorContent, newlineIndices, mv, bounds);
      }
    }
    return currentIndex;
  }

  /**
   * incrementer var = mv.dx > 0 ? 1 : -1
   * loop through every character in editorContent, checking for match
   * if match is found, return that match index and end search, else, return original startIndex index
   * return caDestination
   * @param startIndex starting ca coordinate
   * @param editorContent
   * @param newlineIndices
   * @param mv
   * @param bounds
   * @return
   */
  private int moveLeftRight(int startIndex, String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv, int[] bounds) {
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

  private int moveUpDown(int caDestination, String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv, int[] bounds) {
    return 0;
  }

  private boolean contentLimitReached(MovementVector mv, int currentIndex, String editorContent) {
    if(mv.dx > 0) {
      return currentIndex > editorContent.length() - 1;
    } else if(mv.dx < 0) {
      return currentIndex <= 0;
    } else {
      return true;
    }
  }

}
