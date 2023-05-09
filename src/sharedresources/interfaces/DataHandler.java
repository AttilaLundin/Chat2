package sharedresources.interfaces;

import server.ChatRoomStorage;
import server.UserStorage;

import java.io.ObjectOutputStream;

public interface DataHandler {
    void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream);
}
