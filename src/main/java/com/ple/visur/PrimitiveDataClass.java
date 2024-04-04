package com.ple.visur;


public class PrimitiveDataClass implements DataClass {

  DFReferenceGetter dfReferenceGetter;

  public PrimitiveDataClass(DFReferenceGetter dfReferenceGetter) {
    this.dfReferenceGetter = dfReferenceGetter;
  }

  public static PrimitiveDataClass make(DFReferenceGetter dfReferenceGetter) {
    return new PrimitiveDataClass(dfReferenceGetter);
  }

  public DataFormBrick getReference(DataFormBrick dfb) {
    DataForm referenceDF = dfReferenceGetter.getReference(dfb);
    DataFormBrick dfReferenceBrick = dfb.convert(referenceDF);
    return dfReferenceBrick;
  }

}
