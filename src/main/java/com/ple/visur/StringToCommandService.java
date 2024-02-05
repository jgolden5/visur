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
    boolean sentenceCanStillBeCompiled = true;

    while(sentenceCanStillBeCompiled) {
      int i = 0;
      while (i < 5) {
        Matcher matcher;
        Word wordToAddToCommand = null;
        switch (i) {
          case 0:
            Pattern pattern = Pattern.compile("^(-?[0-9]+\\.?[0-9]*)(.*)");
            matcher = pattern.matcher(currentSentence);
            if (matcher.matches()) {
              int value = Integer.parseInt(matcher.group(1));
              currentSentence = matcher.group(2);
              wordToAddToCommand = LiteralNumberWord.make();
              Object[] opAndOpInfo = new Object[]{wordToAddToCommand.toOperator(), value};
              command.addOperatorWithInfo(opAndOpInfo);
            }
            break;
          case 1:
            //LiteralStringWord
            break;
          case 2:
            //AssignmentWord
            break;
          case 3:
            //OperatorWord
            break;
          case 4:
            //RecallWord
            break;
          default:
            System.out.println("iterator id not recognized");
        }
        i++;
      }
      sentenceCanStillBeCompiled = false;
    }
    return command;
  }

}
