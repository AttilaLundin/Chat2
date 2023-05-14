import common.ChatRoom;
import common.Message;
import common.RegisteredUser;
import common.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is responsible for testing the functionality of the ChatRoom class.
 * @author Odai Alrahem
 */
public class ChatRoomTest {

    private RegisteredUser user1;
    private RegisteredUser user2;
    private RegisteredUser user3;
    private ChatRoom chatRoom1;
    private ChatRoom chatRoom2;
    private ChatRoom chatRoom3;
    private TextMessage message1;
    private TextMessage message2;
    private TextMessage message3;

    /**
     * This method sets up the testing environment before each test is run.
     */
    @BeforeEach
    public void setup() {
        user1 = new RegisteredUser.RegisteredUserBuilder().username("testUser1").password("testPassword1").build();
        user2 = new RegisteredUser.RegisteredUserBuilder().username("testUser2").password("testPassword2").build();
        user3 = new RegisteredUser.RegisteredUserBuilder().username("testUser3").password("testPassword3").build();
        List<RegisteredUser> members = new ArrayList<>();
        members.add(user1);
        members.add(user2);
        members.add(user3);

        List<Message> messages = new ArrayList<>();
        message1 = new TextMessage.TextMessageBuilder().text("testMessage1").sender(user1).build();
        message2 = new TextMessage.TextMessageBuilder().text("testMessage2").sender(user2).build();
        message3 = new TextMessage.TextMessageBuilder().text("testMessage3").sender(user3).build();
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);

        chatRoom1 = new ChatRoom("testChatRoom1", members, messages);
        chatRoom2 = new ChatRoom("testChatRoom2", members, messages);
        chatRoom3 = new ChatRoom("testChatRoom3", members, messages);
    }

    /**
     * This test ensures that the correct users are returned for a chat room.
     */
    @Test
    public void testGetUsers() {
        List<RegisteredUser> users = chatRoom1.getUsers();
        assertEquals(3, users.size());
        assertTrue(users.contains(user1) && users.contains(user2) && users.contains(user3));
    }

    /**
     * This test verifies that the chat room ID is correctly returned.
     */
    @Test
    public void testGetChatRoomID() {
        assertNotNull(chatRoom1.getChatRoomID());
        assertNotNull(chatRoom2.getChatRoomID());
        assertNotNull(chatRoom3.getChatRoomID());
    }

    /**
     * This test ensures that the correct users are returned for a chat room.
     */
    @Test
    public void testGetUsersInChatRoom() {
        List<RegisteredUser> users = chatRoom1.getUsersInChatRoom();
        assertEquals(3, users.size());
        assertTrue(users.contains(user1) && users.contains(user2) && users.contains(user3));
    }

    /**
     * This test verifies that the correct messages are returned for a chat room.
     */
    @Test
    public void testGetMessages() {
        List<Message> messages = chatRoom1.getMessages();
        assertEquals(3, messages.size());
        assertTrue(messages.contains(message1) && messages.contains(message2) && messages.contains(message3) );
    }

    /**
     * This test checks if adding a text message to a chat room works correctly.
     */
    @Test
    public void testAddTextMessage() {
        Message newMessage = new TextMessage.TextMessageBuilder().text("testMesage").sender(user1).build();
        chatRoom1.addMessage(newMessage);
        List<Message> messages = chatRoom1.getMessages();
        assertEquals(4, messages.size());
        assertTrue(messages.contains(newMessage));
    }

    /**
     * This test checks if adding a list of messages to a chat room works correctly.
     */
    @Test
    public void testAddMessageList() {
        Message newMessage1 = new TextMessage.TextMessageBuilder().text("testMessage1").sender(user1).build();
        Message newMessage2 = new TextMessage.TextMessageBuilder().text("testMessage2").sender(user1).build();
        List<Message> newMessages = new ArrayList<>();
        newMessages.add(newMessage1);
        newMessages.add(newMessage2);
        chatRoom1.addMessageList(newMessages);
        List<Message> messages = chatRoom1.getMessages();
        assertEquals(2, messages.size());
        assertTrue(messages.containsAll(newMessages));
    }

    /**
     * This test verifies that the correct chat room name is returned.
     */
    @Test
    public void testGetChatRoomName() {
        assertEquals("testChatRoom1", chatRoom1.getChatRoomName());
        assertEquals("testChatRoom2", chatRoom2.getChatRoomName());
        assertEquals("testChatRoom3", chatRoom3.getChatRoomName());
    }
}
