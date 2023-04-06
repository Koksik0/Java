package com.example.restapi.Service;

import com.example.restapi.EmailMessage;
import com.example.restapi.EmailRecipient;
import com.example.restapi.EmailSender;
import com.example.restapi.SenderException;
import org.springframework.stereotype.Service;

@Service("emailMessageService")
public class EmailMessageService {
  public String sendEmail(String address, String title, String message)
      throws SenderException, InterruptedException {
    EmailMessage emailMessage = new EmailMessage(title, message);
    EmailRecipient emailRecipient = new EmailRecipient(address);
    new EmailSender().send(emailMessage, emailRecipient);
    return "Ok";
  }
}
