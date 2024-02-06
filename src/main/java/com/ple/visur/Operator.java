package com.ple.visur;

public enum Operator {
  absoluteMove,
  cursorLeft,
  cursorRight,
  cursorDown,
  cursorUp,
  moveCursorToBeginningOfNextWord,
  moveCursorToBeginningOfCurrentLine,
  moveCursorToEndOfCurrentLine,
  moveCursorToFirstNonSpaceInCurrentLine,
  enterInsertMode,
  enterEditingMode,
  insertNewLine,
  insertChar,
  deleteCurrentChar,
  moveRight,
  literalNumberOperator,
  literalStringOperator,
  assignmentWordOperator,
  nativeOperatorWordOperator,
}
