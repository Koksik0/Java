import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class PushSender implements Sender {
  private PushRecipientProvider pushRecipientProvider;

  public PushSender(PushRecipientProvider pushRecipientProvider) {
    this.pushRecipientProvider = pushRecipientProvider;
  }

  @Override
  public void send(Message message, Recipient recipient) throws SenderException {

    if (!(message instanceof PushMessage)) throw new SenderException("message in not PushMessage");
    if (!(recipient instanceof PushRecipient))
      throw new SenderException("recipient in not PushRecipient");

    message.validateMessage();
    recipient.validateRecipient();

    String bodyMD5 = message.anonymizeMessageBody();
    String anonymizedRecipientAddress = recipient.anonymize();

    Map<Recipient, DataOutputStream> map = pushRecipientProvider.getData();
    try {
      map.get(recipient).writeBytes(message + "\n");
      map.get(recipient).flush();

    } catch (IOException e) {
      pushRecipientProvider.updateListOfClient(recipient);
    }

    System.out.printf(
            "[Push] Message sent, title= '%s', bodyMD5= '%s', recipient= '%s'%n",
            message.getMessageTitle(), bodyMD5, anonymizedRecipientAddress);
  }
}
