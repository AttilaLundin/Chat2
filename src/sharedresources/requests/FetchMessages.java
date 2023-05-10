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

public class FetchMessages implements DataHandler, Serializable {
    private UUID chatRoomID;

    public FetchMessages(UUID chatRoomID){
        this.chatRoomID = chatRoomID;
    }
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {
        try {
            List<Message> messagesList = chatroomStorage.getMessages(chatRoomID);
            if(messagesList == null){
                outputStream.writeObject(true);
                outputStream.flush();
                return;
            }

            for(Message m : messagesList){
                outputStream.writeObject(m);
                outputStream.flush();
            }
            outputStream.writeObject(true);
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
