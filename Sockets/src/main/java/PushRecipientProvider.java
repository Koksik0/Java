import org.apache.commons.lang3.RandomStringUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PushRecipientProvider extends Thread {
  private List<Recipient> recipientList = new ArrayList<>();
  private List<DataOutputStream> dataOutputStreamList = new ArrayList<>();
  private Random random = new Random();
  private int port;

  public void initializeServer() {
    ServerSocket serverSocket = null;
    Socket socket = null;

    try {
      serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      e.printStackTrace();
    }
    while (true) {
      try {
        socket = serverSocket.accept();
      } catch (IOException e) {
        System.out.println("I/O error: " + e);
      }
      new EchoThread(socket).start();
      recipientList.add(generateNextRecipient());
      try {
        dataOutputStreamList.add(new DataOutputStream(socket.getOutputStream()));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void initialize(int port) {
    this.port = port;
    Thread r = new Thread(this::initializeServer);
    r.start();
  }

  private Recipient generateNextRecipient() {
    String generateNextRecipient =
        "111111111111111111111111111" + RandomStringUtils.random(5, true, true);
    return new PushRecipient(generateNextRecipient);
  }

  public Recipient getNextRecipient() {
    if (recipientList.size() == 0) {
      return null;
    }
    int next = random.nextInt(0, recipientList.size());
    return recipientList.get(next);
  }

  public Map<Recipient, DataOutputStream> getData() {
    Map<Recipient, DataOutputStream> map = new HashMap<>();
    for (int x = 0; x < recipientList.size(); x++) {
      map.put(recipientList.get(x), dataOutputStreamList.get(x));
    }
    return map;
  }

  public void updateListOfClient(Recipient recipient) {
    for (int x = 0; x < recipientList.size(); x++) {
      if (recipientList.get(x).equals(recipient)) {
        recipientList.remove(x);
        dataOutputStreamList.remove(x);
      }
    }
  }
}
