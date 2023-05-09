package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.interfaces.DataHandler;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GetUsersRequest implements DataHandler, Serializable {
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {

    }
}
