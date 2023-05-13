package common;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * This class represents a chat room in a chat application. It contains the list of registered users
 * and messages. Each chat room has a unique ID and a name.
 */
public class ChatRoom implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String chatRoomName;
    private final UUID chatRoomID;
    private final List<RegisteredUser> users;
    private List<Message> messages;

    /**
     * Constructs a ChatRoom with the specified name, users, and messages.
     *
     * @param chatRoomName the name of the chat room
     * @param users        the list of registered users
     * @param messages     the list of messages
     */
    public ChatRoom(String chatRoomName, List<RegisteredUser> users, List<Message> messages){
        this.chatRoomName = Objects.requireNonNull(chatRoomName);
        this.chatRoomID = UUID.randomUUID();
        this.users = Objects.requireNonNull(users);
        this.messages = messages;
    }

    /**
     * Returns the list of registered users in the chat room.
     *
     * @return the list of users
     */
    public List<RegisteredUser> getUsers(){
        return users;
    }

    /**
     * Returns the unique identifier of the chat room.
     *
     * @return the UUID of the chat room
     */
    public UUID getChatRoomID(){
        return chatRoomID;
    }

    /**
     * Returns the list of registered users in the chat room.
     *
     * @return the list of users
     */
    public List<RegisteredUser> getUsersInChatRoom(){
        return users;
    }

    /**
     * Returns the list of messages in the chat room.
     *
     * @return the list of messages
     */
    public List<Message> getMessages(){
        return messages;
    }

    /**
     * Adds a new message to the chat room.
     *
     * @param message the message to be added
     */
    public void addMessage(Message message){
        this.messages.add(message);
    }

    /**
     * Replaces (updates) the current list of messages with the provided list of messages.
     *
     * @param messages the new list of messages
     */
    public void addMessageList(List<Message> messages){
        this.messages = messages;
    }

    /**
     * Returns the name of the chat room.
     *
     * @return the name of the chat room
     */
    public String getChatRoomName(){
        return chatRoomName;
    }
}
