import java.util.List;


public class ChatRoom {

    private String chatRoomName;
    private int chatRoomID;
    private List<User> users;
    private List<Message> messages;

    public ChatRoom(List<User> users, List<Message> messages, String chatRoomName){
        //kolla std hashcodes
        chatRoomID = users.hashCode() * chatRoomName.hashCode() * 31;
        this.users = users;
        this.messages = messages;
        this.chatRoomName = chatRoomName;
    }

    public List<User> getUsers(){
        return users;
    }

    public int getChatRoomID(){
        return chatRoomID;
    }
    public List<Message> getMessages(){
        return messages;
    }
    public void addMessage(Message message){
        this.messages.add(message);
    }


}
