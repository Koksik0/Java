public enum EmailQueueStatus {
  AWAITING(0, "Queue element is waiting to be processed"),
  IN_PROGRESS(1, "Queue element is being processed"),
  ;

  private final int id;
  private final String description;

  EmailQueueStatus(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public static EmailQueueStatus getEnumById(int code) {
    for (EmailQueueStatus e : EmailQueueStatus.values()) {
      if (code == e.getId()) {
        return e;
      }
    }
    return null;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }
}
