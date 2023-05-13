import common.RegisteredUser;
import common.TextMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class is responsible for testing the functionality of the TextMessage class.
 */
public class TextMessageTest {

    /**
     * This test ensures that the correct text is retrieved from a TextMessage object.
     */
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

    /**
     * This test verifies that the correct sender is retrieved from a TextMessage object,
     * and that the method does not return incorrect senders.
     */
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
