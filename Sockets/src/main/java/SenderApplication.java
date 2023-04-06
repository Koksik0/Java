public class SenderApplication {

  static final int PORT = 27182;

  public static void main(String[] args) throws InterruptedException, SenderException {

    PushMessageProvider pushMessageProvider = new PushMessageProvider();
    PushRecipientProvider pushRecipientProvider = new PushRecipientProvider();
    pushRecipientProvider.initialize(PORT);
    PushSender pushSender = new PushSender(pushRecipientProvider);

    Message nextMessage;
    do {
      nextMessage = pushMessageProvider.getNextMessage();
      Recipient nextRecipient = pushRecipientProvider.getNextRecipient();
      if (nextRecipient != null) {
        pushSender.send(nextMessage, nextRecipient);
      } else {
        System.out.println("Client is not connected");
      }
    } while (nextMessage != null);
  }
}
