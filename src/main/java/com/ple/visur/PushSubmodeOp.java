package com.ple.visur;

import java.util.Stack;

public class PushSubmodeOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String editorSubmodeAsString = (String)eds.pop();
    String editorSubmodeAsStringWithoutQuotes = editorSubmodeAsString.substring(1, editorSubmodeAsString.length() - 1);
    EditorSubmode editorSubmode = EditorSubmode.getSubmodeByString(editorSubmodeAsStringWithoutQuotes);
    if(editorSubmode != null) {
      if(editorSubmode ==  EditorSubmode.editing) {
        Stack<EditorSubmode> editorSubmodeStack = emc.getEditorSubmodeStack();
        while(editorSubmodeStack.size() > 1) {
          editorSubmodeStack.pop();
        }
        emc.putEditorSubmodeStack(editorSubmodeStack);
      } else {
        emc.putEditorSubmode(editorSubmode);
      }
    }
  }
}
