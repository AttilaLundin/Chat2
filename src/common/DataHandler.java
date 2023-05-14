package common;

import server.ChatRoomStorage;
import server.UserStorage;

import java.io.ObjectOutputStream;

/**
 * An interface that represents a data handler in the chat application.
 * This interface has a method with parameters meant for handling data from user storage and chat room storage but also
 * handles requests and users.
 *
 * @author Odai Alrahem
 */
public interface DataHandler{

    /**
     * Handles data from user storage and chat room storage. The method definition is left to the
     * implementing class.
     *
     * @param userStorage the user storage from the server
     * @param chatroomStorage the chat room storage from the server
     * @param outputStream the output stream to send data to the client
     */
    void dataHandler(UserStorage userStorage, ChatRoomStorage chatroomStorage, ObjectOutputStream outputStream);
}
