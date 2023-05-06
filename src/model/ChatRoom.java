package model;

import model.messages.Message;
import model.messages.TextMessage;
import model.user.SessionUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChatRoom implements Cloneable, Serializable {

    private final UUID chatRoomID;
    private List<SessionUser> users;
    private final List<Message> textMessages;

    public ChatRoom(List<SessionUser> users, List<Message> textMessages){

        chatRoomID = UUID.randomUUID();
        this.users = users;
        this.textMessages = textMessages;

    }

    public ChatRoom(){
        chatRoomID = UUID.randomUUID();
        ArrayList<SessionUser> testUsers = new ArrayList<>();
        testUsers.add(new SessionUser.Builder("test", "testp").build());
        testUsers.add(new SessionUser.Builder("test", "testp").build());
        this.textMessages = new ArrayList<>();
    }

    public List<SessionUser> getUsers(){
        return users;
    }

    public UUID getChatRoomID(){
        return chatRoomID;
    }
    public List<Message> getMessages(){
        return textMessages;
    }
    public void addMessage(TextMessage textMessage){
        this.textMessages.add(textMessage);
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
