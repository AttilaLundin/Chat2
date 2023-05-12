package sharedresources.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.ChatRoom;
import sharedresources.interfaces.DataHandler;
import sharedresources.interfaces.Message;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

/**
 * This class implements the DataHandler and Serializable interfaces.
 * It represents a message sending request from a client, containing the message and the chat room ID.
 */
public class SendMessage implements DataHandler, Serializable {

    private UUID chatRoomID;
    private Message message;

    /**
     * Initializes a new SendMessage with the given chat room ID and message.
     *
     * @param chatRoomID The ID of the chat room where the message will be sent.
     * @param message The message to be sent.
     */
    public SendMessage(UUID chatRoomID, Message message){
        this.chatRoomID = chatRoomID;
        this.message = message;
    }

    /**
     * Handles the message sending request by adding the message to the chat room.
     *
     * @param userStorage The storage of users.
     * @param chatroomStorage The storage of chat rooms.
     * @param outputStream The output stream to the client.
     */
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream){
        chatroomStorage.addMessageToChatRoom(chatRoomID, message);
    }
}
