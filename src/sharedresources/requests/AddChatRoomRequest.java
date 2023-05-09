package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.User;
import sharedresources.interfaces.DataHandler;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class AddChatRoomRequest implements DataHandler, Serializable {

    private List<User> usersInChatRoom;

    public AddChatRoomRequest(List<User> usersInChatRoom){
        this.usersInChatRoom = usersInChatRoom; //ska det vara add users in chatroom ? om jag fattat rätt
    }


    @Override
    public void dataHandler(Object userStorage, Object chatroomStorage, ObjectOutputStream outputStream) {

    }
}
