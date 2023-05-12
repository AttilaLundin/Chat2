package ModulTesting;

import common.ChatRoom;
import common.Message;
import common.RegisteredUser;
import common.TextMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;


public class ChatRoomTest {
    private ChatRoom chatRoom;
    private List<RegisteredUser> users;
    private List<Message> messages;

    @Before
    public void setUp() {
        users = new ArrayList<>();
        RegisteredUser User1 = new RegisteredUser.UserBuilder().username("User1").password("user1").build();
        RegisteredUser User2 = new RegisteredUser.UserBuilder().username("User1").password("user1").build();
        users.add(User1);
        users.add(User2);

        messages = new ArrayList<>();
        messages.add(new TextMessage.TextMessageBuilder().text("hello User2").sender(User1).build());
        messages.add(new TextMessage.TextMessageBuilder().text("hello user1").sender(User2).build());

        chatRoom = new ChatRoom("Test Chat Room", users, messages);
    }

    @Test
    public void testGetUsers() {
        List<RegisteredUser> retrievedUsers = chatRoom.getUsers();
        assertEquals(users, retrievedUsers);
    }

    @Test
    public void testGetChatRoomID() {
        UUID chatRoomID = chatRoom.getChatRoomID();
        assertNotNull(chatRoomID);
    }

    @Test
    public void testGetUsersInChatRoom() {
        List<RegisteredUser> retrievedUsers = chatRoom.getUsersInChatRoom();
        assertEquals(users, retrievedUsers);
    }

    @Test
    public void testGetMessages() {
        List<Message> retrievedMessages = chatRoom.getMessages();
        assertEquals(messages, retrievedMessages);
    }

    @Test
    public void testAddMessage() {
        RegisteredUser User1 = new RegisteredUser.UserBuilder().username("User1").password("user1").build();
        Message newMessage = new TextMessage.TextMessageBuilder().text("hello").sender(User1).build();
        chatRoom.addMessage(newMessage);

        List<Message> retrievedMessages = chatRoom.getMessages();

        assertTrue(retrievedMessages.contains(newMessage));

    }

    @Test
    public void testAddMessageList() {
        RegisteredUser User1 = new RegisteredUser.UserBuilder().username("User1").password("user1").build();
        List<Message> newMessages = new ArrayList<>();
        Message msg = new TextMessage.TextMessageBuilder().text("this is a test for addMessagelist").sender(User1).build();

        newMessages.add(msg);

        chatRoom.addMessagList(newMessages);

        List<Message> retrievedMessages = chatRoom.getMessages();
        assertEquals(newMessages, retrievedMessages);
    }

    @Test
    public void testGetChatRoomName() {
        String chatRoomName = chatRoom.getChatRoomName();
        assertEquals("Test Chat Room", chatRoomName);
    }


    public static void main(String [] args){
    }
}