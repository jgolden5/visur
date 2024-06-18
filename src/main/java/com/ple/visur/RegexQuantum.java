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
   * if start < editorContent.length() - 1
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
  public int[] getBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int span, boolean includeTail) {
    int[] bounds = new int[2];
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int leftBound = (int)caBVV.getVal();
    int rightBound = leftBound;
    if(span > 0) {
      leftBound = getQuantumStart(leftBound);
    } else if(isInMiddleOfRegex(leftBound)) {
      leftBound = getPrevBound(leftBound);
    }
    int spansRemaining = span;
    while(spansRemaining > 0) {
      rightBound = getQuantumEnd(rightBound);
      spansRemaining--;
      if(spansRemaining > 0) {
        rightBound = getNextBound(rightBound);
      }
    }
    caBVV.putVal(leftBound);
    bounds[0] = leftBound;
    bounds[1] = rightBound;
    return bounds;
  }

  private boolean isInMiddleOfRegex(int bound) {
    String editorContent = emc.getEditorContent();
    if(bound > 0 && bound < editorContent.length()) {
      Matcher prevMatcher = pattern.matcher(editorContent.substring(bound - 1, bound));
      Matcher nextMatcher = pattern.matcher(editorContent.substring(bound, bound + 1));
      boolean matchExistsBefore = prevMatcher.matches();
      boolean matchExistsAfter = nextMatcher.matches();
      return matchExistsBefore && matchExistsAfter;
    } else {
      return false;
    }
  }

  private int getQuantumStart(int start) {
    int leftBound = start;
    boolean endFound = false;
    while(!endFound) {
      if(leftBound > 0) {
        String strToMatch = emc.getStrToMatch(false, leftBound);
        Matcher matcher = pattern.matcher(strToMatch);
        if (matcher.matches()) {
          leftBound--;
        } else {
          endFound = true;
        }
      } else {
        endFound = true;
      }
    }
    return leftBound;
  }

  private int getQuantumEnd(int start) {
    String editorContent = emc.getEditorContent();
    int rightBound = start;
    boolean endFound = false;
    while(!endFound) {
      if(rightBound < editorContent.length()) {
        String strToMatch = emc.getStrToMatch(true, rightBound);
        Matcher matcher = pattern.matcher(strToMatch);
        if (matcher.matches()) {
          rightBound++;
        } else {
          endFound = true;
        }
      } else {
        endFound = true;
      }
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
   * @return resulting ca coordinate from move method
   */
  @Override
  public int move(String editorContent, ArrayList<Integer> newlineIndices, MovementVector mv) {
    BrickVisurVar caBVV = (BrickVisurVar) emc.getGlobalVar("ca");
    int startingCA = (int)caBVV.getVal();
    int destination = startingCA;
    int span = emc.getSpan();
    int spansRemaining = span;
    int incrementer = mv.dx > 0 ? 1 : -1;
    boolean leftRightMovement = mv.dx != 0;
    while(mv.dx != 0) {
      destination = mv.dx > 0 ? getNextBound(destination) : getPrevBound(destination);
      if(span > 0 && isAtBeginningOfQuantum(destination)) {
        spansRemaining--;
      }
      while(spansRemaining > 0) {
        destination = mv.dx > 0 ? getNextQuantumStart(destination) : getPrevQuantumStart(destination);
        spansRemaining--;
        if(spansRemaining > 0) {
          destination = mv.dx > 0 ? getNextBound(destination) : getPrevBound(destination);
        }
      }
      mv.dx -= incrementer;
    }
    while(mv.dy != 0) {
      CharacterQuantum cq = new CharacterQuantum();
      destination = cq.move(editorContent, newlineIndices, mv);
      incrementer = mv.dy > 0 ? 1 : -1;
      destination -= incrementer;
    }
    if(span > 0 && !isAtBeginningOfQuantum(destination) && leftRightMovement) {
      destination = startingCA;
    }
    System.out.println("destination = " + destination);
    return destination;
  }

  private boolean isAtBeginningOfQuantum(int bound) {
    String nextString = emc.getStrToMatch(true, bound);
    Matcher nextStringMatcher = pattern.matcher(nextString);
    boolean nextStringMatches = nextStringMatcher.matches();
    boolean prevStringMatches = false;
    if(bound > 0) {
      String prevString = emc.getStrToMatch(false, bound);
      Matcher prevStringMatcher = pattern.matcher(prevString);
      prevStringMatches = prevStringMatcher.matches();
    }
    return nextStringMatches && !prevStringMatches;
  }

  private int getNextQuantumStart(int bound) {
    String editorContent = emc.getEditorContent();
    int nextStart = bound;
    boolean matchFound = false;
    while(!matchFound) {
      if(nextStart >= editorContent.length() - 1) {
        break;
      }
      String strToMatch = editorContent.substring(nextStart, nextStart + 1);
      Matcher matcher = pattern.matcher(strToMatch);
      if(matcher.matches()) {
        matchFound = true;
      } else {
        nextStart++;
      }
    }
    if(!matchFound) {
      nextStart = bound;
    }
    return nextStart;
  }

  private int getPrevQuantumStart(int bound) {
    int prevStart = bound;
    boolean matchFound = false;
    while(!matchFound) {
      if(isAtBeginningOfQuantum(prevStart)) {
        matchFound = true;
      } else {
        prevStart--;
      }
    }
    if(!matchFound) {
      prevStart = bound;
    }
    return prevStart;
  }

  /** searches for the first bound that cursor could move to if span == 0
   * set bound var equal to caBVV's val
   * set while loop condition initial value = bound >= editorContent.length - 1,
     * (the above is to ensure that editorContent.substring(bound, bound + 1) will be valid)
   * set firstWasMatch var = false
     * (the above is because if the first is not a match, we don't want to search for the next nonmatch)
   * if bound < editorContent.length, we set firstWasMatch var to equal matcher.matches(firstCharInEC)
   * increment bound
   * loop while !invalidBoundFound && firstWasMatch
     * if(matcher.matches(nextCharInEC))
       * bound++
     * else
       * invalidBoundFound = false
   * @return bound
   */
  public int getNextBound(int bound) {
    String editorContent = emc.getEditorContent();
    boolean nonmatchFound = bound >= editorContent.length() - 1;
    boolean firstWasMatch = false;
    if(bound < editorContent.length()) {
      Matcher matcher = pattern.matcher(editorContent.substring(bound, bound + 1));
      firstWasMatch = matcher.matches();
      bound++;
    }
    if(firstWasMatch) {
      while (!nonmatchFound) {
        String strToMatch = editorContent.substring(bound, bound + 1);
        Matcher matcher = pattern.matcher(strToMatch);
        if (matcher.matches()) {
          bound++;
          if (bound == editorContent.length()) {
            nonmatchFound = true;
          }
        } else {
          nonmatchFound = true;
        }
      }
    }
    return bound;
  }

  /** it's the same idea as getNextBound, but it searches backwards
   * @return
   */
  public int getPrevBound(int bound) {
    String editorContent = emc.getEditorContent();
    boolean invalidBoundFound = bound == 0;
    boolean firstWasMatch = false;
    if(bound > 0) {
      String strToMatch = editorContent.substring(bound - 1, bound);
      Matcher matcher = pattern.matcher(strToMatch);
      firstWasMatch = matcher.matches();
      bound--;
    }
    while(!invalidBoundFound && firstWasMatch) {
      Matcher matcher = pattern.matcher(editorContent.substring(bound - 1, bound));
      if(matcher.matches()) {
        bound--;
        if(bound == 0) {
          invalidBoundFound = true;
        }
      } else {
        invalidBoundFound = true;
      }
    }
    return bound;
  }

}
