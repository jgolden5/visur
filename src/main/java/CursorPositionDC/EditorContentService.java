package CursorPositionDC;

import DataClass.CompoundDataClassBrick;
import DataClass.DataClassBrick;
import DataClass.PrimitiveDataClassBrick;
import DataClass.Result;
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

  public int getCX(LocalMap<EditorModelKey, Object> editorModel) {
    VisurVar cxDCBAsVisurVar = getGlobalVar("cx", editorModel);
    PrimitiveDataClassBrick cxDCB = cxDCBAsVisurVar.getBrick();
    CursorPositionDCHolder cursorPositionDCHolder = getCursorPositionDCHolder(editorModel);
    Result<DataClassBrick> r = cxDCB.getOuter().getOrCalculateInner(cxDCB.name, cursorPositionDCHolder);
    int cx;
    if(r.getError() == null) {
      cxDCB = (PrimitiveDataClassBrick) r.getVal();
      cx = (int)cxDCB.getDFB().getVal();
    } else {
      cx = -1;
    }
    return cx;
  }

  public int getCY(LocalMap<EditorModelKey, Object> editorModel) {
    VisurVar cyDCBAsVisurVar = getGlobalVar("cy", editorModel);
    PrimitiveDataClassBrick cyDCB = cyDCBAsVisurVar.getBrick();
    CursorPositionDCHolder cursorPositionDCHolder = getCursorPositionDCHolder(editorModel);
    Result<DataClassBrick> r = cyDCB.getOuter().getOrCalculateInner(cyDCB.name, cursorPositionDCHolder);
    int cy;
    if(r.getError() == null) {
      cyDCB = (PrimitiveDataClassBrick) r.getVal();
      cy = (int)cyDCB.getDFB().getVal();
    } else {
      cy = -1;
    }
    return cy;
  }

  public int getCA(LocalMap<EditorModelKey, Object> editorModel) {
    VisurVar caDCBAsVisurVar = getGlobalVar("ca", editorModel);
    PrimitiveDataClassBrick caDCB = caDCBAsVisurVar.getBrick();
    CursorPositionDCHolder cursorPositionDCHolder = getCursorPositionDCHolder(editorModel);
    Result<DataClassBrick> r = caDCB.getOuter().getOrCalculateInner(caDCB.name, cursorPositionDCHolder);
    int ca;
    if(r.getError() == null) {
      caDCB = (PrimitiveDataClassBrick) r.getVal();
      ca = (int)caDCB.getDFB().getVal();
    } else {
      ca = -1;
    }
    return ca;
  }

  public VisurVar getGlobalVar(String varName, LocalMap<EditorModelKey, Object> editorModel) {
    VariableMap globalVarMap = getGlobalVarMap(editorModel);
    return globalVarMap.get(varName);
  }

  public VariableMap getGlobalVarMap(LocalMap<EditorModelKey, Object> editorModel) {
    return (VariableMap) editorModel.get(globalVariableMap);
  }

  public String getCurrentContentLine(LocalMap<EditorModelKey, Object> editorModel) {
    int cy = getCY(editorModel);
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

  public void putCursorPositionDCHolder(CursorPositionDCHolder cpDCHolder, LocalMap<EditorModelKey, Object> editorModel) {
    editorModel.put(cursorPositionDCHolder, cpDCHolder);
  }

  //note, do not use these to set initial values for cx and cy in initializerService because outers must already be set
  public void putCX(int cx, LocalMap<EditorModelKey, Object> editorModel) {
    CursorPositionDCHolder cursorPositionDCHolder = getCursorPositionDCHolder(editorModel);
    VisurVar cxDCBAsVisurVar = getGlobalVar("cx", editorModel);
    PrimitiveDataClassBrick cxDCB = cxDCBAsVisurVar.getBrick();
    CompoundDataClassBrick outer = cxDCB.getOuter();
    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cx, outer, cursorPositionDCHolder);
    Result r = outer.putInner("cx", cxDCB);
    if(r.getError() == null) {
      cxDCBAsVisurVar = VisurVar.make(cxDCB);
      putGlobalVar("cx", cxDCBAsVisurVar, editorModel);
    } else {
      putGlobalVar("cx", null, editorModel);
    }
  }

  public void putCY(int cy, LocalMap<EditorModelKey, Object> editorModel) {
    CursorPositionDCHolder cursorPositionDCHolder = getCursorPositionDCHolder(editorModel);
    VisurVar cyDCBAsVisurVar = getGlobalVar("cy", editorModel);
    PrimitiveDataClassBrick cyDCB = cyDCBAsVisurVar.getBrick();
    CompoundDataClassBrick outer = cyDCB.getOuter();
    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(cy, outer, cursorPositionDCHolder);
    Result r = outer.putInner("cy", cyDCB);
    if(r.getError() == null) {
      cyDCBAsVisurVar = VisurVar.make(cyDCB);
      putGlobalVar("cy", cyDCBAsVisurVar, editorModel);
    } else {
      putGlobalVar("cy", null, editorModel);
    }
  }

  public void putCA(int ca, LocalMap<EditorModelKey, Object> editorModel) {
    CursorPositionDCHolder cursorPositionDCHolder = getCursorPositionDCHolder(editorModel);
    VisurVar caDCBAsVisurVar = getGlobalVar("ca", editorModel);
    PrimitiveDataClassBrick caDCB = caDCBAsVisurVar.getBrick();
    CompoundDataClassBrick outer = caDCB.getOuter();
    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick(ca, outer, cursorPositionDCHolder);
    Result r = outer.putInner("ca", caDCB);
    if(r.getError() == null) {
      caDCBAsVisurVar = VisurVar.make(caDCB);
      putGlobalVar("ca", caDCBAsVisurVar, editorModel);
    } else {
      putGlobalVar("ca", null, editorModel);
    }
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
