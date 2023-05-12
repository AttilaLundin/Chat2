package common.requests;

import server.ChatRoomStorage;
import server.UserStorage;
import common.DataHandler;
import common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * This class implements the DataHandler and Serializable interfaces.
 * It is responsible for fetching all messages from a specific chat room identified by its UUID.
 */
public class FetchMessages implements DataHandler, Serializable {
    private UUID chatRoomID;
    /**
     * Initializes a new FetchMessages request with the given UUID of the chat room.
     *
     * @param chatRoomID The UUID of the chat room.
     */
    public FetchMessages(UUID chatRoomID){
        this.chatRoomID = chatRoomID;
    }

    /**
     * Handles fetching all messages from the specified chat room in the chat room storage
     * and sends them to the client through the output stream.
     *
     * @param userStorage The storage of users.
     * @param chatroomStorage The storage of chat rooms.
     * @param outputStream The output stream to the client.
     */
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
