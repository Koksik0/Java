package com.example.restapi;

public abstract class Recipient {
  abstract String getRecipientAddress();

  abstract void validateRecipient() throws SenderException;

  abstract String anonymize();
}
