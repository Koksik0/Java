import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DatabaseServices {
  private static final Logger logger = LoggerFactory.getLogger(DatabaseServices.class);
  private Connection connection;

  private String url;
  private String username;
  private String password;

  public DatabaseServices(String url, String username, String password) {
    this.url = url;
    this.username = username;
    this.password = password;
  }

  public synchronized void connect() throws Exception {
    if (connection == null) {
      logger.info("Connecting to %s".formatted(url));
      try {
        connection = DriverManager.getConnection(url, username, password);
      } catch (SQLException ex) {
        throw new Exception("Error connecting to %s".formatted(url), ex);
      }
    } else {
      throw new Exception("Already connected to %s".formatted(url));
    }
  }

  public synchronized void disconnect() throws Exception {
    logger.info("Closing connection to %s".formatted(url));
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException ex) {
        throw new Exception("Can't close connection %s".formatted(url), ex);
      }
    }
  }

  private ResultSet executeQuery(String statement) throws Exception {
    if (connection != null) {
      Statement cStatement = connection.createStatement();
      ResultSet resultSet = cStatement.executeQuery(statement);
      return resultSet;
    } else {
      throw new Exception("Not connected to %s".formatted(url));
    }
  }

  private long executeUpdate(String statement, boolean returnGeneratedKeys) throws Exception {
    if (connection != null) {
      try {
        try (Statement cStatement = connection.createStatement()) {
          long updated = cStatement.executeUpdate(statement, Statement.RETURN_GENERATED_KEYS);
          if (returnGeneratedKeys) { // How to determine success of an insert/update
            final ResultSet rs = cStatement.getGeneratedKeys();
            // Get id of the last inserted value
            if (rs.next()) {
              return rs.getLong(1);
            }
          }
          return updated;
        }
      } catch (SQLException ex) {
        throw new Exception("Can't execute statement '%s'".formatted(statement), ex);
      }
    } else {
      throw new Exception("Not connected to %s".formatted(url));
    }
  }

  public long saveMessageInDatabase(Message message) {
    String title = message.getMessageTitle();
    String body = message.getMessageBody();
    try {
      return executeUpdate(
          "INSERT INTO uj_sender.email_message(message_title, message_body) VALUES(\'"
              + title
              + "\',\'"
              + body
              + "\')",
          true);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public long saveRecipientInDatabase(Recipient recipient) {
    String emailRecipient = recipient.getRecipientAddress();
    ResultSet resultSet = null;
    try {
      resultSet =
          executeQuery(
              "SELECT uj_sender.email_recipient.email_recipient_id FROM uj_sender.email_recipient WHERE recipient_address = \'"
                  + emailRecipient
                  + "\' LIMIT 1");
      if (resultSet.next()) {
        long emailRecipientId = resultSet.getLong("email_recipient_id");
        resultSet.close();
        return emailRecipientId;
      } else {
        return executeUpdate(
            "INSERT INTO uj_sender.email_recipient(recipient_address) VALUES(\'"
                + emailRecipient
                + "\')",
            true);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void saveMessageInQueue(long messageId, long recipientId) {
    try {
      executeUpdate(
          "INSERT INTO uj_sender.email_queue (email_message_id,email_recipient_id) VALUES (\'"
              + messageId
              + "\',\'"
              + recipientId
              + "\')",
          false);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public EmailQueue downloadEmailQueue() {
    try {
      ResultSet resultSet =
          executeQuery("SELECT * FROM uj_sender.email_queue WHERE status_id = 0 LIMIT 1");
      if (resultSet.next()) {
        return new EmailQueue(
            resultSet.getLong("email_queue_id"),
            resultSet.getTimestamp("creation_date"),
            resultSet.getTimestamp("modification_date"),
            resultSet.getLong("status_id"),
            resultSet.getLong("email_message_id"),
            resultSet.getLong("email_recipient_id"));
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Message downloadEmailMessage(long messageId) {
    try {
      ResultSet resultSet =
          executeQuery(
              "SELECT * FROM uj_sender.email_message WHERE email_message_id = \'"
                  + messageId
                  + "\' LIMIT 1");
      if (resultSet.next()) {
        return new EmailMessage(
            resultSet.getString("message_title"), resultSet.getString("message_body"));
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Recipient downloadEmailRecipient(long recipientId) {
    try {
      ResultSet resultSet =
          executeQuery(
              "SELECT * FROM uj_sender.email_recipient WHERE email_recipient_id = \'"
                  + recipientId
                  + "\' LIMIT 1");
      if (resultSet.next()) {
        return new EmailRecipient(resultSet.getString("recipient_address"));
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteInQueue(long queueId) {
    try {
      executeUpdate(
          "DELETE FROM uj_sender.email_queue WHERE email_queue_id = \'"
              + queueId
              + "\' AND status_id = 1",
          false);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public boolean changeStatusIdInEmailQueue(long queueId) {
    try {
      long result =
          executeUpdate(
              "UPDATE uj_sender.email_queue SET status_id = 1 WHERE email_queue_id = \'"
                  + queueId
                  + "\' AND status_id = 0",
              false);
      if (result > 0) {
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
