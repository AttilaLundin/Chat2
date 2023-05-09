package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.ChatRoom;
import sharedresources.interfaces.DataHandler;
import sharedresources.interfaces.Message;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

public class SendMessageRequest implements DataHandler, Serializable {

    private UUID chatRoomID;
    private Message message;

    public SendMessageRequest(ChatRoom chatRoom, Message message){
        this.chatRoomID = chatRoom.getChatRoomID();
        this.message = message;
    }

    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream){
        chatroomStorage.addMessageToChatRoom(chatRoomID, message);
    }
}
