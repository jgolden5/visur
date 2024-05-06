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

  public VisurVar getGlobalVar(String varName, LocalMap<EditorModelKey, Object> editorModel) {
    VariableMap globalVarMap = getGlobalVarMap(editorModel);
    return globalVarMap.get(varName);
  }

  public VariableMap getGlobalVarMap(LocalMap<EditorModelKey, Object> editorModel) {
    return (VariableMap) editorModel.get(globalVariableMap);
  }

  public String getCurrentContentLine(LocalMap<EditorModelKey, Object> editorModel) {
    int cy = getGlobalVar("cy", editorModel).getInt();
    return getContentLineAtY(cy, editorModel);
  }

  public String getContentLineAtY(int y, LocalMap<EditorModelKey, Object> editorModel) {
    final String content = getEditorContent(editorModel);
    ArrayList<Integer> newlineIndices = getNewlineIndices(editorModel);
    final String currentContentLine;
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
    return currentContentLine;
  }

  public ArrayList<Integer> getNewlineIndices(LocalMap<EditorModelKey, Object> editorModel) {
    //note that this must be re-updated every time editorContent is changed
    return (ArrayList<Integer>) editorModel.get(newlineIndices);
  }

  public int getVirtualX(LocalMap<EditorModelKey, Object> editorModel) {
    return (int) editorModel.get(virtualX);
  }

  public boolean getVirtualXIsAtEndOfLine(LocalMap<EditorModelKey, Object> editorModel) {
    return (boolean) editorModel.get(virtualXIsAtEndOfLine);
  }

  public int getCanvasWidth(LocalMap<EditorModelKey, Object> editorModel) {
    return (int) editorModel.get(canvasWidth);
  }

  public int getCanvasHeight(LocalMap<EditorModelKey, Object> editorModel) {
    return (int) editorModel.get(canvasHeight);
  }

  public void putEditorContent(String contentLines, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(editorContent, contentLines);
    updateNewlineIndices(editorModel);
  }

  public void putGlobalVar(String globalVarName, VisurVar globalVarValue, LocalMap<EditorModelKey, Object> editorModel) {
    VariableMap gvm = getGlobalVarMap(editorModel);
    gvm.put(globalVarName, globalVarValue); //updates value that was previously at associated key
    putGlobalVarMap(gvm, editorModel);
  }

  public void putGlobalVarMap(VariableMap gvm, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(globalVariableMap, gvm);
  }

  public void updateNewlineIndices(LocalMap<EditorModelKey, Object> editorModel) {
    String content = getEditorContent(editorModel);
    ArrayList<Integer> indices = new ArrayList<>();
    boolean keepGoing = true;
    int fullStringIndex = 0;
    while (keepGoing) {
      int substringIndex = content.indexOf("\n");
      if (substringIndex != -1) {
        fullStringIndex += substringIndex;
        indices.add(fullStringIndex);
        content = content.substring(substringIndex + 1);
        fullStringIndex++; //because of newline char
      } else {
        keepGoing = false;
      }
    }
    editorModel.put(newlineIndices, indices);
  }

  public void putVirtualX(int x, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(virtualX, x);
    putVirtualXIsAtEndOfLine(false, editorModel);
  }

  public void putVirtualXIsAtEndOfLine(boolean isAtEndOfLine, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(virtualXIsAtEndOfLine, isAtEndOfLine);
  }

  public void putCanvasWidth(int width, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(canvasWidth, width);
  }

  public void putCanvasHeight(int height, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(canvasHeight, height);
  }

}
