import common.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import common.Message;
import common.ChatRoom;
import common.RegisteredUser;
import server.ChatRoomStorage;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is responsible for testing the functionality of the ChatRoomStorage class.
 */
public class ChatRoomStorageTest {

    private ChatRoomStorage storage;
    private RegisteredUser user1;
    private RegisteredUser user2;
    private RegisteredUser user3;
    private ChatRoom chatRoom1;
    private ChatRoom chatRoom2;
    private ChatRoom chatRoom3;
    private Message message1;
    private Message message2;
    private Message message3;

    /**
     * This method sets up the testing environment before each test is run.
     */
    @BeforeEach
    public void setup() {
        storage = new ChatRoomStorage();
        user1 = new RegisteredUser.RegisteredUserBuilder().username("testUser1").password("testPassword1").build();
        user2 = new RegisteredUser.RegisteredUserBuilder().username("testUser2").password("testPassword2").build();
        user3 = new RegisteredUser.RegisteredUserBuilder().username("testUser3").password("testPassword3").build();
        List<RegisteredUser> members = new ArrayList<>();
        members.add(user1);
        members.add(user2);
        members.add(user3);

        chatRoom1 = storage.addChatRoom("testChatRoom1", members);
        chatRoom2 = storage.addChatRoom("testChatRoom2", members);
        chatRoom3 = storage.addChatRoom("testChatRoom3", members);

        message1 = new TextMessage.TextMessageBuilder().text("testMessage1").sender(user1).build();
        message2 = new TextMessage.TextMessageBuilder().text("testMessage2").sender(user2).build();
        message3 = new TextMessage.TextMessageBuilder().text("testMessage3").sender(user3).build();
    }

    /**
     * This test ensures that the correct chat rooms for a user are returned.
     */
    @Test
    public void testGetChatRoomsUserIsPartOf() {
        List<ChatRoom> chatRoomsForUser = storage.getAllChatRooms(user1);

        assertEquals(3, chatRoomsForUser.size());
        assertEquals(chatRoom1, chatRoomsForUser.get(0));
        assertEquals(chatRoom2, chatRoomsForUser.get(1));
        assertEquals(chatRoom3, chatRoomsForUser.get(2));
    }

    /**
     * This test verifies that the correct chat rooms are returned based on their IDs.
     */
    @Test
    public void testGetChatRooms() {

        ChatRoom retrievedChatRoom1 = storage.getChatRoom(chatRoom1.getChatRoomID());
        assertEquals(chatRoom1, retrievedChatRoom1);

        ChatRoom retrievedChatRoom2 = storage.getChatRoom(chatRoom2.getChatRoomID());
        assertEquals(chatRoom2, retrievedChatRoom2);

        ChatRoom retrievedChatRoom3 = storage.getChatRoom(chatRoom3.getChatRoomID());
        assertEquals(chatRoom3, retrievedChatRoom3);

    }

    /**
     * This test verifies that the correct messages are returned for a given chat room ID.
     */
    @Test
    public void testGetMessages() {
        storage.addMessageToChatRoom(chatRoom1.getChatRoomID(), message1);
        storage.addMessageToChatRoom(chatRoom1.getChatRoomID(), message2);
        storage.addMessageToChatRoom(chatRoom1.getChatRoomID(), message3);

        List<Message> messages = storage.getMessages(chatRoom1.getChatRoomID());

        assertEquals(3, messages.size());
        assertEquals(message1, messages.get(0));
        assertEquals(message2, messages.get(1));
        assertEquals(message3, messages.get(2));


    }

    /**
     * This test checks if adding a chat room works correctly.
     */
    @Test
    public void testAddChatRoom() {
        int initialSize = storage.getAllChatRooms(user1).size();
        ChatRoom newChatRoom = storage.addChatRoom("testChatRoom4", List.of(user1));

        assertEquals(initialSize + 1, storage.getAllChatRooms(user1).size());
        assertEquals(newChatRoom, storage.getChatRoom(newChatRoom.getChatRoomID()));
    }

    /**
     * This test checks if adding a text message to a chat room works correctly.
     */
    @Test
    public void testAddTextMessageToChatRoom() {
        int initialSize = storage.getMessages(chatRoom1.getChatRoomID()).size();
        Message newMessage = new TextMessage.TextMessageBuilder().text("testMessage").sender(user1).build();
        storage.addMessageToChatRoom(chatRoom1.getChatRoomID(), newMessage);
        assertEquals(initialSize + 1, storage.getMessages(chatRoom1.getChatRoomID()).size());
        assertEquals(newMessage, storage.getMessages(chatRoom1.getChatRoomID()).get(initialSize));
    }

    /**
     * This test checks if adding an image message to a chat room works correctly.
     */
    @Test
    public void testAddImageMessageToChatRoom() {
        int initialSize = storage.getMessages(chatRoom1.getChatRoomID()).size();
        Message newMessage = new TextMessage.TextMessageBuilder().text("testMessage").sender(user1).build();
        storage.addMessageToChatRoom(chatRoom1.getChatRoomID(), newMessage);
        assertEquals(initialSize + 1, storage.getMessages(chatRoom1.getChatRoomID()).size());
        assertEquals(newMessage, storage.getMessages(chatRoom1.getChatRoomID()).get(initialSize));
    }

    /**
     * This test checks the behavior when trying to get chat rooms for a non-existent user.
     */
    @Test
    public void testGetChatRoomsForNonExistentUser() {
        assertThrowsExactly(NullPointerException.class, () -> storage.getAllChatRooms(null));
    }

    /**
     * This test checks the behavior when trying to get a chat room with a non-existent ID.
     */
    @Test
    public void testGetChatRoomForNonExistentId() {
        UUID randomUUID = UUID.randomUUID();
        assertThrowsExactly(NullPointerException.class, () -> storage.getMessages(randomUUID));
    }

    /**
     * This test checks the behavior when trying to get messages for a non-existent chat room.
     */
    @Test
    public void testGetMessagesForNonExistentChatRoom() {
        UUID randomUUID = UUID.randomUUID();
        assertThrowsExactly(NullPointerException.class, () -> storage.getMessages(randomUUID));

        List<ChatRoom> chatRoomsForOther = storage.getAllChatRooms(new RegisteredUser.RegisteredUserBuilder().username("otherUsername").password("otherPassword").build());
        assertNull(chatRoomsForOther);
    }
}