package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.ChatRoom;
import sharedresources.User;
import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class CreateNewChatRoom implements DataHandler, Serializable {

    private List<User> usersInChatRoom;
    private String chatRoomName = "capy chat";

    public CreateNewChatRoom(List<User> usersInChatRoom){
        this.usersInChatRoom = usersInChatRoom;//ska det vara add users in chatroom ? om jag fattat rätt

    }


    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {
       try{
           ChatRoom chatRoom = chatroomStorage.addChatRoom(chatRoomName, usersInChatRoom);
           outputStream.writeObject(chatRoom);
           outputStream.flush();
       }catch (IOException e){
           e.printStackTrace();
       }

    }
}