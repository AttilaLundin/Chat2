package sharedresources.interfaces;

import server.ChatRoomStorage;
import server.UserStorage;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface DataHandler{
    void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream);
}
