package testing;


import common.RegisteredUser;
import common.TextMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TextMessageTest {

    @Test
    public void testGetText() {
        String messageText = "Hello YADZILA!";
        RegisteredUser sender = new RegisteredUser.UserBuilder().username("User1").password("user1").build();

        TextMessage textMessage = new TextMessage.TextMessageBuilder()
                .text(messageText)
                .sender(sender)
                .build();

        String retrievedText = textMessage.getText();

        assertEquals(messageText, retrievedText);
    }

    @Test
    public void testGetSender() {
        String messageText = "Hello ROZKEK";
        RegisteredUser sender = new RegisteredUser.UserBuilder().username("User1").password("user1").build();
        RegisteredUser NOTSender = new RegisteredUser.UserBuilder().username("USER2").password("user2").build();
        TextMessage textMessage = new TextMessage.TextMessageBuilder()
                .text(messageText)
                .sender(sender)
                .build();

        RegisteredUser retrievedSender = textMessage.getSender();

        assertNotNull(retrievedSender);
        assertEquals(sender, retrievedSender);
        assertNotEquals(NOTSender,retrievedSender);
    }
}
