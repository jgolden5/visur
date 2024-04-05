package com.ple.visur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDF {

  @Test void aDFToCXCYDF() {
    DataClass[] cursorPosDataClasses = DFDCInitializerService.getCursorPosDataClasses();
    DataForm[] cursorPosDataForms = DFDCInitializerService.getCursorPosDataForms(cursorPosDataClasses[0], cursorPosDataClasses[1]);
    DataForm aDF = cursorPosDataForms[0];
    DataForm cxcyDF = cursorPosDataForms[1];
    DataFormBrick aDFB = DataFormBrick.make(aDF, 16);
    DataFormBrick cxcyDFB = aDFB.convert(cxcyDF);

    assertEquals(DataFormBrick.make(cxcyDF, new CursorPosition(4, 1)), cxcyDFB);

    aDFB = DataFormBrick.make(aDF, 0);
    cxcyDFB = aDFB.convert(cxcyDF);

    assertEquals(DataFormBrick.make(cxcyDF, new CursorPosition(0, 0)), cxcyDFB);

    aDFB = DataFormBrick.make(aDF, 6);
    cxcyDFB = aDFB.convert(cxcyDF);

    assertEquals(DataFormBrick.make(cxcyDF, new CursorPosition(6, 0)), cxcyDFB);

    aDFB = DataFormBrick.make(aDF, 30);
    cxcyDFB = aDFB.convert(cxcyDF);

    assertEquals(DataFormBrick.make(cxcyDF, new CursorPosition(5, 2)), cxcyDFB);

  }

  @Test void cxcyDFToADF() {
    DataClass[] cursorPosDataClasses = DFDCInitializerService.getCursorPosDataClasses();
    DataForm[] cursorPosDataForms = DFDCInitializerService.getCursorPosDataForms(cursorPosDataClasses[0], cursorPosDataClasses[1]);
    DataForm aDF = cursorPosDataForms[0];
    DataForm cxcyDF = cursorPosDataForms[1];

    DataFormBrick cxcyDFB = DataFormBrick.make(cxcyDF, new CursorPosition(5, 1));
    DataFormBrick aDFB = cxcyDFB.convert(aDF);

    assertEquals(DataFormBrick.make(aDF, 17), aDFB);

    cxcyDFB = DataFormBrick.make(cxcyDF, new CursorPosition(6, 0));
    aDFB = cxcyDFB.convert(aDF);

    assertEquals(DataFormBrick.make(aDF, 6), aDFB);
  }


}
