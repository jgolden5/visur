package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiteralNumberWord implements Word {

  public static LiteralNumberWord make() {
    return new LiteralNumberWord();
  }

  @Override
  public CompiledWord compile(String sentence) {
    Pattern pattern = Pattern.compile("^(-?[0-9]+\\.?[0-9]*)(.*)");
    Matcher matcher = pattern.matcher(sentence);
    if(matcher.matches()) {
      Operator op = new LiteralNumberOperator();
      Object opInfo = Integer.parseInt(matcher.group(1));
      CompiledWord compiledWord = new CompiledWord(op, opInfo, matcher.group(2));
      return compiledWord;
    }
      return null;
  }

}
