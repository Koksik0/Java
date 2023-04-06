import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class PushClient {

  static final int PORT = 27182;

  public static void main(String[] args) {

    try (Socket echoSocket = new Socket("localhost", PORT);
        BufferedReader in =
            new BufferedReader(new InputStreamReader(echoSocket.getInputStream()))) {
      String mesage = in.readLine();
      while (mesage != null) {
        System.out.println(mesage);
        mesage = in.readLine();
      }

    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: localhost.");
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for " + "the connection to: localhost.");
      System.exit(1);
    }
  }
}
