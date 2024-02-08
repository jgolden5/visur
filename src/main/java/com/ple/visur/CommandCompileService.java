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
      Matcher matcher;
      Object opInfo;
      switch (i) {
        case 0:
          pattern = Pattern.compile("^(-?[0-9]+\\.?[0-9]*)(.*)");
          op = LiteralNumberWord.make().toOperator();
          matcher = getMatcher(pattern, currentSentence);
          opInfo = Integer.parseInt(matcher.group(1));
          break;
        case 1:
          pattern = Pattern.compile("(\"[^\"]*\")(.*)");
          op = LiteralStringWord.make().toOperator();
          matcher = getMatcher(pattern, currentSentence);
          opInfo = matcher.group(1);
          break;
        case 2:
          pattern = Pattern.compile("(->*[^\s]+)(.*)");
          op = AssignmentWord.make().toOperator();
          matcher = getMatcher(pattern, currentSentence);
          opInfo = EditorModelKey.valueOf(matcher.group(1));
          break;
        case 3:
          pattern = Pattern.compile("([^\\s]+)(.*)");
          op = NativeOperatorWord.make().toOperator();
          matcher = getMatcher(pattern, currentSentence);
          if(isValidOperator) {
            opInfo = matcher.group(1);
          }
          break;
        case 4:
          pattern = Pattern.compile("([^\\s]+)(.*)");
          op = RecallWord.make().toOperator();
          matcher = getMatcher(pattern, currentSentence);
          if(varExistsInEditorModel) {
            opInfo = matcher.group(1);
          }
          break;
        default:
          System.out.println("word not recognized!");
          command = null;
          break compileLoop;
      }
      if(matcher == null) {
        i++;
      } else {
        i = 0;
        currentSentence = newSentence.stripLeading();
      }
    }
    return command;
  }

  private Matcher getMatcher(Pattern pattern, String currentSentence) {
    Matcher m = pattern.matcher(currentSentence);
    if(m.matches()) {
      return m;
    } else {
      return null;
    }
  }

}
