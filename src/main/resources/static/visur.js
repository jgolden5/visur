let canvasX;
let canvasY;
let contentLines;

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

let canvasXOffset = 6
let canvasYOffset = 20

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
    canvasX = message["body"]["canvasX"]
    canvasY = message["body"]["canvasY"]
    contentLines = message["body"]["contentLines"]
    mode = message["body"]["editorMode"]
    isInCommandState = message["body"]["isInCommandState"]
    commandStateContent = message["body"]["commandStateContent"]
    commandCursor = message["body"]["commandCursor"]

//    console.log("Content = " + (contentLines))

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
    width: (canvas.width - canvasXOffset) / cellWidth + 1,
    height: (canvas.height - canvasYOffset) / cellHeight + 1
  };
  /*canvasWasChangedEventComplete ensures that canvasWasChanged gets called before modelWasChanged so that
  getCanvasWidth is not called before putCanvasWidth */
  eb.registerHandler("canvasWasChangedEventComplete", (error, message) => {
    console.log("canvasWasChangedEventComplete message: " + JSON.stringify(message));
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
  let x
  let y
  let numberOfWrappedLines = 0
  let cursorWasDrawn = false
  let fullContentWasDrawn = false
  contentLoop:
    for(y = 0; y < canvasHeight; y++) {
      let line;
      if(contentLines.length == drawContentY) {
        fullContentWasDrawn = true
      } else {
        line = contentLines[drawContentY]
      }
      for(x = 0; x <= canvasWidth; x++) {
        if(fullContentWasDrawn && cursorWasDrawn) {
          break contentLoop
        }
        if(!cursorWasDrawn) {
          if(x == canvasX && y == canvasY) {
//            if(mode == "insert") {
              drawCursor(x, y, "⎸️")
              cursorWasDrawn = true
//            } else {
//              drawCursor(x, y, "⬜️") //may get used for span
//            }
          }
        }
        if(x != canvasWidth) {
          if(!fullContentWasDrawn) {
            let char = line.charAt(drawContentX)
            drawCharacter(x, y, char)
            drawContentX++
          }
        }
      }
      if(!fullContentWasDrawn) {
        if(canvasWidth * (numberOfWrappedLines + 1) >= line.length) {
          numberOfWrappedLines = 0
          drawContentX = 0
          drawContentY++
        } else {
          numberOfWrappedLines++
        }
      }
    }
}

let i = 0

function toXContent(x) { //as opposed to XCursor
  return x * cellWidth + canvasXOffset
}

function toXCursor(x) {
  return x * cellWidth + canvasXOffset - 6
}

function toY(y) {
  return y * cellHeight + canvasYOffset
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
