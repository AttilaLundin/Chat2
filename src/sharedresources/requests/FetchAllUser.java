package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.User;
import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class FetchAllUser implements DataHandler, Serializable {
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {

        try{
            Map<String, User> userMap = userStorage.getAllUsers();
            if(userMap == null){
                outputStream.writeObject(true);
                outputStream.flush();
                return;
            }
            Set<String> keySetSet = userMap.keySet();
            for (String key : keySetSet){
                outputStream.writeObject(userMap.get(key));
                outputStream.flush();
            }
            outputStream.writeObject(true);
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
