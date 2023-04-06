package com.example.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushSender implements Sender {

  private static final Logger logger = LoggerFactory.getLogger(PushSender.class);

  @Override
  public void send(Message message, Recipient recipient) throws SenderException {

    if (!(message instanceof PushMessage)) throw new SenderException("message in not PushMessage");
    if (!(recipient instanceof PushRecipient))
      throw new SenderException("recipient in not PushRecipient");

    message.validateMessage();
    logger.info("Validation message Ok");
    recipient.validateRecipient();
    logger.info("Validation Recipient Ok");

    String bodyMD5 = message.anonymizeMessageBody();
    String anonymizedRecipientAddress = recipient.anonymize();
    System.out.printf(
        "[Push] Message sent, title= '%s', bodyMD5= '%s', recipient= '%s'%n",
        message.getMessageTitle(), bodyMD5, anonymizedRecipientAddress);
  }
}
