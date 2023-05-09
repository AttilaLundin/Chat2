package sharedresources.requests;

import sharedresources.ChatRoom;
import sharedresources.interfaces.DataHandler;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AddChatRoomRequest implements DataHandler, Serializable {

    private ChatRoom chatRoom;

    public AddChatRoomRequest(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
    }


    @Override
    public void dataHandler(Object userStorage, Object chatroomStorage, ObjectOutputStream outputStream) {

    }
}
