package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChatRoom implements Cloneable{

    private UUID chatRoomID;
    private List<User> users;
    private List<Message> messages;

    public ChatRoom(List<User> users, List<Message> messages){

        chatRoomID = UUID.randomUUID();
        this.users = users;
        this.messages = messages;

    }

    public ChatRoom(){
        chatRoomID = UUID.randomUUID();
        ArrayList<User> testUsers = new ArrayList<>();
        testUsers.add(new User.Builder("test", "testp").build());
        testUsers.add(new User.Builder("test", "testp").build());
        this.messages = new ArrayList<>();
    }

    public List<User> getUsers(){
        return users;
    }

    public UUID getChatRoomID(){
        return chatRoomID;
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
