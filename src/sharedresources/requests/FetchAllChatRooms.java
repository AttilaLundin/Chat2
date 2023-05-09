package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.User;
import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FetchAllChatRooms implements DataHandler, Serializable {

    private User user;
    public FetchAllChatRooms(User user){
        this.user = user;
    }
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {

        try{
            outputStream.writeObject(chatroomStorage.getAllChatRooms(user));
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
