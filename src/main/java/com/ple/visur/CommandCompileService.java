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

    while(currentSentence != "") {
      CompiledWordResponse compiledWordResponse;

      LiteralNumberWord literalNumberWord = new LiteralNumberWord();
      LiteralStringWord literalStringWord = new LiteralStringWord();
      AssignmentWord assignmentWord = new AssignmentWord();
      NativeOperatorWord nativeOperatorWord = new NativeOperatorWord("");
      RecallWord recallWord = new RecallWord();
      Word[] words = new Word[] {
        literalNumberWord, literalStringWord, assignmentWord, nativeOperatorWord, recallWord
      };

      for(Word word : words) {
        compiledWordResponse = word.compile(currentSentence);
        currentSentence = compiledWordResponse.remainingSentence;
      }

    }
    return command;
  }


}
