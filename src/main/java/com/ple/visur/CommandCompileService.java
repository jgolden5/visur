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
          wordToAddToCommand = LiteralNumberWord.make();
          newSentence = modifyCommandAndGetRemainingSentence(currentSentence, pattern, command, wordToAddToCommand);
          break;
        case 1:
          pattern = Pattern.compile("(\"[^\"]*\")(.*)");
          wordToAddToCommand = LiteralStringWord.make();
          newSentence = modifyCommandAndGetRemainingSentence(currentSentence, pattern, command, wordToAddToCommand);
          break;
        case 2:
          pattern = Pattern.compile("(->*[^\s]+)(.*)");
          wordToAddToCommand = AssignmentWord.make();
          newSentence = modifyCommandAndGetRemainingSentence(currentSentence, pattern, command, wordToAddToCommand);
          break;
        case 3:
          pattern = Pattern.compile("([^\\s]+)(.*)");
          wordToAddToCommand = NativeOperatorWord.make();
          newSentence = modifyCommandAndGetRemainingSentence(currentSentence, pattern, command, wordToAddToCommand);
          break;
        case 4:
          pattern = Pattern.compile("([^\\s]+)(.*)");
          wordToAddToCommand = RecallWord.make();
          newSentence = modifyCommandAndGetRemainingSentence(currentSentence, pattern, command, wordToAddToCommand);
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

  private boolean operatorExistsForWord(String word) {
    for (Operator op : Operator.values()) {
      if (op.name().equals(word)) {
        return true;
      }
    }
    return false;
  }

  private boolean globalVarExistsForWord(String word) {
    VariableMap gvm = ServiceHolder.editorModelService.getGlobalVariableMap();
    return gvm.get(word) != null;
  }

  private String modifyCommandAndGetRemainingSentence(String currentSentence, Pattern pattern, VisurCommand command, Word wordToAddToCommand) {
    Matcher matcher = pattern.matcher(currentSentence);
    if (matcher.matches()) {
      Object wordValue = null;
      Operator operatorFromWord;
      operatorFromWord = wordToAddToCommand.toOperator();
      switch(operatorFromWord) {
        case literalNumberOperator:
          wordValue = Integer.parseInt(matcher.group(1));
          break;
        case literalStringOperator:
          wordValue = matcher.group(1);
          break;
        case assignmentOperator:
          wordValue = EditorModelKey.valueOf(matcher.group(1).substring(2)); //chop off the ->
          break;
        case nativeOperator:
          String currentWord = matcher.group(1);
          if(currentWord.equals("+")) {
            currentWord = "add";
          }
          if(operatorExistsForWord(currentWord)) {
            wordValue = Operator.valueOf(currentWord);
          }
          break;
        case recallOperator:
          if(globalVarExistsForWord(matcher.group(1))) {
            wordValue = matcher.group(1);
          }
          break;
        default:
          System.out.println("Word Operator " + wordToAddToCommand.toOperator() + " not recognized");
      }
      if(wordValue != null) {
        currentSentence = matcher.group(2);
        Object[] opAndOpInfo = new Object[]{operatorFromWord, wordValue};
        command.addOperatorWithInfo(opAndOpInfo);
        System.out.println("matched word = \"" + wordValue + "\", remaining = \"" + currentSentence + "\".");
      }
    }
    return currentSentence;
  }

}
