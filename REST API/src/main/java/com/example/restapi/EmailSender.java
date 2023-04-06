package com.example.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

public class EmailSender implements Sender {

  private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
  private String address;
  private String title;
  private String message;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public void send(Message message, Recipient recipient)
      throws SenderException, InterruptedException {
    if (!(message instanceof EmailMessage))
      throw new SenderException("message is not EmailMessage");
    if (!(recipient instanceof EmailRecipient))
      throw new SenderException("recipient is not EmailRecipient");

    message.validateMessage();
    logger.info("Validation message Ok");
    recipient.validateRecipient();
    logger.info("Validation Recipient Ok");
    String bodyMD5 = message.anonymizeMessageBody();
    String anonymizedRecipientAddress = recipient.anonymize();
    sleep(5000); // sending

    /* Use System.out to graphically distinguish sending from logging */
    System.out.printf(
        "[Email] Message sent, title= '%s', bodyMD5= '%s', recipient= '%s'%n",
        message.getMessageTitle(), bodyMD5, anonymizedRecipientAddress);
  }
}
