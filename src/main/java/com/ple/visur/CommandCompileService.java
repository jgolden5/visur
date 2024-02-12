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

    LiteralNumberWord literalNumberWord = new LiteralNumberWord();
    LiteralStringWord literalStringWord = new LiteralStringWord();
    AssignmentWord assignmentWord = new AssignmentWord();
    NativeOperatorWord nativeOperatorWord = new NativeOperatorWord();
    RecallWord recallWord = new RecallWord();
    Word[] words = new Word[] {
      literalNumberWord, literalStringWord, assignmentWord, nativeOperatorWord, recallWord
    };

    compileLoop:
    while(currentSentence != "") {
      CompiledWordResponse compiledWordResponse;
      for(Word word : words) {
        compiledWordResponse = word.compile(currentSentence);
        if(compiledWordResponse != null) {
          command.ops.add(compiledWordResponse.op);
          command.opInfo.add(compiledWordResponse.opInfo);
          currentSentence = compiledWordResponse.remainingSentence.stripLeading();
          break;
        }
        if(word.getClass().getSimpleName().equals("RecallWord")) {
          break compileLoop;
        }
      }

    }
    return command;
  }


}
