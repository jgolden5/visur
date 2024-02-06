package com.ple.visur;

public enum Operator {
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
}
