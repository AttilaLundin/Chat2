package common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TextMessageTest {

    @Test
    public void testGetText() {
        String messageText = "Hello YADZILA!";
        RegisteredUser sender = new RegisteredUser.RegisteredUserBuilder().username("User1").password("user1").build();

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
        RegisteredUser sender = new RegisteredUser.RegisteredUserBuilder().username("User1").password("user1").build();
        RegisteredUser NOTSender = new RegisteredUser.RegisteredUserBuilder().username("USER2").password("user2").build();
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
