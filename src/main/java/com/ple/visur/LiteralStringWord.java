package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiteralStringWord implements Word {

  public static LiteralStringWord make() {
    return new LiteralStringWord();
  }

  @Override
  public CompiledWordResponse compile(String sentence) {
    Pattern pattern = Pattern.compile("(\"[^\"]*\")(.*)");
    Matcher matcher = pattern.matcher(sentence);
    if(matcher.matches()) {
      Operator op = new LiteralNumberOperator();
      Object opInfo = Integer.parseInt(matcher.group(1));
      CompiledWordResponse compiledWord = new CompiledWordResponse(op, opInfo, matcher.group(2));
      return compiledWord;
    }
    return null;
  }


}
