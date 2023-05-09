package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.User;
import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class AddChatRoomRequest implements DataHandler, Serializable {

    private List<User> usersInChatRoom;
    private String chatRoomName = "capy chat";

    public AddChatRoomRequest(List<User> usersInChatRoom){
        this.usersInChatRoom = usersInChatRoom;//ska det vara add users in chatroom ? om jag fattat rätt

    }


    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {
       try{
           outputStream.writeObject(chatroomStorage.addChatRoom(chatRoomName, usersInChatRoom));
           outputStream.flush();
       }catch (IOException e){
           e.printStackTrace();
       }

    }
}
