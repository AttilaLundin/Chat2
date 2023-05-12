package common;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class ChatRoom implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String chatRoomName;
    private final UUID chatRoomID;
    private final List<RegisteredUser> users;
    private List<Message> messages;

    public ChatRoom(String chatRoomName, List<RegisteredUser> users, List<Message> messages){
        this.chatRoomName = Objects.requireNonNull(chatRoomName);
        this.chatRoomID = UUID.randomUUID();
        this.users = Objects.requireNonNull(users);
        this.messages = messages;
    }

    public List<RegisteredUser> getUsers(){
        return users;
    }
    public UUID getChatRoomID(){
        return chatRoomID;
    }
    public List<RegisteredUser> getUsersInChatRoom(){
        return users;
    }
    public List<Message> getMessages(){
        return messages;
    }
    public void addMessage(Message message){
        this.messages.add(message);
    }
    public void addMessagList(List<Message> messages){
        this.messages = messages;
    }

    public String getChatRoomName(){
        return chatRoomName;
    }

}
