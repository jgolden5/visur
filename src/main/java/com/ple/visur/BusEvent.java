package com.ple.visur;

public enum BusEvent {
  //sent by eb.onopen (visur.js), consumed by CanvasWasChangedVerticle
  canvasWasChanged,
  keyWasPressed, modelChange, viewWasChanged
}

