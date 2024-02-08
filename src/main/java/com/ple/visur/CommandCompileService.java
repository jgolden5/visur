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
      switch (i) {
        case 0:
          pattern = Pattern.compile("^(-?[0-9]+\\.?[0-9]*)(.*)");

          break;
        case 1:
          pattern = Pattern.compile("(\"[^\"]*\")(.*)");

          break;
        case 2:
          pattern = Pattern.compile("(->*[^\s]+)(.*)");

          break;
        case 3:
          pattern = Pattern.compile("([^\\s]+)(.*)");

          break;
        case 4:
          pattern = Pattern.compile("([^\\s]+)(.*)");

          break;
        default:
          System.out.println("word not recognized!");
          command = null;
          break compileLoop;
      }
      if(currentSentence.equals(newSentence)) {
        i++;
      } else {
        i = 0;
        currentSentence = newSentence.stripLeading();
      }
    }
    return command;
  }

}
