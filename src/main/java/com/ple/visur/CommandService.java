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
        String varToAssignAsString = assignmentArray[0];
        if(isValidVarToAssign(varToAssignAsString)) {
          EditorModelKey varNameToAssign = EditorModelKey.valueOf(varToAssignAsString);
          ServiceHolder.editorModelService.editorModel.put(varNameToAssign, (Object)assignmentArray[1]);
        } else {
          ems.reportError("assignment variable in command state is not valid");
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

  private boolean isValidVarToAssign(String varToTest) {
    boolean varIsValid = false;
    for(EditorModelKey v : EditorModelKey.values()) {
      if(v.name().equals(varToTest)) {
        varIsValid = true;
      }
    }
    return varIsValid;
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
