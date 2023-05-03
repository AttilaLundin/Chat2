package application;

import java.util.List;
import java.util.UUID;


public class ChatRoom implements Cloneable{

//    private String chatRoomName;
    private UUID chatRoomID;
    private User user;
    private List<User> users;
    private List<Message> messages;

    public ChatRoom(List<User> users, List<Message> messages){
        //kolla std hashcodes
        chatRoomID = UUID.randomUUID();
        this.users = users;
        this.messages = messages;
//        this.chatRoomName = chatRoomName;
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
