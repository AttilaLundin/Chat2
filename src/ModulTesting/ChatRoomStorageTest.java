package ModulTesting;
import common.TextMessage;
import server.ChatRoomStorage;
import common.RegisteredUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import common.ChatRoom;
import common.Message;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ChatRoomStorageTest {

    private ChatRoomStorage chatRoomStorage;
    private RegisteredUser user1;
    private RegisteredUser user2;
    private RegisteredUser user3;

    @BeforeEach
    void setUp() {
        chatRoomStorage = new ChatRoomStorage();
        user1 = new RegisteredUser.UserBuilder().username("test1").password("test").build();
        user2 = new RegisteredUser.UserBuilder().username("test2").password("test2").build();
        user3 = new RegisteredUser.UserBuilder().username("test3").password("test3").build();
    }
    @Test
    void testAddChatRoomAndGetAllChatRooms() {
        // Create a chat room
        ChatRoom chatRoom = chatRoomStorage.addChatRoom("Test Room", Arrays.asList(user1, user2));

        assertNotNull(chatRoom);
        assertNotNull(chatRoom.getChatRoomID());
        // Retrieve chat rooms for user1 and user2
        List<ChatRoom> user1ChatRooms = chatRoomStorage.getAllChatRooms(user1);
        List<ChatRoom> user2ChatRooms = chatRoomStorage.getAllChatRooms(user2);

        assertEquals(1, user1ChatRooms.size());
        assertEquals(1, user2ChatRooms.size());
        assertTrue(user1ChatRooms.contains(chatRoom));
        assertTrue(user2ChatRooms.contains(chatRoom));

        // Retrieve chat rooms for user3 (not added to any chat room)
        List<ChatRoom> user3ChatRooms = chatRoomStorage.getAllChatRooms(user3);
        assertNull(user3ChatRooms);
    }
    @Test
    void testGetChatRoom() {
        // Create a chat room
        ChatRoom chatRoom = chatRoomStorage.addChatRoom("Test Room", Arrays.asList(user1, user2));

        assertNotNull(chatRoom);
        assertNotNull(chatRoom.getChatRoomID());

        // Retrieve the chat room by ID
        ChatRoom retrievedChatRoom = chatRoomStorage.getChatRoom(chatRoom.getChatRoomID());

        assertNotNull(retrievedChatRoom);
        assertEquals(chatRoom, retrievedChatRoom);
    }
    @Test
    void testAddMessageToChatRoomAndGetMessages() {
        // Create a chat room
        ChatRoom chatRoom = chatRoomStorage.addChatRoom("Test Room", Arrays.asList(user1, user2));

        assertNotNull(chatRoom);
        assertNotNull(chatRoom.getChatRoomID());

        // Add a message to the chat room
        Message message = new TextMessage.TextMessageBuilder().text("ODAI KAN Inte spela cs").sender(user1).build();
        chatRoomStorage.addMessageToChatRoom(chatRoom.getChatRoomID(), message);

        // Retrieve the messages from the chat room
        List<Message> messages = chatRoomStorage.getMessages(chatRoom.getChatRoomID());

        assertEquals(1, messages.size());
        assertEquals(message, messages.get(0));
    }
}
