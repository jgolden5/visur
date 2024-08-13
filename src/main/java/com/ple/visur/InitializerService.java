package com.ple.visur;

import CursorPositionDC.*;
import DataClass.CompoundDataClassBrick;
import DataClass.LayeredDataClassBrick;
import DataClass.PrimitiveDataClassBrick;

import java.util.ArrayList;
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

    final String initialEditorContent = "Hello world\n" +
      "How are you?\n" +
      "Goodbye";
//    final String initialEditorContent = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\nbbb";
//    final String initialEditorContent = "Vehumet is a god of the destructive powers of magic.\n" +
//      "He is also the best guardian for DrCj, objectively.\n" +
//      "Followers will gain divine assistance in commanding the hermetic arts, and the most favoured stand to gain access to some of the fearsome spells in Vehumet's library.\n" +
//      "One's devotion to Vehumet can be proven by the causing of as much carnage and destruction as possible.\n" +
//      "Worshippers of Vehumet will quickly be able to recover their magical energy upon killing beings.\n" +
//      "As they gain favour, they will also gain enhancements to their destructive spells — first assistance in casting such spells and then increased range for conjurations.\n" +
//      "Vehumet will offer followers the knowledge of increasingly powerful destructive spells as they gain piety.\n" +
//      "Whether \"having spells as a god\" is a good thing is up to you, but it does come with a few downsides. Without active abilities, you have less ways to deal with a dangerous situation. In addition, Vehumet is one of the weaker gods for the early game. For spellcaster backgrounds, the first few spell gifts generally won't be much of an improvement compared to your own spells. Characters new to spellcasting have to take time to train up magic, which might not be all that powerful by the time you hit 1* or even 3*. Furthermore, there are ways to get an \"engine\" without taking up the god slot.\n" +
//      "Let's find out what it means to be a magic-user...";

    initializeCursorPositionDCBsAndBVVs();

    emc.initializeEditorContent(initialEditorContent);
    emc.updateNextLineIndices();
    emc.putLineWrapping(LineWrapping.wrapped);

    int vcx = 0;
    emc.putVCX(vcx);
    int cy = 0;
    emc.putCY(cy);

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

  private void initializeCursorPositionDCBsAndBVVs() {
    CursorPositionDCHolder cursorPositionDCHolder = new CursorPositionDCHolder();
    WholeNumberDC wholeNumberDC = cursorPositionDCHolder.wholeNumberDC;
    WholeNumberListDC wholeNumberListDC = cursorPositionDCHolder.wholeNumberListDC;
    PrimitiveDataClassBrick nlDCB = wholeNumberListDC.makeBrick("nl", new ArrayList<>(), true);
    PrimitiveDataClassBrick rcxDCB = wholeNumberDC.makeBrick("rcx", new ArrayList<>(), false);
    PrimitiveDataClassBrick cyDCB = wholeNumberDC.makeBrick("cy", new ArrayList<>(), false);
    PrimitiveDataClassBrick llDCB = wholeNumberDC.makeBrick("ll", new ArrayList<>(), false);
    CursorPositionDC cursorPositionDC = CursorPositionDCHolder.make().cursorPositionDC;
    LayeredDataClassBrick cursorPositionDCB = cursorPositionDC.makeBrick(nlDCB, rcxDCB, cyDCB, llDCB);
    CompoundDataClassBrick coordinatesDCB = cursorPositionDCB.getLayer(0);
    CompoundDataClassBrick llFromCYDCB = cursorPositionDCB.getLayer(1);
    CompoundDataClassBrick virtualDCB = cursorPositionDCB.getLayer(2);
    CompoundDataClassBrick caAndNLDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("caAndNL");
    CompoundDataClassBrick rcxcyAndNLDCB = (CompoundDataClassBrick) coordinatesDCB.getInner("rcxcyAndNL");
    CompoundDataClassBrick llcyAndNLDCB = (CompoundDataClassBrick) llFromCYDCB.getInner("llcyAndNL");
    CompoundDataClassBrick cyAndNLDCB = (CompoundDataClassBrick) llFromCYDCB.getInner("cyAndNL");
    CompoundDataClassBrick vcxAndLLDCB = (CompoundDataClassBrick) virtualDCB.getInner("vcxAndLL");
    CompoundDataClassBrick rcxAndLODCB = (CompoundDataClassBrick) virtualDCB.getInner("rcxAndLO");
    PrimitiveDataClassBrick caDCB = (PrimitiveDataClassBrick) caAndNLDCB.getInner("ca");
    PrimitiveDataClassBrick vcxDCB = (PrimitiveDataClassBrick) vcxAndLLDCB.getInner("vcx");
    PrimitiveDataClassBrick loDCB = (PrimitiveDataClassBrick) rcxAndLODCB.getInner("lo");
    caAndNLDCB.putInner("ca", caDCB);
    caAndNLDCB.putInner("nl", nlDCB);
    rcxcyAndNLDCB.putInner("rcx", rcxDCB);
    rcxcyAndNLDCB.putInner("cy", cyDCB);
    rcxcyAndNLDCB.putInner("nl", nlDCB);
    llcyAndNLDCB.putInner("ll", llDCB);
    cyAndNLDCB.putInner("cy", cyDCB);
    llcyAndNLDCB.putInner("nl", nlDCB);
    cyAndNLDCB.putInner("cy", cyDCB);
    cyAndNLDCB.putInner("nl", nlDCB);
    vcxAndLLDCB.putInner("vcx", vcxDCB);
    vcxAndLLDCB.putInner("ll", llDCB);
    rcxAndLODCB.putInner("rcx", rcxDCB);
    rcxAndLODCB.putInner("lo", loDCB);
    coordinatesDCB.putInner("caAndNL", caAndNLDCB);
    coordinatesDCB.putInner("rcxcyAndNL", rcxcyAndNLDCB);
    llFromCYDCB.putInner("llcyAndNL", llcyAndNLDCB);
    llFromCYDCB.putInner("cyAndNL", cyAndNLDCB);
    virtualDCB.putInner("vcxAndLL", vcxAndLLDCB);
    virtualDCB.putInner("rcxAndLO", rcxAndLODCB);

    initializeBVVs(nlDCB, caDCB, rcxDCB, vcxDCB, cyDCB, llDCB);

  }

  private void initializeBVVs(PrimitiveDataClassBrick nlDCB, PrimitiveDataClassBrick caDCB, PrimitiveDataClassBrick rcxDCB, PrimitiveDataClassBrick vcxDCB, PrimitiveDataClassBrick cyDCB, PrimitiveDataClassBrick llDCB) {
    BrickVisurVar nlBVV = BrickVisurVar.make(nlDCB);
    BrickVisurVar caBVV = BrickVisurVar.make(caDCB);
    BrickVisurVar rcxBVV = BrickVisurVar.make(rcxDCB);
    BrickVisurVar vcxBVV = BrickVisurVar.make(vcxDCB);
    BrickVisurVar cyBVV = BrickVisurVar.make(cyDCB);
    BrickVisurVar llBVV = BrickVisurVar.make(llDCB);
    emc.putGlobalVar("nl", nlBVV);
    emc.putGlobalVar("ca", caBVV);
    emc.putGlobalVar("rcx", rcxBVV);
    emc.putGlobalVar("vcx", vcxBVV);
    emc.putGlobalVar("cy", cyBVV);
    emc.putGlobalVar("ll", llBVV);
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
    int realCA = emc.getCA();
    int bounds[] = emc.getQuantumNameToQuantum().get(startingCursorQuantumName).getBoundaries(realCA, emc.getNextLineIndices(), emc.getSpan(), false);
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
