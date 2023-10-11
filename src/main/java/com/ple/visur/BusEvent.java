package com.ple.visur;

public enum BusEvent {
  //sent in js in eb.onopen, consumed by CanvasWasChangedVerticle
  canvasWasChanged,

  //sent in js in keyDown event listener, consumed by KeyWasPressedVerticle
  keyWasPressed,

  //sent in KeyWasPressedVerticle, consumed by ModelChangeVerticle
  modelChange,

  //sent in ModelChangeVerticle, consumed in visur.js in eventbus.onopen
  viewWasChanged
}

