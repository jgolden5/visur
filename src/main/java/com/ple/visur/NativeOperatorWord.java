package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NativeOperatorWord implements Word {

  @Override
  public CompiledWordResponse compile(String sentence) {
    Pattern pattern = Pattern.compile("([^\\s]+)(.*)");
    Matcher matcher = pattern.matcher(sentence);
    if(matcher.matches()) {
      String opSource = matcher.group(1);
      CompiledWordResponse cwr;
      Operator op = null;
      switch (opSource) {
        case "absoluteMove":
          op = new AbsoluteMoveOperator();
          break;
        default:
          System.out.println("operator " + opSource + " not recognized.");
      }
      if(op != null) {
        cwr = new CompiledWordResponse(op, null, matcher.group(2));
      } else {
        cwr = null;
      }
      return cwr;
    } else {
      return null;
    }
  }
}
