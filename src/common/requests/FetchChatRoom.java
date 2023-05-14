package common.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import common.ChatRoom;
import common.DataHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * This class implements the DataHandler interface and Serializable interface.
 * It is responsible for fetching a specific chat room using its UUID.
 *
 * @author Odai Alrahem
 */
public class FetchChatRoom implements DataHandler, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    UUID chatRoomID;

    /**
     * Initializes a new FetchChatRoom request with the given UUID of the chat room.
     *
     * @param chatRoomID The UUID of the chat room.
     */
    public FetchChatRoom(UUID chatRoomID){
        this.chatRoomID = chatRoomID;
    }

    /**
     * Handles fetching the specified chat room from the chat room storage and sending it to the client through the output stream.
     *
     * @param userStorage The storage of users.
     * @param chatroomStorage The storage of chat rooms.
     * @param outputStream The output stream to the client.
     */
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream) {
        try {
            ChatRoom chatRoom = chatroomStorage.getChatRoom(chatRoomID);
            outputStream.writeObject(chatRoom);
            outputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
