package common.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import common.RegisteredUser;
import common.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the DataHandler interface and Serializable interface.
 * It is responsible for fetching all users from the user storage.
 */
public class FetchAllUser implements DataHandler, Serializable {

    /**
     * Handles fetching all users from the user storage and sending them to the client through the output stream.
     *
     * @param userStorage The storage of users.
     * @param chatroomStorage The storage of chat rooms.
     * @param outputStream The output stream to the client.
     */
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {

        try{
            Map<String, RegisteredUser> userMap = userStorage.getAllUsers();
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
