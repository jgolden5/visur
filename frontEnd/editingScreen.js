let canvasWidth = 30;
let canvasHeight = 34;
let cellWidth = 20;
let cellHeight = 20;

let exampleParagraph = "Trog is an ancient god of anger and violence. Followers are expected to kill in Trog's name, and especially to slay wizards. In return, worshippers of Trog gain the ability to go berserk at will in combat, and will be granted assistance in the form of powerful weapons and mighty allies. Followers are absolutely forbidden the use of spell magic. Trog offers various powers to help followers in battle. Followers will gain the ability to go berserk at will, and to rapidly heal their wounds while fortifying their willpower. Later, followers may call in powerful raging allies."
let currentCharIndex = 0
let paragraphWidth = canvasWidth;
let paragraphHeight = Math.ceil(exampleParagraph.length / paragraphWidth)

let cursorR = 0;
let cursorC = 0;
let paragraphXOffset = 3;
let paragraphYOffset = 25;
let cursorXOffset = -2;
let cursorYOffset = 25

document.addEventListener("keypress", keyPressHandler, false)
document.addEventListener("keyup", keyUpHandler, false)

let canvas = document.getElementById("mainCanvas");
let ctx = canvas.getContext("2d");
ctx.font = cellWidth + "px Courier";

let editingSpace = [];
let insertModeOn = false;

function buildCanvas() {
  for(let r = 0; r < paragraphHeight; r++) {
      editingSpace[r] = [];
    for(let c = 0; c < paragraphWidth; c++) {
          editingSpace[r][c] = " "
      }
  }
}

function paragraphToX(c) {
    return c * cellWidth + paragraphXOffset;
}

function paragraphToY(r) {
    return r * cellHeight + paragraphYOffset;
}

function cursorToX(c) {
    return c * cellWidth + cursorXOffset;
}

function cursorToY(r) {
    return r * cellHeight + cursorYOffset;
}

function drawCharacter(r, c, characterToDraw) {
    ctx.fillText(characterToDraw, paragraphToX(c), paragraphToY(r));
}

function drawCursor(r, c, cursorSymbol) {
    ctx.fillText(cursorSymbol, cursorToX(c), cursorToY(r))
}

function clearCell(r, c) {
    ctx.clearRect(paragraphToX(c), paragraphToY(r), cellWidth, cellHeight);
}

function drawCell(r, c) {
    let cell = editingSpace[r][c];
    let characterToDraw;
    let cursorIcon = insertModeOn ? "|" : "⬜"

    if(r == cursorR && c == cursorC) {
      drawCursor(r, c, cursorIcon)
    }
    characterToDraw = exampleParagraph.charAt(currentCharIndex)
    drawCharacter(r, c, characterToDraw)

	currentCharIndex++
}

function clearBoard() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

function drawBoard() {
	for(let r = 0; r < paragraphHeight; r++) {
		for(let c = 0; c < paragraphWidth; c++) {
            drawCell(r, c);
        }
      }
}

function cursorMove(rChange, cChange) {
    let cursorShouldNotMove = (rChange == 1 && cursorR == paragraphHeight - 1 ||
        rChange == 1 && cursorR >= paragraphHeight - 2 && cursorC >= exampleParagraph.length - (paragraphHeight - 1) * paragraphWidth ||
        rChange == -1 && cursorR == 0 ||
        cChange == 1 && cursorC == paragraphWidth - 1 ||
        cChange == 1 && cursorR == paragraphHeight - 1 && cursorC + 1 >= exampleParagraph.length - (paragraphHeight - 1) * paragraphWidth ||
        cChange == -1 && cursorC == 0)
    if(!cursorShouldNotMove) {
        cursorR += rChange
        cursorC += cChange
        refresh()
    }
}

function cursorContinue() { //continues the cursor along the line of text being edited on the screen

}

function keyPressHandler(e) {
    if(!insertModeOn) {
        if(e.key == "h" || e.key == "ArrowLeft") {
          cursorMove(0, -1)
        }
        else if(e.key == "l" || e.key == "ArrowRight") {
          cursorMove(0, 1)
        }
        else if(e.key == "j" || e.key == "ArrowDown") {
          cursorMove(1, 0)
        }
        else if(e.key == "k" || e.key == "ArrowUp") {
          cursorMove(-1, 0)
        }
        else if(e.key == "i") {
          insertModeOn = true
          refresh()
          console.log("insert mode is on")
        }
    } else {
      let indexPosition = cursorR * canvasWidth + cursorC
      if(event.key === "Backspace") {
        console.log("Backspace triggered")
        const firstPart = exampleParagraph.substring(0, indexPosition)
        const lastPart = exampleParagraph.substring(indexPosition + 1, exampleParagraph.length - 1)
        exampleParagraph = firstPart + lastPart
      } else {
        let characterToBeInserted = e.key
        let tempParagraphArr = exampleParagraph.split("")
        tempParagraphArr.splice(indexPosition, 0, characterToBeInserted)
        exampleParagraph = tempParagraphArr.join("")
        if(cursorC == paragraphWidth - 1) {
          paragraphHeight += 1
          cursorR += 1
          cursorC = 0
        } else {
          cursorMove(0, 1)
        }
      }
      refresh()
    }
}

function keyUpHandler(e) {
    if(insertModeOn) {
        if(e.key === "Escape") {
            insertModeOn = false
            refresh()
            console.log("insert mode is off")
        }
    }
}

function refresh() {
    clearBoard();
    currentCharIndex = 0;
    buildCanvas();
    drawBoard();
}

refresh();
