package com.ple.visur;

public enum BusEvent {
  //sent in js in eb.onopen, consumed by CanvasWasChangedVerticle
  canvasWasChanged,

  //sent in js in keyDown event listener, consumed by KeyWasPressedVerticle
  keyWasPressed,

  //sent in KeyWasPressedVerticle, consumed by ModelWasChangedVerticle
  modelWasChanged,

  //sent in ModelWasChangedVerticle, consumed in visur.js in eventbus.onopen
  viewWasChanged
}

