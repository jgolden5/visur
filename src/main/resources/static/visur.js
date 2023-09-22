var cursorX;
var cursorY;
var content;

document.addEventListener('keydown', (event) => {
  var eb = new EventBus('http://localhost:8888/eventbus');
  eb.onopen = function() {
    var svc = new BrowserInputService(eb, "keyWasPressed");
    svc.keyPress(event.key);
    eb.registerHandler('viewWasChanged', (error, message) => {
      console.log("view was changed. message = " + (JSON.stringify(message)))
      cursorX = message["body"]["cursorX"]
      cursorY = message["body"]["cursorY"]

      console.log("CursorX = " + (message["body"]["cursorX"]))
      console.log("CursorY = " + (message["body"]["cursorY"]))

      clearCanvas()
      drawCanvas()
      console.log("Canvas should have been drawn")

    })
  }

})

let canvas = document.getElementById("mainCanvas")

let cellWidth = 20
let cellHeight = 20
let canvasWidth = canvas.width / cellWidth
let canvasHeight = canvas.height / cellHeight

let canvasXOffset = 3
let canvasYOffset = 25


let ctx = canvas.getContext("2d")
ctx.font = cellWidth + "px courier"

function drawCanvas() {
  console.log("before drawCanvas()")
  for(let y = 0; y < canvasHeight; y++) {
    for(let x = 0; x < canvasWidth; x++) {
      if(cursorX == x && cursorY == y) {
        drawCharacter(x, y, "O")
      } else {
        drawCharacter(x, y, ".") //eventually content
      }
    }
  }
  console.log("after drawCanvas()")
}

let i = 0

function toX(x) {
  return x * cellWidth + canvasXOffset
}

function toY(y) {
  return y * cellHeight + canvasYOffset
}

function drawCharacter(x, y, characterToDraw) {
  console.log("before drawCharacter()")
  ctx.fillText(characterToDraw, toX(x), toY(y));
  console.log("after drawCharacter(), " + i + " characters have been drawn")
  i++
  console.log(i + " characters have been drawn")
}

function clearCanvas() {
  console.log("before clearCanvas()")
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  console.log("after clearCanvas()")
}


