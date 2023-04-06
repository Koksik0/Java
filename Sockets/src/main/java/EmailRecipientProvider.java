import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class EmailRecipientProvider extends RecipientProvider {
  Random random = new Random();

  @Override
  public Recipient getNextRecipient() {
    int length = random.nextInt(1, 10);
    boolean useLetters = true;
    boolean useNumbers = true;
    String firstPart = RandomStringUtils.random(length, useLetters, useNumbers);
    String secondPart = "@domain.com";
    String emailAddress = firstPart + secondPart;
    return new EmailRecipient(emailAddress);
  }
}
