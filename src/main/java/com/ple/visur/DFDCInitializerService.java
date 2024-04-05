package com.ple.visur;


import java.util.ArrayList;
import java.util.HashMap;

public class DFDCInitializerService {
  public static DFDCInitializerService make() {
    return new DFDCInitializerService();
  }

  public static DataForm[] getCursorPosDataForms(DataClass cursorPosDC, DataClass cxcyDC) {
    DataForm[] cursorPosDataForms = new DataForm[3];
    DataForm aDF = DataForm.make("a", cursorPosDC);
    DataForm cxcyDF = DataForm.make("cxcy", cxcyDC);

    //I need to learn the difference between the Converters and the Derivers because there's a chance that both are doing the same thing in this case

    Converter aToCXCY = (val) -> {
      //for test:
      ArrayList<Integer> newlineIndices = new ArrayList<>();
      newlineIndices.add(11);
      newlineIndices.add(24);
//    not test:  ArrayList<Integer> newlineIndices = ems.getNewlineIndices();

      int a = (int)val;
      int cx = 0;
      int cy = 0;

      if(a <= newlineIndices.get(0)) {
        cx = a;
      } else {
        for (int i = 0; i < newlineIndices.size(); i++) {
          if (a > newlineIndices.get(i)) {
            cy++;
            cx = a - (newlineIndices.get(i) + 1);
          }
        }
      }

      CursorPosition cursorPos = new CursorPosition(cx, cy);
      return cursorPos;
    };

    Converter cxcyToA = (val) -> {
      Object newVal = null;
      return newVal;
    };

    aDF.putConverter(cxcyDF, aToCXCY);
    cxcyDF.putConverter(aDF, cxcyToA);

    cursorPosDataForms[0] = aDF;
    cursorPosDataForms[1] = cxcyDF;
    return cursorPosDataForms;
  }

  public static DataClass[] getCursorPosDataClasses() {
    DataClass[] cursorPosDataClasses = new DataClass[3];

    CompoundDataClass cursorPosDC = CompoundDataClass.make();
    CompoundDataClass wholePairDC = CompoundDataClass.make();

    DFReferenceGetter getAbsoluteReferenceDF = (dfb) -> {
      DataForm[] cursorPosDataForms = getCursorPosDataForms(cursorPosDC, wholePairDC);
      return cursorPosDataForms[0]; //should be absolute form (a). cursorPosDataForms[1] would be cxcy form
    };

    PrimitiveDataClass wholeNumberDC = PrimitiveDataClass.make(getAbsoluteReferenceDF);
    cursorPosDC.putSub("a", wholeNumberDC);
    cursorPosDC.putSub("cxcy", wholePairDC);
    wholePairDC.putSub("cx", wholeNumberDC);
    wholePairDC.putSub("cy", wholeNumberDC);

//    Deriver aToCXCY = (cdcb) -> {
//      return DataFormBrick.make();
//    };
//
//    Deriver cxcyToA = (cdcb) -> {
//      return DataFormBrick.make();
//    };

//    cursorPosDC.putDeriver("a", aToCXCY);
//    cursorPosDC.putDeriver("cxcy", cxcyToA);

    cursorPosDataClasses[0] = cursorPosDC;
    cursorPosDataClasses[1] = wholePairDC;
    cursorPosDataClasses[2] = wholeNumberDC;
    return cursorPosDataClasses;
  }

  public static DataClassBrick[] getCursorPosDataClassBricks(DataFormBrick aDFB, DataFormBrick cxDFB, DataFormBrick cyDFB) {
    DataClassBrick[] cursorPosDataClassBricks = new DataClassBrick[5];

    DataClass[] cursorPosDataClasses = getCursorPosDataClasses();
    CompoundDataClass cursorPosDC = (CompoundDataClass) cursorPosDataClasses[0];
    CompoundDataClass wholePairDC = (CompoundDataClass) cursorPosDataClasses[1];
    PrimitiveDataClass wholeNumberDC = (PrimitiveDataClass) cursorPosDataClasses[2];

    HashMap<String, DataClassBrick> startingSubsCursorPos = new HashMap<>();
    startingSubsCursorPos.put("a", null);
    startingSubsCursorPos.put("cxcy", null);

    CompoundDataClassBrick cursorPosDCB = CompoundDataClassBrick.make(cursorPosDC, null, startingSubsCursorPos, 1);

    PrimitiveDataClassBrick aDCB = PrimitiveDataClassBrick.make(wholeNumberDC, cursorPosDCB, aDFB);

    HashMap<String, DataClassBrick> startingSubsCXCY = new HashMap<>();
    startingSubsCXCY.put("cx", null);
    startingSubsCXCY.put("cy", null);

    CompoundDataClassBrick cxcyDCB = CompoundDataClassBrick.make(wholePairDC, cursorPosDCB, startingSubsCXCY, 2);

    cursorPosDCB.putSub("a", aDCB);
    cursorPosDCB.putSub("cxcy", cxcyDCB);

    PrimitiveDataClassBrick cxDCB = PrimitiveDataClassBrick.make(wholeNumberDC, cxcyDCB, cxDFB);
    PrimitiveDataClassBrick cyDCB = PrimitiveDataClassBrick.make(wholeNumberDC, cxcyDCB, cyDFB);

    cxcyDCB.putSub("cx", cxDCB);
    cxcyDCB.putSub("cy", cyDCB);

    cursorPosDataClassBricks[0] = cursorPosDCB;
    cursorPosDataClassBricks[1] = aDCB;
    cursorPosDataClassBricks[2] = cxcyDCB;
    cursorPosDataClassBricks[3] = cxDCB;
    cursorPosDataClassBricks[4] = cyDCB;

    return cursorPosDataClassBricks;
  }

}
