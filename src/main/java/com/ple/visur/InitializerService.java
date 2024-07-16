package com.ple.visur;

import CursorPositionDC.*;
import DataClass.CompoundDataClass;
import DataClass.CompoundDataClassBrick;
import DataClass.PrimitiveDataClass;
import DataClass.PrimitiveDataClassBrick;

import java.util.HashMap;
import java.util.Stack;

import static com.ple.visur.EditorMode.navigate;
import static com.ple.visur.EditorModelKey.globalVariableMap;

public class InitializerService {

  private final EditorModelCoupler emc;

  public static InitializerService make(EditorModelCoupler editorModelCoupler) {
    return new InitializerService(editorModelCoupler);
  }

  private InitializerService(EditorModelCoupler editorModelCoupler) {
    this.emc = editorModelCoupler;
    initializeEditorModel();
  }

  public void initializeEditorModel() {
    VariableMap initialGvm = new VariableMap(new HashMap<>());
    emc.editorModel.put(globalVariableMap, initialGvm);

    emc.putEditorMode(navigate);

    Stack<EditorSubmode> editorSubmodeStack = new Stack<>();
    editorSubmodeStack.push(EditorSubmode.navigate);
    emc.putEditorSubmodeStack(editorSubmodeStack);

    emc.putExecutionDataStack(new ExecutionDataStack());

//    final String initialEditorContent = "Hello world\n" +
//      "How are you?\n" +
//      "Goodbye";
    final String initialEditorContent = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\nbbb";
//    final String initialEditorContent = "Vehumet is a god of the destructive powers of magic.\n" +
//      "Followers will gain divine assistance in commanding the hermetic arts, and the most favoured stand to gain access to some of the fearsome spells in Vehumet's library.\n" +
//      "One's devotion to Vehumet can be proven by the causing of as much carnage and destruction as possible.\n" +
//      "Worshippers of Vehumet will quickly be able to recover their magical energy upon killing beings.\n" +
//      "As they gain favour, they will also gain enhancements to their destructive spells — first assistance in casting such spells and then increased range for conjurations.\n" +
//      "Vehumet will offer followers the knowledge of increasingly powerful destructive spells as they gain piety.\n" +
//      "Whether \"having spells as a god\" is a good thing is up to you, but it does come with a few downsides. Without active abilities, you have less ways to deal with a dangerous situation. In addition, Vehumet is one of the weaker gods for the early game. For spellcaster backgrounds, the first few spell gifts generally won't be much of an improvement compared to your own spells. Characters new to spellcasting have to take time to train up magic, which might not be all that powerful by the time you hit 1* or even 3*. Furthermore, there are ways to get an \"engine\" without taking up the god slot.";

    initializeCursorPositionDCBs();

    emc.initializeEditorContent(initialEditorContent);
    emc.updateNewlineIndices();
    emc.putLineWrapping(LineWrapping.wrapped);
    BrickVisurVar realCABVV = (BrickVisurVar) emc.getGlobalVar("realCA");
    BrickVisurVar virtualLongCXBVV = (BrickVisurVar) emc.getGlobalVar("virtualLongCX");
    BrickVisurVar virtualLongCYBVV = (BrickVisurVar) emc.getGlobalVar("virtualLongCY");
    realCABVV.putVal(0);
    virtualLongCXBVV.putVal(0);
    virtualLongCYBVV.putVal(0);
    emc.putGlobalVar("realCA", realCABVV);
    emc.putGlobalVar("virtualLongCX", virtualLongCXBVV);
    emc.putGlobalVar("virtualLongCY", virtualLongCYBVV);
    emc.putIsInCommandState(false);
    emc.putCommandStateContent("");
    emc.putCommandCursor(emc.getCommandStateContent().length());
    emc.putPreviousSearchTarget("");
    if(emc.getEditorContent().length() > 0) {
      emc.putSpan(1);
    } else {
      emc.putSpan(0);
    }

    initializeQuantums();

    initializeKeymaps();

  }

