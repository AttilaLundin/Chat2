package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FetchAllUser implements DataHandler, Serializable {
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {

        try{
            outputStream.writeObject(userStorage.getAllUsers());
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
