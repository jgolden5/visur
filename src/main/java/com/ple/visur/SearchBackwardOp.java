package com.ple.visur;

public class SearchBackwardOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    ExecutionDataStack eds = emc.getExecutionDataStack();
    String searchTarget = "";
    if (eds.size() > 0) {
      searchTarget = (String) eds.pop();
    }
    if (searchTarget.equals("")) {
      System.out.println("No search target found");
    } else {
      System.out.println("backward search target = " + searchTarget);
      String editorContent = emc.getEditorContent();
      Quantum scopeQuantum = emc.getScopeQuantum();
      int[] scopeQuantumBounds = scopeQuantum.getBoundaries(editorContent, emc.getNewlineIndices(), 1, false); //span is always 1 when searching within a scopeQuantum
      int scopeQuantumStart = scopeQuantumBounds[0];
      int cursorQuantumStart = emc.getCursorQuantumStart();
      if(cursorQuantumStart > scopeQuantumStart) {
        String editorContentSubstringToSearch = editorContent.substring(scopeQuantumStart, cursorQuantumStart);
        int foundResult = editorContentSubstringToSearch.lastIndexOf(searchTarget);
        if (foundResult > -1) {
          int foundIndex = scopeQuantumStart + foundResult; //this is not necessary if the two are truly equal. This is temp for debugging
          emc.putCA(foundIndex);
          emc.putVirtualCX(emc.getLongCX());
          Quantum cursorQuantum = emc.getCursorQuantum();
          int[] newBounds = cursorQuantum.getBoundaries(editorContent, emc.getNewlineIndices(), emc.getSpan(), false);
          emc.putCursorQuantumStart(newBounds[0]);
          emc.putCursorQuantumEnd(newBounds[1]);
        }
      }
    }
  }
}
