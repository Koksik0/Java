package com.example.restapi;

public class SenderException extends Exception {
  public SenderException() {}

  public SenderException(String message) {
    super(message);
  }
}
