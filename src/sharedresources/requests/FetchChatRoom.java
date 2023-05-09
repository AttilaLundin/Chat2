package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

public class FetchChatRoom implements DataHandler, Serializable {

    UUID chatRoomID;

    public FetchChatRoom(UUID chatRoomID){
        this.chatRoomID = chatRoomID;
    }

    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(chatroomStorage.getChatRoom(chatRoomID));
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
