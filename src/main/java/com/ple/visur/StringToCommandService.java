package com.ple.visur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToCommandService {
  public static StringToCommandService make() {
    return new StringToCommandService();
  }

  public VisurCommand compile(String sentence) {
    VisurCommand command = new VisurCommand(sentence);
    String currentSentence = sentence;

    while(currentSentence != "") {
      int i = 0;
      while (i <= 5) {
        Pattern pattern;
        Word wordToAddToCommand;
        switch (i) {
          case 0:
            pattern = Pattern.compile("^(-?[0-9]+\\.?[0-9]*)(.*)");
            wordToAddToCommand = LiteralNumberWord.make();
            currentSentence = modifyCommandAndGetRemainingSentence(currentSentence, pattern, command, wordToAddToCommand);
            break;
          case 1:
            pattern = Pattern.compile("(\"[^\"]*\")(.*)");
            wordToAddToCommand = LiteralStringWord.make();
            currentSentence = modifyCommandAndGetRemainingSentence(currentSentence, pattern, command, wordToAddToCommand);
            break;
          case 2:
            pattern = Pattern.compile("(->*[^\s]+)(.*)");
            wordToAddToCommand = AssignmentWord.make();
            currentSentence = modifyCommandAndGetRemainingSentence(currentSentence, pattern, command, wordToAddToCommand);
            break;
          case 3:
            //OperatorWord
            break;
          case 4:
            //RecallWord
            break;
          default:
            System.out.println("word not recognized!");
        }
        currentSentence = currentSentence.stripLeading();
        i++;
      }
    }
    return command;
  }

  private String modifyCommandAndGetRemainingSentence(String currentSentence, Pattern pattern, VisurCommand command, Word wordToAddToCommand) {
    Matcher matcher = pattern.matcher(currentSentence);
    if (matcher.matches()) {
      Object wordValue = null;
      Operator operatorFromWord = wordToAddToCommand.toOperator();
      switch(operatorFromWord) {
        case literalNumberOperator -> wordValue = Integer.parseInt(matcher.group(1));
        case literalStringOperator -> wordValue = matcher.group(1);
        case assignmentWordOperator -> wordValue = EditorModelKey.valueOf(matcher.group(1).substring(2)); //chop off the ->
        default -> System.out.println("Word Operator " + wordToAddToCommand.toOperator() + " not recognized");
      }
      if(wordValue != null) {
        currentSentence = matcher.group(2);
        Object[] opAndOpInfo = new Object[]{operatorFromWord, wordValue};
        command.addOperatorWithInfo(opAndOpInfo);
        System.out.println("matched word = \"" + wordValue.toString() + "\", remaining = \"" + currentSentence + "\".");
      }
    }
    return currentSentence;
  }

}
