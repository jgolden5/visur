package com.ple.visur;

public class CommandService {

  public static CommandService make() {
    return new CommandService();
  }

  public void handleSentence() {
    EditorModelService ems = ServiceHolder.editorModelService;
    String commandStateContent = ems.getCommandStateContent();
    String[] sentence = commandStateContent.split(" ");
    for(String word : sentence) {
      if(word.contains("=")) {
        String[] assignmentArray = word.split("=");
        String key = assignmentArray[0];
        String val = assignmentArray[1];
        if(isValidVarToAssign(key, val)) {
          VisurVar visurVar = ems.getGlobalVar(key);
          visurVar.put(val);
        } else {
          ems.reportError("assignment variable " + key + " = " + val + " in command state is not valid");
        }
      } else {
        if(isValidOperator(word)) {
          Operator wordOperator = Operator.valueOf(word);
          OperatorToService operatorToService = OperatorToService.make();
          OperatorService operatorService = operatorToService.get(wordOperator);
          operatorService.execute(wordOperator);
        } else {
          ems.reportError("operator in command state is not valid");
        }
      }
    }
  }

  private boolean isValidVarToAssign(String key, String val) {
    boolean keyIsValid = false;
    String[] validVars = new String[]{"contentX", "contentY"};
    for(int i = 0; i < validVars.length; i++) {
      if(validVars[i].equals(key)) {
        keyIsValid = true;
        break;
      }
    }
    boolean valIsValid = false;
    EditorModelService ems = ServiceHolder.editorModelService;
    switch(key) {
      case "contentX":
        int contentX = Integer.parseInt(val);
        valIsValid = contentX >= 0 && contentX <= ems.getCurrentContentLineLength();
        ems.putVirtualX(contentX); //for now
        break;
      case "contentY":
        int contentY = Integer.parseInt(val);
        valIsValid = contentY >= 0 && contentY < ems.getEditorContentLines().length;
        break;
      default:
        ems.reportError("value type not recognized for command assignment");
    }
    return keyIsValid && valIsValid;
  }

  private boolean isValidOperator(String opToTest) {
    boolean opIsValid = false;
    for(Operator op : Operator.values()) {
      if(op.name().equals(opToTest)) {
        opIsValid = true;
      }
    }
    return opIsValid;
  }

}
