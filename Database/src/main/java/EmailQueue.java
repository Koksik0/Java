import java.sql.Timestamp;

public class EmailQueue {

  final Timestamp creationDate;
  final long emailMessageId;
  final long emailRecipientId;
  Timestamp modificationDate;
  Long emailQueueId = null;
  long statusId = EmailQueueStatus.AWAITING.getId();

  public EmailQueue(Timestamp creationDate, long emailMessageId, long emailRecipientId) {
    this.creationDate = creationDate;
    this.modificationDate = creationDate;
    this.emailMessageId = emailMessageId;
    this.emailRecipientId = emailRecipientId;
  }

  public EmailQueue(
      Long emailQueueId,
      Timestamp creationDate,
      Timestamp modificationDate,
      long statusId,
      long emailMessageId,
      long emailRecipientId) {
    this.emailQueueId = emailQueueId;
    this.creationDate = creationDate;
    this.modificationDate = modificationDate;
    this.statusId = statusId;
    this.emailMessageId = emailMessageId;
    this.emailRecipientId = emailRecipientId;
  }

  public EmailQueue copy() {
    return new EmailQueue(
        this.emailQueueId,
        this.creationDate,
        this.modificationDate,
        this.statusId,
        this.emailMessageId,
        this.emailRecipientId);
  }

  public Long getEmailQueueId() {
    return emailQueueId;
  }

  public void setEmailQueueId(Long emailQueueId) {
    this.emailQueueId = emailQueueId;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public Timestamp getModificationDate() {
    return modificationDate;
  }

  public void setModificationDate(Timestamp modificationDate) {
    this.modificationDate = modificationDate;
  }

  public long getStatusId() {
    return statusId;
  }

  public void setStatusId(long statusId) {
    this.statusId = statusId;
  }

  public long getEmailMessageId() {
    return emailMessageId;
  }

  public long getEmailRecipientId() {
    return emailRecipientId;
  }
}
