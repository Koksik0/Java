import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThreadTest {
    @Test
    void testValidateRecipient(){
        try {
            EmailRecipient emailRecipient = new EmailRecipient("aaa");
            emailRecipient.validateRecipient();
        }catch (SenderException senderException){
            assertEquals("Invalid recipient address",senderException.getMessage());
        }

        try {
            EmailRecipient emailRecipient = new EmailRecipient("uj@@gmail.com");
            emailRecipient.validateRecipient();
        }catch (SenderException senderException){
            assertEquals("Invalid recipient address",senderException.getMessage());
        }

        try {
            EmailRecipient emailRecipient = new EmailRecipient(null);
            emailRecipient.validateRecipient();
        }catch (SenderException senderException){
            assertEquals("Recipient address is null",senderException.getMessage());
        }

        try {
            PushRecipient pushRecipient = new PushRecipient("&*(45678901234567890123456712!)_");
            pushRecipient.validateRecipient();
        }catch (SenderException senderException){
            assertEquals("Invalid recipient address",senderException.getMessage());
        }

        try {
            PushRecipient pushRecipient = new PushRecipient("123");
            pushRecipient.validateRecipient();
        }catch (SenderException senderException){
            assertEquals("Recipient address is too short or too long. The recipient address should be 32 characters",senderException.getMessage());
        }

        try {
            PushRecipient pushRecipient = new PushRecipient(null);
            pushRecipient.validateRecipient();
        }catch (SenderException senderException){
            assertEquals("Recipient address is null",senderException.getMessage());
        }
    }

    @Test
    void testEmailRecipientAnonymize(){
        assertEquals("***@gmail.com", new EmailRecipient("aaa@gmail.com").anonymize());
        assertEquals("******@gmail.com", new EmailRecipient("qwerty@gmail.com").anonymize());
        assertEquals("*********@gmail.com", new EmailRecipient("asdfghjkl@gmail.com").anonymize());
    }

    @Test
    void testPushRecipientAnonymize(){
        assertEquals("...klzxc", new PushRecipient("1234567890qwertyuiopasdfghjklzxc").anonymize());
        assertEquals("...7vf8w", new PushRecipient("bdyugwybdsya7gd8327gvd7w3g87vf8w").anonymize());
        assertEquals("...32783", new PushRecipient("h384tr378tr87gweq87fg3287f832783").anonymize());
    }

    @Test
    void givenPassword_whenHashingUsingCommons_thenVerifying() {

        String hash = "35454B055CC325EA1AF2126E27707052";
        String password = "ILoveJava";
        String md5Hex = DigestUtils.md5Hex(password).toUpperCase();
        Assertions.assertEquals(md5Hex, hash);
    }

    @Test
    void testValidatePushMessage(){
        try{
            PushMessage pushMessage = new PushMessage("","Co tam?");
            pushMessage.validateMessage();
        } catch (SenderException senderException){
            assertEquals("Message title is empty",senderException.getMessage());
        }

        try{
            PushMessage pushMessage = new PushMessage("Witam","");
            pushMessage.validateMessage();
        } catch (SenderException senderException){
            assertEquals("Message body is empty",senderException.getMessage());
        }

        try{
            PushMessage pushMessage = new PushMessage("Witam","gfouegywfuoysdfougywefuydsuifygewofyugo" +
                    "fbauygfudsbfuyawfuydbskuyfgdsoufbdsuiabfuayewgfocsduybfouagyewfouysdavbofuweygafouyvbsduycgewaufybcsuydocvy" +
                    "cbauweydgsfuydsabfuoyagewfuydsgofyubevaowfygbodsuyvocvyewfoycsgdvaociuvwegofybvosduigfpiewufboguisabvdfiuewgfoy" +
                    "dsfbawuegyfouaydsvfoauydgfohvsdoufyGboadsuyfbodsyugfouydsbafuygeaoyfvusdygfoavyewfoudsayvgoufcfyvewoufyvsdaouyvf");
            pushMessage.validateMessage();
        } catch (SenderException senderException){
            assertEquals("Message is too long! The message should be 256 characters or less",senderException.getMessage());
        }
    }
    @Test
    void testValidateEmailMessage(){
        try{
            EmailMessage emailMessage = new EmailMessage("","Co tam?");
            emailMessage.validateMessage();
        } catch (SenderException senderException){
            assertEquals("Message title is empty",senderException.getMessage());
        }

        try{
            EmailMessage emialMessage = new EmailMessage("Witam","");
            emialMessage.validateMessage();
        } catch (SenderException senderException){
            assertEquals("Message body is empty",senderException.getMessage());
        }
    }
}
