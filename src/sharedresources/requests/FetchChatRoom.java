package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.ChatRoom;
import sharedresources.TextMessage;
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
            ChatRoom chatRoom = chatroomStorage.getChatRoom(chatRoomID);
            System.out.println(chatRoom.getMessages().size());
            List<Message> list = chatRoom.getMessages();
            for(Message m : list) System.out.println("in Fetch chatroom: " + ((TextMessage)m).getText());

            outputStream.writeObject(chatRoom);
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
