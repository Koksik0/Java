package com.example.restapi;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestApplicationTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(
            strings = {
                    "",
                    "null",
                    "thisIsNotAValidEmail",
                    "smithexample.com",
                    "smith@examp@le.com",
                    "MR.Smith+example.com"
            })
    void testEmptyOrInvalidEmailRecipient(String invalidValue) {
        EmailSender sender = new EmailSender();
        Message message = new EmailMessage("Test title", "Test message");
        Recipient recipient = new EmailRecipient(invalidValue);
        assertThrows(SenderException.class, () -> sender.send(message, recipient));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(
            strings = {
                    "null",
                    "1234567890poiuytrewqasdfghjklmn_",
                    "smithexample.com",
                    "gweurg372gr87egf87w3g7fvgew87t37fvg8o7wt37g38",
                    "!w87g-8o7q3dv8ag378g87fa873fg7y+"
            })
    void testEmptyOrInvalidPushRecipient(String invalidValue) {
        PushSender sender = new PushSender();
        Message message = new PushMessage("Test title", "Test message");
        Recipient recipient = new PushRecipient(invalidValue);
        assertThrows(SenderException.class, () -> sender.send(message, recipient));
    }

    @Test
    void testEmailRecipientAnonymize() {
        assertEquals("***@gmail.com", new EmailRecipient("aaa@gmail.com").anonymize());
        assertEquals("******@gmail.com", new EmailRecipient("qwerty@gmail.com").anonymize());
        assertEquals("*********@gmail.com", new EmailRecipient("asdfghjkl@gmail.com").anonymize());
    }

    @Test
    void testPushRecipientAnonymize() {
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

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyTitlePushMessage(String title) {
        PushMessage pushMessage = new PushMessage(title, "Co tam?");
        assertThrows(SenderException.class, pushMessage::validateMessage);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyTitleEmailMessage(String title) {
        EmailMessage emailMessage = new EmailMessage(title, "Co tam?");
        assertThrows(SenderException.class, emailMessage::validateMessage);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(
            strings = {
                    "dhgauyfeguwfuidGYUEYgfydvyufcgeavyfcuGESUvfbdzygfuysducgyuzysgefyg"
                            + "vasugfuydzgLFUydsgufgdusgfuisdbfviulzdgfdvbszluicgdlszgvliudszgv"
                            + "vbduzsykgfkuzxblczgdslcbldzxgfldszbvciesgflubvdjhgfuyegfhbdyzsugf"
                            + "fzsuykgfdukzysgfuzesgfludsflyugeulbfludgzsflibelsblsvczduygedhfuweuyfgsdayeg"
                            + "fhaeruygfuydsgufyagwefuydsakuyfegwyfuyksdageiygfuysdgafgeyfoegfugduyfsgeyifgewa"
                            + "aehufgaseoufgeiofgusaygeufoguesgfesyfguyawegfuygdsavuyfkgwecuyksgeyfvekaugyfdav"
            })
    void testEmptyOrTooLongMessageBodyPushMessage(String messageBody) {
        PushMessage pushMessage = new PushMessage("Witam", messageBody);
        assertThrows(SenderException.class, pushMessage::validateMessage);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyBodyMessageEmailMessage(String messageBody) {
        EmailMessage emailMessage = new EmailMessage("Witam", messageBody);
        assertThrows(SenderException.class, emailMessage::validateMessage);
    }
}