  private void initializeCursorPositionDCBs() {
    CursorPositionDC cursorPositionDC = CursorPositionDCHolder.make().cursorPositionDC;
    CompoundDataClassBrick cursorPositionDCB = cursorPositionDC.makeBrick();
    WholeNumberListDC niDC = (WholeNumberListDC) cursorPositionDC.getInner("ni");
    WholeNumberDC wholeNumberDC = (WholeNumberDC) cursorPositionDC.getInner("wholeNumber");
    CoordinatesDC coordinatesDC = (CoordinatesDC) cursorPositionDC.getInner("coordinates");

    PrimitiveDataClassBrick niDCB = (PrimitiveDataClassBrick) niDC.makeBrick("ni", cursorPositionDCB);
    PrimitiveDataClassBrick cwDCB = (PrimitiveDataClassBrick) wholeNumberDC.makeBrick("cw", cursorPositionDCB);

    initializeRealCursorPositionDCB(cursorPositionDC, coordinatesDC, niDCB, cwDCB);
    initializeVirtualCursorPositionDCB(cursorPositionDC, coordinatesDC, niDCB, cwDCB);
  }

  private void initializeRealCursorPositionDCB(CursorPositionDC cursorPositionDC, CoordinatesDC coordinatesDC, PrimitiveDataClassBrick niDCB, PrimitiveDataClassBrick cwDCB) {
    CompoundDataClassBrick realCursorPositionDCB = cursorPositionDC.makeBrick("realCursorPosition", null);
    CompoundDataClassBrick coordinatesDCB = coordinatesDC.makeBrick("coordinates", realCursorPositionDCB);
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("longCXCY");
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) coordinatesDCB.getInner("ca");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCX");
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCY");
    longCXCYDCB.putInner("longCX", longCXDCB);
    longCXCYDCB.putInner("longCY", longCYDCB);
    shortCXCYDCB.putInner("shortCX", shortCXDCB);
    shortCXCYDCB.putInner("shortCY", shortCYDCB);
    coordinatesDCB.putInner("ca", caDCB);
    coordinatesDCB.putInner("longCXCY", longCXCYDCB);
    realCursorPositionDCB.putInner("ni", niDCB);
    realCursorPositionDCB.putInner("cw", cwDCB);
    realCursorPositionDCB.putInner("coordinates", coordinatesDCB);

    BrickVisurVar caDCBVV = BrickVisurVar.make(caDCB);
    BrickVisurVar longCXDCBVV = BrickVisurVar.make(longCXDCB);
    BrickVisurVar longCYDCBVV = BrickVisurVar.make(longCYDCB);
    BrickVisurVar shortCXDCBVV = BrickVisurVar.make(shortCXDCB);
    BrickVisurVar shortCYDCBVV = BrickVisurVar.make(shortCYDCB);
    BrickVisurVar niBVV = BrickVisurVar.make(niDCB);
    BrickVisurVar cwBVV = BrickVisurVar.make(cwDCB);

