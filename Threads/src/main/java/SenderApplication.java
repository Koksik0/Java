import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderApplication {

  private static final int THREADS_COUNT = 4;
  private static final Logger logger = LoggerFactory.getLogger(SenderApplication.class);
  static int messagesSent = 0;

  static synchronized void increase() {
    messagesSent++;
  }

  public static void main(String[] args) throws InterruptedException {

    EmailSender emailSender = new EmailSender();
    MessageProvider messageProvider = new EmailMessageProvider();
    RecipientProvider recipientProvider = new EmailRecipientProvider();

    Runnable sendEmails =
        () -> {
          Message message = messageProvider.getNextMessage();
          Recipient recipient = recipientProvider.getNextRecipient();
          while (message != null) {
            try {

              emailSender.send(message, recipient);

              logger.info("Messege %s sent to %s".formatted(message, recipient));
              increase();

            } catch (SenderException | InterruptedException e) {
              throw new RuntimeException(e);
            }
            message = messageProvider.getNextMessage();
            recipient = recipientProvider.getNextRecipient();
          }
        };

    Thread[] threads = new Thread[THREADS_COUNT];

    for (int i = 0; i < THREADS_COUNT; i++) {
      Thread t = new Thread(sendEmails);
      threads[i] = t;
      t.start();
    }

    for (int i = 0; i < THREADS_COUNT; i++) {
      threads[i].join();
    }

    logger.info("Total %d messages sent".formatted(messagesSent));
  }
}
