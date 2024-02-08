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
      Object opInfo = null;
      switch (i) {
        case 0:
          pattern = Pattern.compile("^(-?[0-9]+\\.?[0-9]*)(.*)");
          matcher = getMatcher(pattern, currentSentence);
          opInfo = Integer.parseInt(matcher.group(1));
          op = LiteralNumberWord.make().toOperator();
          break;
        case 1:
          pattern = Pattern.compile("(\"[^\"]*\")(.*)");
          matcher = getMatcher(pattern, currentSentence);
          opInfo = matcher.group(1);
          op = LiteralStringWord.make().toOperator();
          break;
        case 2:
          pattern = Pattern.compile("(->*[^\s]+)(.*)");
          matcher = getMatcher(pattern, currentSentence);
          opInfo = EditorModelKey.valueOf(matcher.group(1));
          op = AssignmentWord.make().toOperator();
          break;
        case 3:
          pattern = Pattern.compile("([^\\s]+)(.*)");
          matcher = getMatcher(pattern, currentSentence);
          opInfo = matcher.group(1);
          op = NativeOperatorWord.make((String)opInfo).toOperator();
          break;
        case 4:
          pattern = Pattern.compile("([^\\s]+)(.*)");
          matcher = getMatcher(pattern, currentSentence);
          if(varExistsInEditorModel(matcher.group(1))) {
            opInfo = matcher.group(1);
          }
          op = RecallWord.make().toOperator();
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
        currentSentence = matcher.group(2).stripLeading();
        command.ops.add(op);
        command.opInfo.add(opInfo);
      }
    }
    return command;
  }

  private boolean varExistsInEditorModel(String varName) {
    for(EditorModelKey k : EditorModelKey.values()) {
      if(k.getClass().getSimpleName().equals(varName)) {
        return true;
      }
    }
    return false;
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
