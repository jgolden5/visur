package com.ple.visur;

public class ModeSwitchService implements OperatorService {

  final EditorModelService ems = ServiceHolder.editorModelService;

  public static ModeSwitchService make() {
    return new ModeSwitchService();
  }


  private void enterEditingMode() { //Escape
    ems.putEditorMode(EditorMode.editing);
    System.out.println("entered editing mode");
  }

  private void enterInsertMode() { //i
    ems.putEditorMode(EditorMode.insert);
    System.out.println("entered insert mode");
  }

  @Override
  public void execute(Operator operator, Object... args) {
    switch(operator) {
      case enterEditingMode:
        enterEditingMode();
        break;
      case enterInsertMode:
        enterInsertMode();
        break;
      default:
        ems.reportError("Operator not recognized in " + this.getClass().getSimpleName());
    }

  }

}
