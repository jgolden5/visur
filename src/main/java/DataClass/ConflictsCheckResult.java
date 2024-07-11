package DataClass;

public enum ConflictsCheckResult {
  yes, no, maybe;

  public static ConflictsCheckResult getMostCertainResult(ConflictsCheckResult... results) {
    for(ConflictsCheckResult r : results) {
      if(r == ConflictsCheckResult.yes) return ConflictsCheckResult.yes;
    }
    for(ConflictsCheckResult r : results) {
      if(r == ConflictsCheckResult.maybe) return ConflictsCheckResult.maybe;
    }
    return ConflictsCheckResult.no;
  }
}
