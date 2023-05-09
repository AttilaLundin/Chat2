package sharedresources.requests;

import server.ChatRoomStorage;
import sharedresources.ChatRoom;
import sharedresources.interfaces.DataHandler;
import sharedresources.interfaces.Message;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

public class AddMessageRequest implements DataHandler, Serializable {

    private UUID chatRoomID;
    private Message message;

    public void AddChatRoomRequest(ChatRoom chatRoom, Message message){
        this.chatRoomID = chatRoom.getChatRoomID();
        this.message = message;
    }

    @Override
    public void dataHandler(Object userStorage, Object chatroomStorage, ObjectOutputStream outputStream){
        if(chatroomStorage instanceof ChatRoomStorage crs) crs.addMessageToChatRoom(chatRoomID, message);
    }
}
