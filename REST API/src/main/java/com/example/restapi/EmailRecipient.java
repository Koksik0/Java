package com.example.restapi;

import com.mysql.cj.util.StringUtils;

import java.util.regex.Pattern;

public class EmailRecipient extends Recipient {

  private final String recipientAddress;

  public EmailRecipient(String recipientAddress) {
    this.recipientAddress = recipientAddress;
  }

  public String getRecipientAddress() {
    return recipientAddress;
  }

  @Override
  void validateRecipient() throws SenderException {
    if (StringUtils.isEmptyOrWhitespaceOnly(recipientAddress))
      throw new SenderException("Recipient address is null");

    String regexPattern =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    boolean isCorrectRecipientAddress =
        Pattern.compile(regexPattern).matcher(recipientAddress.trim()).matches();
    if (!isCorrectRecipientAddress) throw new SenderException("Invalid recipient address");
  }

  @Override
  String anonymize() {
    String[] temp = recipientAddress.split("@");
    StringBuilder stars = new StringBuilder();
    stars.append("*".repeat(temp[0].length()));
    return stars + "@" + temp[1];
  }
}
