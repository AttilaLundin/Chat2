package sharedresources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChatRoom implements Cloneable, Serializable {

    private String chatRoomName;
    private final UUID chatRoomID;
    private List<SessionUser> users;
    private final List<Message> messages;

    public ChatRoom(List<SessionUser> users, List<Message> messages){
        chatRoomID = UUID.randomUUID();
        this.users = users;
        this.messages = messages;
    }

    public ChatRoom(){


        this.chatRoomID = UUID.randomUUID();
        ArrayList<SessionUser> testUsers = new ArrayList<>();
        testUsers.add(new SessionUser.SessionUserBuilder().username("test").password("test").displayname("test").build());
        testUsers.add(new SessionUser.SessionUserBuilder().username("test1").password("test1").displayname("test1").build());
        this.users = testUsers;



        this.messages = new ArrayList<>();
    }

    public List<SessionUser> getUsers(){
        return users;
    }

    public UUID getChatRoomID(){
        return chatRoomID;
    }

    public List<SessionUser> getUsersInChatRoom(){
        return users;
    }
    public List<Message> getMessages(){
        return messages;
    }
    public void addMessage(Message message){
        this.messages.add(message);
    }

    /* Möjligtvis ofullständig kloning, om privata variabler i chatroom inte är primitiva/immutable så
        måste de också explicit kopieras i clone metoden, kanske t.ex. lista av users?
     */
    @Override
    protected ChatRoom clone() {
        try{
            return (ChatRoom) super.clone();
        }
        catch(CloneNotSupportedException e){
            throw new AssertionError("Cloning for this object is not supported", e);
        }
    }
}
