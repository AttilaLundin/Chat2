package common.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import common.ChatRoom;
import common.RegisteredUser;
import common.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * This class implements the DataHandler interface and Serializable interface.
 * It is responsible for creating a new chat room and handling related data.
 */
public class CreateNewChatRoom implements DataHandler, Serializable {

    private List<RegisteredUser> usersInChatRoom;
    private String chatRoomName;

    /**
     * This constructor initializes the CreateNewChatRoom object.
     *
     * @param chatRoomName The name of the chat room to be created.
     * @param usersInChatRoom The list of users in the chat room.
     */
    public CreateNewChatRoom(String chatRoomName, List<RegisteredUser> usersInChatRoom){
        this.usersInChatRoom = usersInChatRoom;
        this.chatRoomName = chatRoomName;

    }

    /**
     * Handles the creation of a new chat room by adding it to the chat room storage and sends the new chat room
     * to the client through the output stream.
     *
     * @param userStorage The storage of users.
     * @param chatroomStorage The storage of chat rooms.
     * @param outputStream The output stream to the client.
     */
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {
       try{
           ChatRoom chatRoom = chatroomStorage.addChatRoom(chatRoomName, usersInChatRoom);
           outputStream.writeObject(chatRoom);
           outputStream.flush();
       }catch (IOException e){
           e.printStackTrace();
       }

    }
}
