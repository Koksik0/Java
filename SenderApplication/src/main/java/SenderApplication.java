import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderApplication {

  private static final Logger logger = LoggerFactory.getLogger(SenderApplication.class);

  public static void main(String[] args) throws SenderException, InterruptedException {

      //Symuluje wysłanie wiadomości email
      EmailMessage emailMessage = new EmailMessage("Siema","Co tam?");
      EmailRecipient emailRecipient = new EmailRecipient("abc@gmail.com");
      EmailSender emailSender = new EmailSender();
      emailSender.send(emailMessage,emailRecipient);
      logger.info("Ok");

      //Symuluję wysyłanie wiadomości push
      PushMessage pushMessage = new PushMessage("Witam","Jak leci?");
      PushRecipient pushRecipient = new PushRecipient("qwertyuiopAAAAAAAAAA123456789012");
      PushSender pushSender = new PushSender();
      pushSender.send(pushMessage,pushRecipient);
      logger.info("Ok");
  }
}