    emc.putGlobalVar("realCA", caDCBVV);
    emc.putGlobalVar("realLongCX", longCXDCBVV);
    emc.putGlobalVar("realLongCY", longCYDCBVV);
    emc.putGlobalVar("realShortCX", shortCXDCBVV);
    emc.putGlobalVar("realShortCY", shortCYDCBVV);
    emc.putGlobalVar("ni", niBVV);
    emc.putGlobalVar("cw", cwBVV);

  }

  private void initializeVirtualCursorPositionDCB(CursorPositionDC cursorPositionDC, CoordinatesDC coordinatesDC, PrimitiveDataClassBrick niDCB, PrimitiveDataClassBrick cwDCB) {
    CompoundDataClassBrick virtualCursorPositionDCB = cursorPositionDC.makeBrick("virtualCursorPosition", null);
    CompoundDataClassBrick coordinatesDCB = coordinatesDC.makeBrick("coordinates", virtualCursorPositionDCB);
    CompoundDataClassBrick longCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("longCXCY");
    CompoundDataClassBrick shortCXCYDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("shortCXCY");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) coordinatesDCB.getInner("ca");
    PrimitiveDataClassBrick longCXDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCX");
    PrimitiveDataClassBrick longCYDCB = (PrimitiveDataClassBrick) longCXCYDCB.getInner("longCY");
    PrimitiveDataClassBrick shortCXDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCX");
    PrimitiveDataClassBrick shortCYDCB = (PrimitiveDataClassBrick) shortCXCYDCB.getInner("shortCY");
    longCXCYDCB.putInner("longCX", longCXDCB);
    longCXCYDCB.putInner("longCY", longCYDCB);
    shortCXCYDCB.putInner("shortCX", shortCXDCB);
    shortCXCYDCB.putInner("shortCY", shortCYDCB);
    coordinatesDCB.putInner("ca", caDCB);
    coordinatesDCB.putInner("longCXCY", longCXCYDCB);
    virtualCursorPositionDCB.putInner("ni", niDCB);
    virtualCursorPositionDCB.putInner("cw", cwDCB);
    virtualCursorPositionDCB.putInner("coordinates", coordinatesDCB);

    BrickVisurVar caDCBVV = BrickVisurVar.make(caDCB);
    BrickVisurVar longCXDCBVV = BrickVisurVar.make(longCXDCB);
    BrickVisurVar longCYDCBVV = BrickVisurVar.make(longCYDCB);
    BrickVisurVar shortCXDCBVV = BrickVisurVar.make(shortCXDCB);
    BrickVisurVar shortCYDCBVV = BrickVisurVar.make(shortCYDCB);

    emc.putGlobalVar("virtualCA", caDCBVV);
    emc.putGlobalVar("virtualLongCX", longCXDCBVV);
    emc.putGlobalVar("virtualLongCY", longCYDCBVV);
    emc.putGlobalVar("virtualShortCX", shortCXDCBVV);
    emc.putGlobalVar("virtualShortCY", shortCYDCBVV);

  }

  private void initializeQuantums() {
    QuantumNameToQuantum quantumNameToQuantum = new QuantumNameToQuantum();
    String startingCursorQuantumName = "character";
    String startingScopeQuantumName = "line";
    quantumNameToQuantum.put("word", new RegexQuantum("word", "\\S+"));
    quantumNameToQuantum.put("character", new CharacterQuantum());
    quantumNameToQuantum.put("line", new LineQuantum());
    quantumNameToQuantum.put("document", new DocumentQuantum());
    emc.putQuantumNameToQuantum(quantumNameToQuantum);

    KeyToQuantumName keyToQuantumName = new KeyToQuantumName();
    keyToQuantumName.put("q", "character");
    keyToQuantumName.put("w", "word");
    keyToQuantumName.put("e", "line");
    keyToQuantumName.put("r", "document");
    emc.putKeyToQuantumName(keyToQuantumName);

    emc.putCursorQuantum(emc.getQuantumNameToQuantum().get(startingCursorQuantumName));
    emc.putScopeQuantum(emc.getQuantumNameToQuantum().get(startingScopeQuantumName));
    int bounds[] = emc.getQuantumNameToQuantum().get(startingCursorQuantumName).getBoundaries(emc.getEditorContent(), emc.getNewlineIndices(), emc.getSpan(), false);
    emc.putCursorQuantumStart(bounds[0]);
    emc.putCursorQuantumEnd(bounds[1]);
    emc.putIsAtQuantumStart(true);
    System.out.println("start bound = " + bounds[0]);
    System.out.println("end bound = " + bounds[1]);
  }

  private void initializeKeymaps() {
    KeymapMap keymapMap = KeymapMap.make();

    Keymap navigateKeymap = Keymap.make("navigate");
    navigateKeymap = initializeNavigateKeymap(navigateKeymap);
    keymapMap.put(EditorSubmode.navigate, navigateKeymap);

    Keymap insertKeymap = Keymap.make("insert");
    insertKeymap = initializeInsertKeymap(insertKeymap);
    keymapMap.put(EditorSubmode.insert, insertKeymap);

    Keymap replaceKeymap = Keymap.make("replace");
    replaceKeymap = initializeReplaceKeymap(replaceKeymap);
    keymapMap.put(EditorSubmode.replace, replaceKeymap);

    Keymap quantumStartKeymap = Keymap.make("quantumStart");
    quantumStartKeymap = initializeQuantumStartKeymap(quantumStartKeymap);
    keymapMap.put(EditorSubmode.quantumStart, quantumStartKeymap);

    Keymap quantumEndKeymap = Keymap.make("quantumEnd");
    quantumEndKeymap = initializeQuantumEndKeymap(quantumEndKeymap);
    keymapMap.put(EditorSubmode.quantumEnd, quantumEndKeymap);

    Keymap spanKeymap = Keymap.make("span");
    spanKeymap = initializeSpanKeymap(spanKeymap);
    keymapMap.put(EditorSubmode.span, spanKeymap);

    Keymap searchForwardKeymap = Keymap.make("searchForward");
    searchForwardKeymap = initializeSearchForwardKeymap(searchForwardKeymap);
    keymapMap.put(EditorSubmode.searchForward, searchForwardKeymap);

    Keymap searchBackwardKeymap = Keymap.make("searchBackward");
    searchBackwardKeymap = initializeSearchBackwardKeymap(searchBackwardKeymap);
    keymapMap.put(EditorSubmode.searchBackward, searchBackwardKeymap);

    Keymap changeScopeKeymap = Keymap.make("changeScope");
    changeScopeKeymap = initializeChangeScopeKeymap(changeScopeKeymap);
    keymapMap.put(EditorSubmode.changeScope, changeScopeKeymap);

    emc.putKeymapMap(keymapMap);

  }

  private Keymap initializeNavigateKeymap(Keymap keymap) {
    CommandCompileService scs = ServiceHolder.commandCompileService;
    keymap.put(KeyPressed.from("h"),
      scs.compile("-1 0 relativeMove")
    );
    keymap.put(KeyPressed.from("l"),
      scs.compile("1 0 relativeMove")
    );
    keymap.put(KeyPressed.from("j"),
      scs.compile("0 1 relativeMove")
    );
    keymap.put(KeyPressed.from("k"),
      scs.compile("0 -1 relativeMove")
    );
    keymap.put(KeyPressed.from("ArrowLeft"),
      scs.compile("-1 0 relativeMove")
    );
    keymap.put(KeyPressed.from("ArrowRight"),
      scs.compile("1 0 relativeMove")
    );
    keymap.put(KeyPressed.from("ArrowDown"),
      scs.compile("0 1 relativeMove")
    );
    keymap.put(KeyPressed.from("ArrowUp"),
      scs.compile("0 -1 relativeMove")
    );
    keymap.put(KeyPressed.from("["),
      scs.compile("\"quantumStart\" pushSubmode")
    );
    keymap.put(KeyPressed.from("]"),
      scs.compile("\"quantumEnd\" pushSubmode")
    );
    keymap.put(KeyPressed.from("s"),
      scs.compile("\"span\" pushSubmode")
    );
    keymap.put(KeyPressed.from("i"),
      scs.compile("\"span\" \"tempSpan\" -> \"cursorQuantum\" \"tempCursorQuantum\" -> \"insert\" changeMode 0 setSpan \"character\" changeCursorQuantum")
    );
    keymap.put(KeyPressed.from("a"),
      scs.compile("\"span\" \"tempSpan\" -> \"cursorQuantum\" \"tempCursorQuantum\" -> \"insert\" changeMode 0 setSpan 1 0 relativeMove \"character\" changeCursorQuantum")
    );
    keymap.put(KeyPressed.from("o"),
      scs.compile("\"span\" \"tempSpan\" -> \"cursorQuantum\" \"tempCursorQuantum\" -> \"replace\" changeMode 0 setSpan \"character\" changeCursorQuantum")
    );
    keymap.put(KeyPressed.from("d"),
      scs.compile("deleteCursorQuantum")
    );
    keymap.put(KeyPressed.from("f"),
      scs.compile("clearEDS \"searchForward\" pushSubmode")
    );
    keymap.put(KeyPressed.from("F"),
      scs.compile("clearEDS \"searchBackward\" pushSubmode")
    );
    keymap.put(KeyPressed.from("n"),
      scs.compile("previousSearchTarget searchInPreviousDirection")
    );
    keymap.put(KeyPressed.from("N"),
      scs.compile("previousSearchTarget searchNotInPreviousDirection")
    );
    keymap.put(KeyPressed.from("/"),
      scs.compile("\"changeScope\" pushSubmode")
    );
    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = ChangeCursorQuantumHandler.make();
    keymap.putHandlers(handlers);
    return keymap;
  }

  private Keymap initializeQuantumStartKeymap(Keymap quantumStartKeymap) {
    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = QuantumStartSubmodeHandler.make();
    quantumStartKeymap.putHandlers(handlers);
    return quantumStartKeymap;
  }

  private Keymap initializeQuantumEndKeymap(Keymap quantumEndKeymap) {
    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = QuantumEndSubmodeHandler.make();
    quantumEndKeymap.putHandlers(handlers);
    return quantumEndKeymap;
  }

  private Keymap initializeSpanKeymap(Keymap spanKeymap) {
    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = SpanSubmodeHandler.make();
    spanKeymap.putHandlers(handlers);
    return spanKeymap;
  }

  private Keymap initializeSearchForwardKeymap(Keymap searchForwardKeymap) {
    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = SearchForwardSubmodeHandler.make();
    searchForwardKeymap.putHandlers(handlers);
    return searchForwardKeymap;
  }

  private Keymap initializeSearchBackwardKeymap(Keymap searchBackwardKeymap) {
    final KeymapHandler[] handlers = new KeymapHandler[1];
    handlers[0] = SearchBackwardSubmodeHandler.make();
    searchBackwardKeymap.putHandlers(handlers);
    return searchBackwardKeymap;
  }

  private Keymap initializeInsertKeymap(Keymap insertKeymap) {
    CommandCompileService scs = ServiceHolder.commandCompileService;
    insertKeymap.put(KeyPressed.from("Escape"),
      scs.compile("\"tempSpan\" \"span\" -> \"tempCursorQuantum\" changeCursorQuantum \"tempSpan\" removeGlobalVar \"tempCursorQuantum\" removeGlobalVar \"navigate\" changeMode")
    );
    insertKeymap.put(KeyPressed.from("ArrowLeft"),
      scs.compile("-1 0 relativeMove")
    );
    insertKeymap.put(KeyPressed.from("ArrowRight"),
      scs.compile("1 0 relativeMove")
    );
    insertKeymap.put(KeyPressed.from("ArrowUp"),
      scs.compile("0 -1 relativeMove")
    );
    insertKeymap.put(KeyPressed.from("ArrowDown"),
      scs.compile("0 1 relativeMove")
    );
    insertKeymap.put(KeyPressed.from("Backspace"),
      scs.compile("deletePreviousChar -1 0 relativeMove")
    );
    final KeymapHandler[] insertKeymapHandlers = new KeymapHandler[1];
    insertKeymapHandlers[0] = InsertModeHandler.make();
    insertKeymap.putHandlers(insertKeymapHandlers);

    return insertKeymap;
  }

  private Keymap initializeReplaceKeymap(Keymap replaceKeymap) {
    CommandCompileService scs = ServiceHolder.commandCompileService;
    replaceKeymap.put(KeyPressed.from("Escape"),
      scs.compile("\"tempSpan\" \"span\" -> \"tempCursorQuantum\" changeCursorQuantum \"tempSpan\" removeGlobalVar \"tempCursorQuantum\" removeGlobalVar \"navigate\" changeMode")
    );
    replaceKeymap.put(KeyPressed.from("ArrowLeft"),
      scs.compile("-1 0 relativeMove")
    );
    replaceKeymap.put(KeyPressed.from("ArrowRight"),
      scs.compile("1 0 relativeMove")
    );
    replaceKeymap.put(KeyPressed.from("ArrowUp"),
      scs.compile("0 -1 relativeMove")
    );
    replaceKeymap.put(KeyPressed.from("ArrowDown"),
      scs.compile("0 1 relativeMove")
    );
    replaceKeymap.put(KeyPressed.from("Backspace"),
      scs.compile("-1 0 relativeMove")
    );
    final KeymapHandler[] replaceKeymapHandlers = new KeymapHandler[1];
    replaceKeymapHandlers[0] = ReplaceModeHandler.make();
    replaceKeymap.putHandlers(replaceKeymapHandlers);

    return replaceKeymap;
  }

  private Keymap initializeChangeScopeKeymap(Keymap changeScopeKeymap) {
    final KeymapHandler[] changeScopeHandlers = new KeymapHandler[1];
    changeScopeHandlers[0] = ChangeScopeSubmodeHandler.make();
    changeScopeKeymap.putHandlers(changeScopeHandlers);

    return changeScopeKeymap;
  }

}
