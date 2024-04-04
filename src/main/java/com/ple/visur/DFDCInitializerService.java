package com.ple.visur;


import java.util.HashMap;

public class DFDCInitializerService {
  public DFDCInitializerService make() {
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

  public static DataForm[] getStringDataForms(DataClass letterStringDC) {
    DataForm[] stringDataForms = new DataForm[8];

    DataForm aStringDF = DataForm.make("a", letterStringDC, new HashMap<>());
    DataForm bStringDF = DataForm.make("b", letterStringDC, new HashMap<>());
    DataForm cStringDF = DataForm.make("c", letterStringDC, new HashMap<>());
    DataForm dStringDF = DataForm.make("d", letterStringDC, new HashMap<>());
    DataForm eStringDF = DataForm.make("e", letterStringDC, new HashMap<>());
    DataForm fStringDF = DataForm.make("f", letterStringDC, new HashMap<>());
    DataForm gStringDF = DataForm.make("g", letterStringDC, new HashMap<>());
    DataForm hStringDF = DataForm.make("h", letterStringDC, new HashMap<>());

    Converter aToB = (val) -> {
      Object newVal = null;
      if (val.equals("a")) {
        newVal = "b";
      }
      return newVal;
    };

    Converter aToC = (val) -> {
      Object newVal = null;
      if (val.equals("a")) {
        newVal = "c";
      }
      return newVal;
    };

    Converter bToD = (val) -> {
      Object newVal = null;
      if (val.equals("b")) {
        newVal = "d";
      }
      return newVal;
    };

    Converter bToE = (val) -> {
      Object newVal = null;
      if (val.equals("b")) {
        newVal = "e";
      }
      return newVal;
    };

    Converter bToF = (val) -> {
      Object newVal = null;
      if (val.equals("b")) {
        newVal = "f";
      }
      return newVal;
    };

    Converter cToG = (val) -> {
      Object newVal = null;
      if (val.equals("c")) {
        newVal = "g";
      }
      return newVal;
    };

    Converter cToA = (val) -> {
      Object newVal = null;
      if (val.equals("c")) {
        newVal = "a";
      }
      return newVal;
    };

    Converter eToH = (val) -> {
      Object newVal = null;
      if (val.equals("e")) {
        newVal = "h";
      }
      return newVal;
    };

    aStringDF.putConverter(bStringDF, aToB);
    aStringDF.putConverter(cStringDF, aToC);

    bStringDF.putConverter(dStringDF, bToD);
    bStringDF.putConverter(eStringDF, bToE);
    bStringDF.putConverter(fStringDF, bToF);

    cStringDF.putConverter(gStringDF, cToG);
    cStringDF.putConverter(aStringDF, cToA);

    eStringDF.putConverter(hStringDF, eToH);

    stringDataForms[0] = aStringDF;
    stringDataForms[1] = bStringDF;
    stringDataForms[2] = cStringDF;
    stringDataForms[3] = dStringDF;
    stringDataForms[4] = eStringDF;
    stringDataForms[5] = fStringDF;
    stringDataForms[6] = gStringDF;
    stringDataForms[7] = hStringDF;

    return stringDataForms;
  }

  public static DataClass[] getBooleanDataClasses() {
    DataClass[] booleanDataClasses = new DataClass[3];
    CompoundDataClass notBoolDC = CompoundDataClass.make(new HashMap<>());

    DFReferenceGetter getBooleanReferenceDF = (dfb) -> {
      DataForm[] booleanDataForms = DFDCInitializerService.getBooleanDataForms(dfb.fromDF.dc);
      return booleanDataForms[1]; //trueFalseStringBool
    };

    PrimitiveDataClass aBoolDC = PrimitiveDataClass.make(getBooleanReferenceDF);
    PrimitiveDataClass bBoolDC = PrimitiveDataClass.make(getBooleanReferenceDF);
    notBoolDC.putSub("a", aBoolDC);
    notBoolDC.putSub("b", bBoolDC);

    Deriver boolNotAToB = (cdcb) -> {
      PrimitiveDataClassBrick subA = (PrimitiveDataClassBrick) cdcb.getSub("a");
      DataFormBrick aSubDFB = subA.getVal();
      Object aSubDFBVal = aSubDFB.getVal();
      Object dfbResVal = null;
      if (aSubDFBVal.equals("true")) {
        dfbResVal = "false";
      } else if (aSubDFBVal.equals("false")) {
        dfbResVal = "true";
      }
      return DataFormBrick.make(aSubDFB.getDF(), dfbResVal);
    };

    Deriver boolNotBToA = (cdcb) -> {
      PrimitiveDataClassBrick subB = (PrimitiveDataClassBrick) cdcb.getSub("b");
      if(subB.getVal() == null) {
        PrimitiveDataClassBrick subA = (PrimitiveDataClassBrick) cdcb.getSub("a");
        if(subA == null) {
          return null;
        }
      }
      DataFormBrick bSubDFB = subB.getVal();
      Object bSubDFBVal = bSubDFB.getVal();
      Object dfbResVal = null;
      if (bSubDFBVal.equals("true")) {
        dfbResVal = "false";
      } else if (bSubDFBVal.equals("false")) {
        dfbResVal = "true";
      }
      return DataFormBrick.make(bSubDFB.getDF(), dfbResVal);
    };

    notBoolDC.putDeriver("a", boolNotBToA);
    notBoolDC.putDeriver("b", boolNotAToB);

    booleanDataClasses[0] = notBoolDC;
    booleanDataClasses[1] = aBoolDC;
    booleanDataClasses[2] = bBoolDC;
    return booleanDataClasses;
  }

  public static DataClassBrick[] getBooleanDataClassBricks(DataFormBrick aDFB, DataFormBrick bDFB) {
    DataClassBrick[] booleanDataClassBricks = new DataClassBrick[3];

    DataClass[] booleanDataClasses = getBooleanDataClasses();
    CompoundDataClass notBoolDC = (CompoundDataClass) booleanDataClasses[0];
    PrimitiveDataClass aBoolDC = (PrimitiveDataClass) booleanDataClasses[1];
    PrimitiveDataClass bBoolDC = (PrimitiveDataClass) booleanDataClasses[2];

    HashMap<String, DataClassBrick> startingSubs = new HashMap<>();
    startingSubs.put("a", null);
    startingSubs.put("b", null);

    CompoundDataClassBrick booleanNotDCB = CompoundDataClassBrick.make(notBoolDC, startingSubs, 1);

    PrimitiveDataClassBrick notBoolA = PrimitiveDataClassBrick.make(aBoolDC, aDFB);
    PrimitiveDataClassBrick notBoolB = PrimitiveDataClassBrick.make(bBoolDC, bDFB);

    booleanNotDCB = booleanNotDCB.putSub("a", notBoolA);
    booleanNotDCB = booleanNotDCB.putSub("b", notBoolB);

    booleanDataClassBricks[0] = booleanNotDCB;
    booleanDataClassBricks[1] = notBoolA;
    booleanDataClassBricks[2] = notBoolB;

    return booleanDataClassBricks;
  }

}
