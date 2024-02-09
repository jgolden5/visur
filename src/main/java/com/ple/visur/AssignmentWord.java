package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignmentWord implements Word {

  @Override
  public CompiledWordResponse compile(String sentence) {
    Pattern pattern = Pattern.compile("(->*[^\s]+)(.*)");
    Matcher matcher = pattern.matcher(sentence);
    if(matcher.matches()) {
      Operator op = new AssignmentOperator();
      Object opInfo = matcher.group(1).substring(2);
      CompiledWordResponse compiledWordResponse = new CompiledWordResponse(op, opInfo, matcher.group(2));
      return compiledWordResponse;
    }
    return null;
  }

}
