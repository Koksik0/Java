public interface Sender {
  void send(Message message, Recipient recipient) throws SenderException, InterruptedException;
}
