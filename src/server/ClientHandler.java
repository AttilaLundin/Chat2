package server;

import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

/**
 * A class that handles client connections for the chat server. This class is responsible for receiving
 * requests from clients, processing them, and sending responses back to the clients.
 */
public class ClientHandler implements Runnable{
    private final Socket socket;
    private ChatRoomStorage chatRoomStorage;
    private final UserStorage userStorage;

    /**
     * Constructor that initializes the client handler with the provided socket, chat room storage, and user storage.
     *
     * @param socket the socket to use for communication
     * @param chatroomStorage the storage for chat rooms
     * @param userStorage the storage for users
     */
    public ClientHandler(Socket socket, ChatRoomStorage chatroomStorage, UserStorage userStorage){
        this.socket = Objects.requireNonNull(socket);
        this.chatRoomStorage = Objects.requireNonNull(chatroomStorage);
        this.userStorage = Objects.requireNonNull(userStorage);
    }

    /**
     * The main execution method for the ClientHandler. It handles incoming requests and sends responses.
     */
    public void run(){
        try(ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream())){
            socket.setKeepAlive(true);

            while (true){
                Object object = input.readObject();
                if(object instanceof DataHandler data){
                    data.dataHandler(userStorage, chatRoomStorage, output);
                }
            }
        }catch (SocketException se) {
            System.out.println("Socket " + socket.getPort() + " disconnected");
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            Thread.currentThread().interrupt();
        }
    }
}