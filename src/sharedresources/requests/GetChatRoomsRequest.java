package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.ChatRoom;
import sharedresources.interfaces.DataHandler;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GetChatRoomsRequest implements DataHandler, Serializable {

    private ChatRoom chatRoom;
    public void GetChatRoomRequest(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
    }
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {

    }
}
