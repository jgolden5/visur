package com.ple.visur;

import java.util.ArrayList;

public class StringToCommandService {
  public VisurCommand compile(String sentence) {
    boolean sentenceCanStillBeCompiled = true;
    VisurCommand command = new VisurCommand();
    ArrayList<Word> words = new ArrayList<>();
    while(sentenceCanStillBeCompiled) {
      int i = 0;
      while (i < 5) {
        String regex;
        switch (i) {
          case 0:
            regex = ""; //LiteralNumberWord
            break;
          case 1:
            regex = ""; //LiteralStringWord
            break;
          case 2:
            regex = ""; //AssignmentWord
            break;
          case 3:
            regex = ""; //OperatorWord
            break;
          case 4:
            regex = ""; //RecallWord
            break;
          default:
            System.out.println("iterator id not recognized");
        }
      }
    }
    return command;
  }
}
