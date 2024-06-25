package com.ple.visur;

public class SearchForwardOp implements Operator {
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
      System.out.println("forward search target = " + searchTarget);
      String editorContent = emc.getEditorContent();
      Quantum scopeQuantum = emc.getScopeQuantum();
      int[] scopeQuantumBounds = scopeQuantum.getBoundaries(editorContent, emc.getNewlineIndices(), 1, false); //span is always 1 when searching within a scopeQuantum
      int scopeQuantumEnd = scopeQuantumBounds[1];
      int ca = emc.getCA();
      if(ca < scopeQuantumEnd) {
        String editorContentSubstringToSearch = editorContent.substring(ca + 1, scopeQuantumEnd);
        int foundResult = editorContentSubstringToSearch.indexOf(searchTarget);
        if (foundResult > -1) {
          int foundIndex = foundResult + ca + 1;
          emc.putCA(foundIndex);
          emc.putVirtualCX(emc.getCX());
          Quantum cursorQuantum = emc.getCursorQuantum();
          int[] newBounds = cursorQuantum.getBoundaries(editorContent, emc.getNewlineIndices(), emc.getSpan(), false);
          emc.putCursorQuantumStart(newBounds[0]);
          emc.putCursorQuantumEnd(newBounds[1]);
        }
      }
    }
    emc.putPreviousSearchTarget(searchTarget);
    emc.putPreviousSearchDirectionWasForward(true);
  }
}
