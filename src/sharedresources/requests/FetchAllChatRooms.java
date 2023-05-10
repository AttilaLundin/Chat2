package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.ChatRoom;
import sharedresources.User;
import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class FetchAllChatRooms implements DataHandler, Serializable {

    private User user;
    public FetchAllChatRooms(User user){
        this.user = user;
    }
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {

        try{
            List<ChatRoom> chatRoomList = chatroomStorage.getAllChatRooms(user);
            if(chatRoomList == null){
                outputStream.writeObject(true);
                outputStream.flush();
                return;
            }
            for(ChatRoom c : chatRoomList){
                System.out.println(c.getChatRoomName());
                outputStream.writeObject(c);
                outputStream.flush();
            }
            outputStream.writeObject(true);
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
