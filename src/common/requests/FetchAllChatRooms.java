package common.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import common.ChatRoom;
import common.RegisteredUser;
import common.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This class implements the DataHandler interface and Serializable interface.
 * It is responsible for fetching all chat rooms that a specific user is part of.
 */
public class FetchAllChatRooms implements DataHandler, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final RegisteredUser user;

    /**
     * This constructor initializes the FetchAllChatRooms object.
     *
     * @param user The user whose chat rooms will be fetched.
     */
    public FetchAllChatRooms(RegisteredUser user){
        this.user = user;
    }

    /**
     * Handles fetching all chat rooms that a specific user is part of by pulling them from the chat room storage
     * and sending them to the client through the output stream.
     *
     * @param userStorage The storage of users.
     * @param chatroomStorage The storage of chat rooms.
     * @param outputStream The output stream to the client.
     */
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
