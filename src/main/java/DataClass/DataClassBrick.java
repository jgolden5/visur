package DataClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public abstract class DataClassBrick {
  public final DataClass dc;
  public final ArrayList<OuterDataClassBrick> outers;
  public String name;

  public DataClassBrick(DataClass dc, ArrayList<OuterDataClassBrick> outers, String name) {
    this.dc = dc;
    this.outers = outers;
    this.name = name;
  }

  public ArrayList<OuterDataClassBrick> getOuters() {
    return outers;
  }

  public ArrayList<ArrayList<OuterDataClassBrick>> getAllOutersInOrder() {
    ArrayList<ArrayList<OuterDataClassBrick>> allOuters = new ArrayList<>();
    if(getOuters() != null) {
      for (OuterDataClassBrick outer : getOuters()) {
        ArrayList<OuterDataClassBrick> odcbArrayList = new ArrayList<>();
        odcbArrayList.add(outer);
        if(outer.getOuters() != null) {
          for (OuterDataClassBrick outerOuter : getOuters()) {
            odcbArrayList.add(outerOuter);
          }
        }
        if(odcbArrayList.size() > 0) {
          allOuters.add(odcbArrayList);
        }
      }
    }
    return allOuters;
  }

  public Result<OuterDataClassBrick> getOuterContainingTargetName(String targetName) {
    Result r = Result.make(null, "target was not found");
    for(OuterDataClassBrick outer : outers) {
      if(outer.containsName(targetName)) {
        r = Result.make(outer, null);
        break;
      }
    }
    return r;
  }

  public void putOuter(OuterDataClassBrick outer) {
    outers.add(outer);
  }

  public abstract Result remove();

  public abstract void removeConflicts(String name, Object val);

  public abstract boolean isComplete();

  public String getName() {
    return name;
  }

  private void putName(String name) {
    this.name = name;
  }

  public boolean containsName(String targetName) {
    boolean containsName = false;
    if(getName().equals(targetName)) {
      containsName = true;
    } else if(this instanceof CompoundDataClassBrick) {
      CompoundDataClassBrick thisAsCDCB = (CompoundDataClassBrick) this;
      for(Map.Entry<String, DataClassBrick> inner : thisAsCDCB.inners.entrySet()) {
        if (inner.getKey().equals(targetName)) {
          containsName = true;
          break;
        }
      }
    } else if(this instanceof LayeredDataClassBrick) {
      LayeredDataClassBrick thisAsLDCB = (LayeredDataClassBrick) this;
      for(CompoundDataClassBrick layer : thisAsLDCB.layers) {
        if (layer.containsName(targetName)) {
          containsName = true;
          break;
        }
      }
    }
    return containsName;
  }

  protected HashSet<PrimitiveDataClassBrick> getAllUnsetNeighborsFromOuters(HashSet<DataClassBrick> dcbsAlreadySearched) {
    HashSet<PrimitiveDataClassBrick> unsetNeighbors = new HashSet<>();
    for(OuterDataClassBrick outer : outers) {
      if(!dcbsAlreadySearched.contains(outer)) {
        if (outer instanceof CompoundDataClassBrick) {
          unsetNeighbors.addAll(getUnsetInnersOfCDCB(dcbsAlreadySearched, (CompoundDataClassBrick) outer));
        } else {
          unsetNeighbors.addAll(getUnsetInnersOfLDCB(dcbsAlreadySearched, (LayeredDataClassBrick) outer));
        }
        if (outer.getOuters() != null) {
          for (OuterDataClassBrick outerOfOuter : outer.getOuters()) {
            unsetNeighbors = outerOfOuter.getAllUnsetNeighborsFromOuters(dcbsAlreadySearched);
          }
        }
        dcbsAlreadySearched.add(outer);
      }
    }
    return unsetNeighbors;
  }

  private HashSet<PrimitiveDataClassBrick> getUnsetInnersOfCDCB(HashSet<DataClassBrick> dcbsAlreadySearched, CompoundDataClassBrick cdcb) {
    HashSet<PrimitiveDataClassBrick> unsetInners = new HashSet<>();
    for(DataClassBrick inner : cdcb.inners.values()) {
      if(!(dcbsAlreadySearched.contains(inner) || inner.isComplete())) {
        if (inner instanceof PrimitiveDataClassBrick) {
          unsetInners.add((PrimitiveDataClassBrick) inner);
        } else {
          final HashSet<PrimitiveDataClassBrick> unsetInnersOfInner = getUnsetInnersOfCDCB(dcbsAlreadySearched, (CompoundDataClassBrick) inner);
          unsetInners.addAll(unsetInnersOfInner);
        }
      }
      dcbsAlreadySearched.add(inner);
    }
    return unsetInners;
  }

  private HashSet<PrimitiveDataClassBrick> getUnsetInnersOfLDCB(HashSet<DataClassBrick> dcbsAlreadySearched, LayeredDataClassBrick ldcb) {
    HashSet<PrimitiveDataClassBrick> unsetInners = new HashSet<>();
    for(CompoundDataClassBrick layer : ldcb.layers) {
      if(!(dcbsAlreadySearched.contains(layer) || layer.isComplete())) {
        final HashSet<PrimitiveDataClassBrick> unsetInnersOfLayer = getUnsetInnersOfCDCB(dcbsAlreadySearched, layer);
        unsetInners.addAll(unsetInnersOfLayer);
      }
      dcbsAlreadySearched.add(layer);
    }
    return unsetInners;
  }

}
