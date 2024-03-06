let contentX;
let contentY;
let editorContent;

let canvas = document.getElementById("mainCanvas")
if((window.innerWidth - 5) % 20 == 0) {
  canvas.width = window.innerWidth
} else {
  canvas.width = window.innerWidth - (window.innerWidth - 5) % 20
}
canvas.height = window.innerHeight
console.log("canvas width = " + canvas.width)
console.log("canvas height = " + canvas.height)

let cellWidth = 20
let cellHeight = 20
let canvasWidth = Math.floor(canvas.width / cellWidth)
let canvasHeight = Math.floor(canvas.height / cellHeight)

let contentXOffset = 6
let contentYOffset = 20

let numberOfTimesPageWasLoaded = 0

let ctx = canvas.getContext("2d")
ctx.font = cellWidth + "px courier"

function refreshEditor() {
  numberOfTimesPageWasLoaded++
  console.log("editor refreshed " + numberOfTimesPageWasLoaded + " times")
}

document.getElementById("visurEditor").onload = function() { refreshEditor() }

var eb = new EventBus('http://localhost:8888/eventbus');
eb.onopen = function() {
  console.log("connection established with js' event bus")
  eb.registerHandler('viewWasChanged', (error, message) => {
    contentX = message["body"]["contentX"]
    contentY = message["body"]["contentY"]
    editorContent = message["body"]["editorContent"]
    mode = message["body"]["editorMode"]
    isInCommandState = message["body"]["isInCommandState"]
    commandStateContent = message["body"]["commandStateContent"]
    commandCursor = message["body"]["commandCursor"]

//    console.log("Content = " + (editorContent))

    clearCanvas()
    ctx.fillStyle = "white"
    drawCanvas()
    if(isInCommandState) {
      let commandStateDisplay = ";"
      let commandCursorWasDrawn = false;
      for(let x = 0; x < commandStateContent.length; x++) {
        if(x == commandCursor) {
          commandStateDisplay += "|";
          commandCursorWasDrawn = true;
        } else {
          commandStateDisplay += " ";
        }
        commandStateDisplay += commandStateContent[x];
      }
      if(!commandCursorWasDrawn) {
        commandStateDisplay += "|"
      }
      document.getElementById("currentEditorModeDisplay").innerHTML = commandStateDisplay
    } else {
      document.getElementById("currentEditorModeDisplay").innerHTML = mode.toUpperCase() + " MODE"
    }

  })
  let canvasInfo = {
    width: (canvas.width - contentXOffset) / cellWidth + 1,
    height: (canvas.height - contentYOffset) / cellHeight + 1
  };
  /*canvasWasChangedEventComplete ensures that canvasWasChanged gets called before modelWasChanged so that
  getCanvasWidth is not called before putCanvasWidth */
  eb.registerHandler("modelWasChanged", (error, message) => {
    console.log("modelWasChanged message: " + JSON.stringify(message));
    eb.send("modelWasChanged", null)
  });
  eb.send("canvasWasChanged", JSON.stringify(canvasInfo));
}

function sendKeyPressedEvent(keyPressed) {
  console.log("keyPressed = " + keyPressed)
  let keyPressedInfo = {
    key: keyPressed
  }
  eb.send("keyWasPressed", JSON.stringify(keyPressedInfo))
  console.log(keyPressedInfo.key + " key was pressed")
}

document.addEventListener('keydown', (event) => {
  sendKeyPressedEvent(event.key)
})

function drawCanvas() {
  let drawContentX = 0
  let drawContentY = 0
  lineContinuing = false
  let x = 0
  let y = 0
  let numberOfWrappedLines = 0
  let cursorWasDrawn = false
  let fullContentWasDrawn = false
  contentLoop:
  for(let absX = 0; absX < editorContent.length; absX++) {
    if(absX == contentX && !cursorWasDrawn) {
      drawCursor(x, y, "⎸");
      cursorWasDrawn = true;
    }
    characterToDraw = editorContent[absX];
    drawCharacter(x, y, characterToDraw);
    x++
    let isAtEndOfCurrentLine = x % canvasWidth == 0
    if(editorContent[absX] === "\n" || isAtEndOfCurrentLine) {
      y++
      x = 0
    }
  }
  if(!cursorWasDrawn) {
    drawCursor(x, y, "⎸");
    cursorWasDrawn = true
  }
}

function toXContent(x) { //as opposed to XCursor
  return x * cellWidth + contentXOffset
}

function toXCursor(x) {
  return x * cellWidth + contentXOffset - 6
}

function toY(y) {
  return y * cellHeight + contentYOffset
}

function drawCharacter(x, y, characterToDraw) {
  ctx.fillText(characterToDraw, toXContent(x), toY(y));
}

function drawCursor(x, y, cursorIcon) {
  ctx.fillText(cursorIcon, toXCursor(x), toY(y))
}

function clearCanvas() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
}
