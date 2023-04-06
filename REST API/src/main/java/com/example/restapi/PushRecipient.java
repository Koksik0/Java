package com.example.restapi;

import com.mysql.cj.util.StringUtils;

import java.util.regex.Pattern;

public class PushRecipient extends Recipient {
  private final String recipientAddress;

  public PushRecipient(String recipientAddress) {
    this.recipientAddress = recipientAddress;
  }

  @Override
  public String getRecipientAddress() {
    return recipientAddress;
  }

  @Override
  void validateRecipient() throws SenderException {
    if (StringUtils.isEmptyOrWhitespaceOnly(recipientAddress))
      throw new SenderException("Recipient address is null");

    String regexPattern = "^[a-zA-Z0-9]*$";
    boolean isCorrectRecipientAddress =
        Pattern.compile(regexPattern).matcher(recipientAddress).matches();
    if (!isCorrectRecipientAddress) throw new SenderException("Invalid recipient address");
    if (recipientAddress.length() != 32)
      throw new SenderException(
          "Recipient address is too short or too long. The recipient address should be 32 characters");
  }

  @Override
  String anonymize() {
    return "..." + recipientAddress.substring(recipientAddress.length() - 5);
  }
}
