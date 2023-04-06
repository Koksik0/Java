public class EmailMessage extends Message {

  private final String messageTitle;
  private final String messageBody;

  public EmailMessage(String messageTitle, String messageBody) {
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
  }
}
