package com.ple.visur;

public class ModeSwitchService implements OperatorService {

  final EditorModelCoupler ems = ServiceHolder.editorModelCoupler;

  public static ModeSwitchService make() {
    return new ModeSwitchService();
  }


  private void enterEditingMode() { //Escape
    ems.putEditorMode(EditorMode.editing);
  }

  private void enterInsertMode() { //i
    ems.putEditorMode(EditorMode.insert);
  }

  @Override
  public void execute(Operator operator, Object... args) {
    if(args.length > 0) {
      throw new RuntimeException("input was of length " + args.length + ". Length should have been 0.");
    }
//    switch(operator) {
//      case enterEditingMode:
//        enterEditingMode();
//        break;
//      case enterInsertMode:
//        enterInsertMode();
//        break;
//      default:
//        ems.reportError("Operator not recognized in " + this.getClass().getSimpleName());
//    }

  }

}
