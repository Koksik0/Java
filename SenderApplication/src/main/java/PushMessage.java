public class PushMessage extends Message {
  private final String messageTitle;
  private final String messageBody;

  public PushMessage(String messageTitle, String messageBody) {
    this.messageTitle = messageTitle;
    this.messageBody = messageBody;
  }

  @Override
  public String getMessageTitle() {
    return messageTitle;
  }

  @Override
  public String getMessageBody() {
    return messageBody;
  }

  @Override
  void validateMessage() throws SenderException {
    validateMessageTitleAndBody();
    if(messageBody.length()>=256)
      throw new SenderException("Message is too long! The message should be 256 characters or less");
  }
}
