package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NativeOpWord implements Word {

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
          op = new AbsoluteMoveOp();
          break;
        case "relativeMove":
          op = new RelativeMoveOp();
          break;
        case "changeSubmode":
          op = new ChangeSubmodeOp();
          break;
        case "changeQuantum":
          op = new ChangeQuantumOp();
          break;
        case "+":
          op = new AddOperator();
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
