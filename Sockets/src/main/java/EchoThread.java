import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class EchoThread extends Thread {
  protected Socket socket;

  public EchoThread(Socket clientSocket) {
    this.socket = clientSocket;
  }

  public void run() {
    try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
      try {
        String message = in.readLine();
        while (message != null) {
          out.writeBytes(message + "\n");
          out.flush();
          message = in.readLine();
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
