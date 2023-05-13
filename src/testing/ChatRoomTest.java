package common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ChatRoomTest {

    private ChatRoom chatRoom;
    private RegisteredUser user;
    private Message message;

    @BeforeEach
    public void setup() {
        user = new RegisteredUser.RegisteredUserBuilder().username("testUser").password("testPassword").build();
        message = new TextMessage.TextMessageBuilder().text("testMesage").sender(user).build();
        List<RegisteredUser> users = new ArrayList<>();
        users.add(user);
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        chatRoom = new ChatRoom("testChatRoom", users, messages);
    }

    @Test
    public void testGetUsers() {
        List<RegisteredUser> users = chatRoom.getUsers();
        assertEquals(1, users.size());
        assertTrue(users.contains(user));
    }

    @Test
    public void testGetChatRoomID() {
        assertNotNull(chatRoom.getChatRoomID());
    }

    @Test
    public void testGetUsersInChatRoom() {
        List<RegisteredUser> users = chatRoom.getUsersInChatRoom();
        assertEquals(1, users.size());
        assertTrue(users.contains(user));
    }

    @Test
    public void testGetMessages() {
        List<Message> messages = chatRoom.getMessages();
        assertEquals(1, messages.size());
        assertTrue(messages.contains(message));
    }

    @Test
    public void testAddMessage() {
        Message newMessage = new TextMessage.TextMessageBuilder().text("testMesage").sender(user).build();
        chatRoom.addMessage(newMessage);
        List<Message> messages = chatRoom.getMessages();
        assertEquals(2, messages.size());
        assertTrue(messages.contains(newMessage));
    }

    @Test
    public void testAddMessageList() {
        Message newMessage1 = new TextMessage.TextMessageBuilder().text("testMessage1").sender(user).build();
        Message newMessage2 = new TextMessage.TextMessageBuilder().text("testMessage2").sender(user).build();
        List<Message> newMessages = new ArrayList<>();
        newMessages.add(newMessage1);
        newMessages.add(newMessage2);
        chatRoom.addMessageList(newMessages);
        List<Message> messages = chatRoom.getMessages();
        assertEquals(2, messages.size());
        assertTrue(messages.containsAll(newMessages));
    }

    @Test
    public void testGetChatRoomName() {
        assertEquals("testChatRoom", chatRoom.getChatRoomName());
    }
}
