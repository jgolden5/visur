var cursorX;
var cursorY;
var contentLines;

let canvas = document.getElementById("mainCanvas")

let cellWidth = 20
let cellHeight = 20
let canvasWidth = canvas.width / cellWidth
let canvasHeight = canvas.height / cellHeight

let canvasXOffset = 3
let canvasYOffset = 25

let ctx = canvas.getContext("2d")
ctx.font = cellWidth + "px courier"

var eb = new EventBus('http://localhost:8888/eventbus');
eb.onopen = function() {
  eb.registerHandler('viewWasChanged', (error, message) => {
    console.log("view was changed. message = " + (JSON.stringify(message)))
    cursorX = message["body"]["cursorX"]
    cursorY = message["body"]["cursorY"]
    contentLines = message["body"]["contentLines"]

    console.log("CursorX = " + (message["body"]["cursorX"]))
    console.log("CursorY = " + (message["body"]["cursorY"]))
    console.log("Content = " + (message["body"]["contentLines"]))

    clearCanvas()
    drawCanvas()
    console.log("Canvas should have been drawn")

  })
  let canvasInfo = {
    width: (canvas.width - canvasXOffset) / cellWidth + 1,
    height: (canvas.height - canvasYOffset) / cellHeight + 1
  };
  eb.send("canvasWasChanged", JSON.stringify(canvasInfo))
}

document.addEventListener('keydown', (event) => {
//  var svc = new BrowserInputService(eb, "keyWasPressed");
//  svc.keyPress(event.key);
  let keyPressedInfo = {
    key: event.key
  }
  eb.send("keyWasPressed", JSON.stringify(keyPressedInfo))
})

function drawCanvas() {
  console.log("before drawCanvas()")
  let contentX = 0
  let contentY = 0
  lineContinuing = false
  let x
  let y
  let numberOfWrappedLines = 0
  let cursorWasDrawn = false
  let fullContentWasDrawn = false
  contentLoop:
    for(y = 0; y < canvasHeight; y++) {
      let line = contentLines[contentY]
      for(x = 0; x < canvasWidth; x++) {
        if(!fullContentWasDrawn) {
          if(contentY == contentLines.length - 1 && contentX == line.length) {
            fullContentWasDrawn = true
          }
        }
        if(!cursorWasDrawn) {
          if(cursorX < x && cursorY < y) {
            cursorWasDrawn = true
          }
        }
        if(fullContentWasDrawn && cursorWasDrawn) {
          break contentLoop
        }
        if(!cursorWasDrawn) {
          if(x == cursorX && y == cursorY) {
            drawCursor(x, y, "⬜️")
          }
        }
        if(!fullContentWasDrawn) {
          let char = line.charAt(contentX)
          drawCharacter(x, y, char)
          contentX++
        }
      }
      if(!fullContentWasDrawn) {
        if(canvasWidth * (numberOfWrappedLines + 1) >= line.length) {
          numberOfWrappedLines = 0
          contentX = 0
          contentY++
        } else {
          numberOfWrappedLines++
        }
      }
    }

  console.log("canvas x = " + x)
  console.log("canvas y = " + y)
  console.log("cursor x = " + cursorX + ", cursor y = " + cursorY)
  console.log("contentLines = " + contentLines + ". contentLines length = " + contentLines.length)
  console.log("after drawCanvas()")
}

let i = 0

function toXContent(x) {
  return x * cellWidth + canvasXOffset
}

function toXCursor(x) {
  return x * cellWidth + canvasXOffset - 6
}

function toY(y) {
  return y * cellHeight + canvasYOffset
}

function drawCharacter(x, y, characterToDraw) {
//  console.log("before drawCharacter()")
  ctx.fillText(characterToDraw, toXContent(x), toY(y));
//  console.log("after drawCharacter(), " + i + " characters have been drawn")
}

function drawCursor(x, y, cursorIcon) {
  ctx.fillText(cursorIcon, toXCursor(x), toY(y))
}

function clearCanvas() {
  console.log("before clearCanvas()")
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  console.log("after clearCanvas()")
}
