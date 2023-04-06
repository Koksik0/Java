import java.util.Date;

import static java.lang.Thread.sleep;

public class PushMessageProvider {

  public synchronized Message getNextMessage() throws InterruptedException {
    Date date = new Date();
    sleep(2000);
    return new PushMessage("Title", "Now is " + date.getTime());
  }
}
