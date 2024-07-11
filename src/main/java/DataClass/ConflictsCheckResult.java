package DataClass;

public enum ConflictsCheckResult {
  yes, no, maybe;

  public static ConflictsCheckResult getMostCertainResult(int minNumberOfCertainResults, ConflictsCheckResult... results) {
    int yeses = 0;
    for(ConflictsCheckResult r : results) {
      if(r == ConflictsCheckResult.yes) yeses++;
      if(yeses >= minNumberOfCertainResults) return yes;
    }
    int maybes = 0;
    for(ConflictsCheckResult r : results) {
      if(r == ConflictsCheckResult.yes || r == ConflictsCheckResult.maybe) maybes++;
      if(maybes >= minNumberOfCertainResults) return maybe;
    }
    return ConflictsCheckResult.no;
  }
}
