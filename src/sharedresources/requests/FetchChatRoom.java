package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.ChatRoom;
import sharedresources.interfaces.DataHandler;
import sharedresources.interfaces.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class FetchChatRoom implements DataHandler, Serializable {

    UUID chatRoomID;

    public FetchChatRoom(UUID chatRoomID){
        this.chatRoomID = chatRoomID;
    }

    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {
        try {
            List<Message> chatRoom = chatroomStorage.getChatRoom(chatRoomID);
            System.out.println(chatRoom);
            outputStream.writeObject(chatRoom);
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
