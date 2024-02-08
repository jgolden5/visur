package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandCompileService {
  public static CommandCompileService make() {
    return new CommandCompileService();
  }

  public VisurCommand compile(String sentence) {
    VisurCommand command = new VisurCommand(sentence);
    String currentSentence = sentence;

    int i = 0;
    compileLoop:
    while(currentSentence != "") {
      Pattern pattern;
      Word wordToAddToCommand;
      String newSentence = "";
      Operator op;
      switch (i) {
        case 0:
          pattern = Pattern.compile("^(-?[0-9]+\\.?[0-9]*)(.*)");
          op = LiteralNumberWord.make().toOperator();
          break;
        case 1:
          pattern = Pattern.compile("(\"[^\"]*\")(.*)");
          op = LiteralStringWord.make().toOperator();
          break;
        case 2:
          pattern = Pattern.compile("(->*[^\s]+)(.*)");
          op = AssignmentWord.make().toOperator();
          break;
        case 3:
          pattern = Pattern.compile("([^\\s]+)(.*)");
          op = NativeOperatorWord.make().toOperator();
          break;
        case 4:
          pattern = Pattern.compile("([^\\s]+)(.*)");
          op = RecallWord.make().toOperator();
          break;
        default:
          System.out.println("word not recognized!");
          command = null;
          break compileLoop;
      }
      if(noMatchFound) {
        i++;
      } else {
        i = 0;
        currentSentence = newSentence.stripLeading();
      }
    }
    return command;
  }

}
