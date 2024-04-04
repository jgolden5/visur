package com.ple.visur;


import java.util.HashMap;

public class DFDCInitializerService {
  public static DFDCInitializerService make() {
    return new DFDCInitializerService();
  }

  private static DataForm initializeTfStringBooleanDF(DataClass booleanDC) {
    return DataForm.make("TfStringBoolean", booleanDC, new HashMap<>());
  }

  private static DataForm initializeTrueFalseBooleanDF(DataClass booleanDC) {
    return DataForm.make("TrueFalseBoolean", booleanDC, new HashMap<>());
  }

  private static DataForm initializeIntBooleanDF(DataClass booleanDC) {
    return DataForm.make("IntBoolean", booleanDC, new HashMap<>());
  }

  private static DataForm initializeJavaBooleanDF(DataClass booleanDC) {
    return DataForm.make("JavaBoolean", booleanDC, new HashMap<>());
  }

  public static DataForm[] getBooleanDataForms(DataClass booleanDC) {
    DataForm[] booleanDataForms = new DataForm[4];
    DataForm tfStringBooleanDF = DFDCInitializerService.initializeTfStringBooleanDF(booleanDC);
    DataForm trueFalseStringBooleanDF = DFDCInitializerService.initializeTrueFalseBooleanDF(booleanDC);
    DataForm intBooleanDF = DFDCInitializerService.initializeIntBooleanDF(booleanDC);
    DataForm javaBooleanDF = DFDCInitializerService.initializeJavaBooleanDF(booleanDC);

    Converter tfToTrueFalse = (val) -> {
      Object newVal = null;
      if(val != null) {
        if (val.equals("t")) {
          newVal = "true";
        } else if (val.equals("f")) {
          newVal = "false";
        }
      }
      return newVal;
    };

    Converter trueFalseToTf = (val) -> {
      Object newVal = null;
      if(val != null) {
        if (val.equals("true")) {
          newVal = "t";
        } else if (val.equals("false")) {
          newVal = "f";
        }
      }
      return newVal;
    };

    Converter trueFalseToInt = (val) -> {
      Object newVal = null;
      if(val != null) {
        if (val.equals("true")) {
          newVal = 0;
        } else if (val.equals("false")) {
          newVal = 1;
        }
      }
      return newVal;
    };

    Converter intToTrueFalse = (val) -> {
      Object newVal = null;
      if(val != null) {
        if (val.equals(0)) {
          newVal = "true";
        } else if (val.equals(1)) {
          newVal = "false";
        }
      }
      return newVal;
    };

    Converter javaToTrueFalse = (val) -> {
      Object newVal = null;
      if(val != null) {
        if ((Boolean)val) {
          newVal = "true";
        } else {
          newVal = "false";
        }
      }
      return newVal;
    };

    tfStringBooleanDF.putConverter(trueFalseStringBooleanDF, tfToTrueFalse);

    trueFalseStringBooleanDF.putConverter(tfStringBooleanDF, trueFalseToTf);
    trueFalseStringBooleanDF.putConverter(intBooleanDF, trueFalseToInt);

    intBooleanDF.putConverter(trueFalseStringBooleanDF, intToTrueFalse);

    javaBooleanDF.putConverter(trueFalseStringBooleanDF, javaToTrueFalse);

    booleanDataForms[0] = tfStringBooleanDF;
    booleanDataForms[1] = trueFalseStringBooleanDF;
    booleanDataForms[2] = intBooleanDF;
    booleanDataForms[3] = javaBooleanDF;
    return booleanDataForms;
  }


  private static DataClass[] getCursorPosDataClasses() {
    DataClass[] cursorPosDataClasses = new DataClass[3];

    CompoundDataClass cursorPosDC = CompoundDataClass.make();
    CompoundDataClass wholePairDC = CompoundDataClass.make();

    DFReferenceGetter getAbsoluteReferenceDF = (dfb) -> {
      DataForm[] cursorPosDataForms = getCursorPosDataForms(dfb.fromDF.dc); //<-- is dc argument necessary now that we have parent dcb's?
      return cursorPosDataForms[0]; //should be absolute form (a). cursorPosDataForms[1] would be cxcy form
    };

    PrimitiveDataClass wholeNumberDC = PrimitiveDataClass.make(getAbsoluteReferenceDF);
    cursorPosDC.putSub("a", wholeNumberDC);
    cursorPosDC.putSub("cxcy", wholePairDC);
    wholePairDC.putSub("cx", wholeNumberDC);
    wholePairDC.putSub("cy", wholeNumberDC);

    Deriver aToCXCY = (cdcb) -> {
      return DataFormBrick.make();
    };

    Deriver cxcyToA = (cdcb) -> {
      return DataFormBrick.make();
    };

    cursorPosDC.putDeriver("a", aToCXCY);
    cursorPosDC.putDeriver("cxcy", cxcyToA);

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
