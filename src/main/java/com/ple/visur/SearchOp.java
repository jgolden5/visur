package com.ple.visur;

public class SearchOp implements Operator {
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
      System.out.println("search target = " + searchTarget);
      String editorContent = emc.getEditorContent();
      Quantum scopeQuantum = emc.getScopeQuantum();
      int[] scopeQuantumBounds = scopeQuantum.getBoundaries(editorContent, emc.getNewlineIndices(), 1, false); //span is always 1 when searching within a scopeQuantum
      int scopeQuantumEnd = scopeQuantumBounds[1];
      int ca = emc.getCA();
      String editorContentSubstringToSearch = editorContent.substring(ca, scopeQuantumEnd);
      int foundLocation = editorContentSubstringToSearch.indexOf(searchTarget);
      if(foundLocation > -1) {
        emc.putCA(foundLocation);
        Quantum cursorQuantum = emc.getCursorQuantum();
        int[] newBounds = cursorQuantum.getBoundaries(editorContent, emc.getNewlineIndices(), emc.getSpan(), false);
        emc.putCursorQuantumStart(newBounds[0]);
        emc.putCursorQuantumEnd(newBounds[1]);
      }
    }
  }
}
