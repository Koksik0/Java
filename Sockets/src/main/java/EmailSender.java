import static java.lang.Thread.sleep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSender implements Sender {

  private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

  @Override
  public void send(Message message, Recipient recipient)
          throws SenderException, InterruptedException {
    if(!(message instanceof EmailMessage))
      throw new SenderException("message is not EmailMessage");
    if(!(recipient instanceof EmailRecipient))
      throw new SenderException("recipient is not EmailRecipient");

    message.validateMessage();
    logger.info("Message validation ok");
    recipient.validateRecipient();
    logger.info("Recipient validation ok");

    String bodyMD5 = message.anonymizeMessageBody();
    String anonymizedRecipientAddress = recipient.anonymize();
    sleep(5000); // sending

    /* Use System.out to graphically distinguish sending from logging */
    System.out.printf("[Email] Message sent, title= '%s', bodyMD5= '%s', recipient= '%s'%n",
        message.getMessageTitle(), bodyMD5, anonymizedRecipientAddress);
  }
}
