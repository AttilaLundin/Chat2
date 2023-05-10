package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.interfaces.DataHandler;
import sharedresources.interfaces.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class FetchMessagesInChatroom implements DataHandler, Serializable {
    private UUID chatRoomID;

    public FetchMessagesInChatroom(UUID chatRoomID){
        this.chatRoomID = chatRoomID;
    }
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {
        try {
            List<Message> list = chatroomStorage.deBugger(chatRoomID);
            outputStream.writeObject(list);
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
