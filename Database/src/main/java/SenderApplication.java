import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SenderApplication {

  private static final Logger logger = LoggerFactory.getLogger(SenderApplication.class);

  public static void main(String[] args) throws Exception {

    if (args.length == 5) {
      final int numberOfEnqueuingThreads = Integer.parseInt(args[0]);
      final int numberOfSendingThreads = Integer.parseInt(args[1]);
      DatabaseServices databaseServices = new DatabaseServices(args[2], args[3], args[4]);
      databaseServices.connect();
      logger.info(
          "There will be %d enqueuing threads and %d sender threads"
              .formatted(numberOfEnqueuingThreads, numberOfSendingThreads));

      EmailSender emailSender = new EmailSender();
      EmailMessageProvider messageProvider = new EmailMessageProvider();
      RecipientProvider recipientProvider = new EmailRecipientProvider();

      List<Thread> threads = new ArrayList<>();
      for (int i = 0; i < numberOfEnqueuingThreads; i++) {
        threads.add(
            new Thread(
                new EmailEnquerRunnable(messageProvider, recipientProvider, databaseServices)));
      }
      for (int i = 0; i < numberOfSendingThreads; i++) {
        threads.add(new Thread(new EmailSenderRunnable(emailSender, databaseServices)));
      }

      for (Thread thread : threads) {
        thread.start();
      }
      for (Thread thread : threads) {
        thread.join();
      }
      databaseServices.disconnect();

    } else {
      logger.error("Params should be: enqueuing-threads-count sender-threads-count");
      System.exit(-1);
    }
  }

  private static class EmailEnquerRunnable implements Runnable {

    private final EmailMessageProvider messageProvider;
    private final RecipientProvider recipientProvider;
    private DatabaseServices databaseServices;

    public EmailEnquerRunnable(
        EmailMessageProvider messageProvider,
        RecipientProvider recipientProvider,
        DatabaseServices databaseServices) {
      this.messageProvider = messageProvider;
      this.recipientProvider = recipientProvider;
      this.databaseServices = databaseServices;
    }

    @Override
    public void run() {
      Message nextMessage;
      do {
        nextMessage = messageProvider.getNextMessage();
        if (nextMessage != null) { // Wyślemy 100 wiadomości
          long messageId = databaseServices.saveMessageInDatabase(nextMessage);
          final Recipient nextRecipient = recipientProvider.getNextRecipient();
          long recipientId = databaseServices.saveRecipientInDatabase(nextRecipient);
          logger.info("Enqueueing message.");
          databaseServices.saveMessageInQueue(messageId, recipientId);
        }
      } while (nextMessage != null);
    }
  }

  private static class EmailSenderRunnable implements Runnable {

    private final EmailSender emailSender;
    private int counter = 0;
    private DatabaseServices databaseServices;

    public EmailSenderRunnable(EmailSender emailSender, DatabaseServices databaseServices) {
      this.emailSender = emailSender;
      this.databaseServices = databaseServices;
    }

    @Override
    public void run() {
      do {
        try {
          EmailQueue emailQueue = databaseServices.downloadEmailQueue();
          if (emailQueue != null) {
            if (databaseServices.changeStatusIdInEmailQueue(emailQueue.getEmailQueueId())) {
              logger.info("Delivering message to send.");
              emailSender.send(
                  databaseServices.downloadEmailMessage(emailQueue.getEmailMessageId()),
                  databaseServices.downloadEmailRecipient(emailQueue.getEmailRecipientId()));
              databaseServices.deleteInQueue(emailQueue.getEmailQueueId());
              counter = 0;
            }
          } else {
            logger.info("No email to send, waiting.");
            counter++;
            sleep(100); // wait for new element in the queue
          }
        } catch (SenderException | InterruptedException e) {
          logger.error("Couldn't send a message", e);
        }
      } while (counter < 100);
    }
  }
}
