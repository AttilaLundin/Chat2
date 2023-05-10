package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.ChatRoom;
import sharedresources.User;
import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GetChatRoomsRequest implements DataHandler, Serializable {

    private User user;
    public GetChatRoomsRequest(User user){
        this.user = user;
    }
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {

        try{

            outputStream.writeObject(chatroomStorage.getChatRooms(user));
            outputStream.flush();

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
