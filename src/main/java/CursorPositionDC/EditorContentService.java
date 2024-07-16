package CursorPositionDC;

import com.ple.visur.*;
import io.vertx.rxjava3.core.shareddata.LocalMap;

import java.util.ArrayList;

import static com.ple.visur.EditorModelKey.*;

public class EditorContentService {

  public static EditorContentService make() {
    return new EditorContentService();
  }

  public String getEditorContent(LocalMap<EditorModelKey, Object> editorModel) {
    return (String)editorModel.get(editorContent);
  }

  public CursorPositionDCHolder getCursorPositionDCHolder(LocalMap<EditorModelKey, Object> editorModel) {
    return (CursorPositionDCHolder)editorModel.get(cursorPositionDCHolder);
  }

  public VisurVar getGlobalVar(String varName, LocalMap<EditorModelKey, Object> editorModel) {
    VariableMap globalVarMap = getGlobalVarMap(editorModel);
    return globalVarMap.get(varName);
  }

  public VariableMap getGlobalVarMap(LocalMap<EditorModelKey, Object> editorModel) {
    return (VariableMap) editorModel.get(globalVariableMap);
  }

  public String getCurrentContentLine(LocalMap<EditorModelKey, Object> editorModel) {
    BrickVisurVar cyBVV = (BrickVisurVar)getGlobalVar("cy", editorModel);
    int cy = (int)cyBVV.getVal();
    return getContentLineAtY(cy, editorModel);
  }

  public String getContentLineAtY(int y, LocalMap<EditorModelKey, Object> editorModel) {
    final String content = getEditorContent(editorModel);
    ArrayList<Integer> newlineIndices = getNewlineIndices(editorModel);
    String currentContentLine = content;
    if(newlineIndices.size() > 1) {
      int lastNewlineIndex = newlineIndices.get(newlineIndices.size() - 1);
      if (y < newlineIndices.size()) {
        if (y > 0) {
          currentContentLine = content.substring(newlineIndices.get(y - 1) + 1, newlineIndices.get(y));
        } else {
          currentContentLine = content.substring(0, newlineIndices.get(y));
        }
      } else if (content.length() > lastNewlineIndex + 1) {
        currentContentLine = content.substring(lastNewlineIndex + 1);
      } else {
        currentContentLine = null;
      }
    }
    return currentContentLine;
  }

  public int[] calcLongLineBoundaries(String editorContent, ArrayList<Integer> newlineIndices, int span, boolean includeTail, LocalMap<EditorModelKey, Object> editorModel) {
    LineQuantum lineQuantum = new LineQuantum();
    return lineQuantum.getBoundaries(editorContent, newlineIndices, span, false);
  }

  public ArrayList<Integer> getNewlineIndices(LocalMap<EditorModelKey, Object> editorModel) {
    BrickVisurVar niBVV = (BrickVisurVar)getGlobalVar("ni", editorModel);
    return (ArrayList<Integer>) niBVV.getVal();
  }

  public int getCanvasWidth(LocalMap<EditorModelKey, Object> editorModel) {
    BrickVisurVar cwBVV = (BrickVisurVar) getGlobalVar("cw", editorModel);
    return (int)cwBVV.getVal();
  }

  public int getCanvasHeight(LocalMap<EditorModelKey, Object> editorModel) {
    return (int) editorModel.get(canvasHeight);
  }

  public void initializeEditorContent(String contentLines, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(editorContent, contentLines);
  }


  public void putEditorContent(String contentLines, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(editorContent, contentLines);
    updateNewlineIndices(editorModel);
  }

  public void putCursorPositionDCHolder(CursorPositionDCHolder cpDCHolder, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(cursorPositionDCHolder, cpDCHolder);
  }

  public void putGlobalVar(String globalVarName, VisurVar globalVarValue, LocalMap<EditorModelKey, Object> editorModel) {
    VariableMap gvm = getGlobalVarMap(editorModel);
    gvm.put(globalVarName, globalVarValue); //updates value that was previously at associated key
    putGlobalVarMap(gvm, editorModel);
  }

  public void putGlobalVarMap(VariableMap gvm, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(globalVariableMap, gvm);
  }

  /**
   * call getEditorContent, and assign it to content var
   * make indices var equal to empty ArrayList
   * loop through every character in content to check if newline char exists
   * if char at index i == \n, then add i to indices var
   * make niBVV var, which is equal to the result of getGlobalVar("ni")
   * call niBVV.putVal(indices)
   * call putGlobalVar("ni", niBVV, editorModel)
   * @param editorModel
   */
  public void updateNewlineIndices(LocalMap<EditorModelKey, Object> editorModel) {
    String content = getEditorContent(editorModel);
    ArrayList<Integer> indices = new ArrayList<>();
    for(int i = 0; i < content.length(); i++) {
      if(content.charAt(i) == '\n') {
        indices.add(i);
      }
    }
    indices.add(content.length());
    BrickVisurVar niBVV = (BrickVisurVar) getGlobalVar("ni", editorModel);
    niBVV.putVal(indices);
    putGlobalVar("ni", niBVV, editorModel);
  }

  public void putVirtualCX(int x, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(virtualX, x);
    putVirtualXIsAtEndOfLine(false, editorModel);
  }

  public void putVirtualXIsAtEndOfLine(boolean isAtEndOfLine, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(virtualXIsAtEndOfLine, isAtEndOfLine);
  }

  public void putCanvasWidth(int width, LocalMap<EditorModelKey, Object> editorModel) {
    BrickVisurVar cwBVV = (BrickVisurVar) getGlobalVar("cw", editorModel);
    cwBVV.putVal(width);
    putGlobalVar("cw", cwBVV, editorModel);
  }

  public void putCanvasHeight(int height, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(canvasHeight, height);
  }

}
