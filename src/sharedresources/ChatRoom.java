package sharedresources;

import sharedresources.interfaces.Message;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class ChatRoom implements Serializable {

    private String chatRoomName;
    private final UUID chatRoomID;
    private List<User> users;
    private transient List<Message> messages;

    public ChatRoom(String chatRoomName, List<User> users, List<Message> messages){
        this.chatRoomName = Objects.requireNonNull(chatRoomName);
        this.chatRoomID = UUID.randomUUID();
        this.users = Objects.requireNonNull(users);
        this.messages = messages;
    }

    /*
    public ChatRoom(){

        this.chatRoomID = UUID.randomUUID();
        ArrayList<User> testUsers = new ArrayList<>();
        testUsers.add(new User.SessionUserBuilder().username("test").password("test").displayname("test").build());
        testUsers.add(new User.SessionUserBuilder().username("test1").password("test1").displayname("test1").build());
        this.users = testUsers;
        this.messages = new ArrayList<>();
    }
    */

    public List<User> getUsers(){
        return users;
    }
    public UUID getChatRoomID(){
        return chatRoomID;
    }
    public List<User> getUsersInChatRoom(){
        return users;
    }
    public List<Message> getMessages(){
        return messages;
    }
    public void addMessage(Message message){
        this.messages.add(message);
    }

}
