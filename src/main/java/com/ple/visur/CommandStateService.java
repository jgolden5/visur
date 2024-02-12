package com.ple.visur;

public class CommandStateService {

  public static CommandStateService make() {
    return new CommandStateService();
  }

  public void executeCommandState(KeyPressed keyPressed) {
    EditorModelService ems = ServiceHolder.editorModelService;
    int commandCursor = ems.getCommandCursor();
    String commandStateKeyPressed = keyPressed.getKey();
    if(commandStateKeyPressed.equals("Escape")) {
      exitCommandState();
    } else if(commandStateKeyPressed.equals("Enter")) {
      VisurCommand visurCommand = ServiceHolder.commandCompileService.compile(ems.getCommandStateContent());
      ServiceHolder.commandExecutionService.execute(visurCommand);
      exitCommandState();
    } else if(commandStateKeyPressed.equals("ArrowLeft") || commandStateKeyPressed.equals("ArrowRight")) {
      switch(commandStateKeyPressed) {
        case "ArrowLeft":
          if(commandCursor > 0) {
            commandCursor--;
            ems.putCommandCursor(commandCursor);
          }
          break;
        case "ArrowRight":
          if(commandCursor < ems.getCommandStateContent().length()) {
            commandCursor++;
            ems.putCommandCursor(commandCursor);
          }
          break;
        default:
          ems.reportError("unexpected error in switch statement for arrow movement in command state");
      }
    } else if(commandStateKeyPressed.equals("Backspace")) {
      String commandStateContent = ems.getCommandStateContent();
      if(commandCursor > 0) {
        String substrBeforeDeletedChar = commandStateContent.substring(0, commandCursor - 1);
        String substrAfterDeletedChar = commandStateContent.substring(commandCursor);
        String newCommandStateContent = substrBeforeDeletedChar + substrAfterDeletedChar;
        ems.putCommandStateContent(newCommandStateContent);
        ems.putCommandCursor(--commandCursor);
      }
    } else if(commandStateKeyPressed.length() == 1) {
      String oldCommandStateContent = ems.getCommandStateContent();
      char charToInsert = commandStateKeyPressed.charAt(0);
      String substrBeforeInsertedChar = oldCommandStateContent.substring(0, commandCursor);
      String substrAfterInsertedChar = oldCommandStateContent.substring(commandCursor);
      String newCommandStateContent = substrBeforeInsertedChar + charToInsert + substrAfterInsertedChar;

      ems.putCommandStateContent(newCommandStateContent);
      ems.putCommandCursor(commandCursor + 1);
    }
  }

  private void exitCommandState() {
    EditorModelService ems = ServiceHolder.editorModelService;
    ems.putIsInCommandState(false);
    ems.putCommandStateContent("");
    ems.putCommandCursor(0);
    System.out.println("Is out of command state");
  }

}
