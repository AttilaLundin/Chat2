package common;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * This class represents a ChatRoom with a unique UUID identifier.
 * It holds data about users in the room, messages exchanged in the room, and provides methods to manage these data.
 *
 * This class implements the Serializable interface to allow its instances to be saved and restored.
 *
 * @author Shazhaib Kazmi
 */
public class ChatRoom implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String chatRoomName;
    private final UUID chatRoomID;
    private final List<RegisteredUser> users;
    private List<Message> messages;

    /**
     * Constructs a new ChatRoom with the provided name, list of users, and list of messages.
     * The ChatRoomID is automatically generated.
     * @param chatRoomName the name of the chat room
     * @param users the list of users in the chat room
     * @param messages the list of messages in the chat room
     */
    public ChatRoom(String chatRoomName, List<RegisteredUser> users, List<Message> messages){
        this.chatRoomName = Objects.requireNonNull(chatRoomName);
        this.chatRoomID = UUID.randomUUID();
        this.users = Objects.requireNonNull(users);
        this.messages = messages;
    }

    /**
     * Returns the list of users in the chat room.
     * @return list of users in the chat room
     */
    public List<RegisteredUser> getUsers(){
        return users;
    }

    /**
     * Returns the UUID of the chat room.
     * @return UUID of the chat room
     */
    public UUID getChatRoomID(){
        return chatRoomID;
    }

    /**
     * Returns the list of users in the chat room.
     * @return list of users in the chat room
     */
    public List<RegisteredUser> getUsersInChatRoom(){
        return users;
    }

    /**
     * Returns the list of messages in the chat room.
     * @return list of messages in the chat room
     */
    public List<Message> getMessages(){
        return messages;
    }

    /**
     * Adds a new message to the chat room.
     * @param message the message to be added
     */
    public void addMessage(Message message){
        this.messages.add(message);
    }

    /**
     * Replaces the list of messages in the chat room with the provided list.
     * @param messages the new list of messages
     */
    public void addMessageList(List<Message> messages){
        this.messages = messages;
    }

    /**
     * Returns the name of the chat room.
     * @return name of the chat room
     */
    public String getChatRoomName(){
        return chatRoomName;
    }

}
