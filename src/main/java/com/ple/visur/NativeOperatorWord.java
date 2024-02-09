package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NativeOperatorWord implements Word {

  String opSource;

  public NativeOperatorWord(String opSource) {
    this.opSource = opSource;
  }

  @Override
  public CompiledWordResponse compile(String sentence) {
    Pattern pattern = Pattern.compile("([^\\s]+)(.*)");
    Matcher matcher = pattern.matcher(sentence);
    if(matcher.matches()) {
      CompiledWordResponse cwr;
      Operator op = null;
      switch (opSource) {
        case "absoluteMove":
          op = new AbsoluteMoveOperator();
        default:
          System.out.println("operator " + opSource + " not recognized.");
      }
      cwr = new CompiledWordResponse(op, null, matcher.group(2));
      return cwr;
    } else {
      return null;
    }
  }
}
