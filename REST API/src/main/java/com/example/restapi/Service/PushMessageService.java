package com.example.restapi.Service;

import com.example.restapi.PushMessage;
import com.example.restapi.PushRecipient;
import com.example.restapi.PushSender;
import com.example.restapi.SenderException;
import org.springframework.stereotype.Service;

@Service("pushMessageService")
public class PushMessageService {
  public String sendPush(String address, String title, String message) throws SenderException {
    PushRecipient pushRecipient = new PushRecipient(address);
    PushMessage pushMessage = new PushMessage(title, message);
    new PushSender().send(pushMessage, pushRecipient);
    return "Ok";
  }
}
