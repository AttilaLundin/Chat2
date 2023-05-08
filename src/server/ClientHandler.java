package server;

import sharedresources.interfaces.DataHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;


public class ClientHandler implements Runnable{
    private final Socket socket;
    private ChatRoomStorage chatRoomStorage;
    private final UserStorage userStorage;

    public ClientHandler(Socket socket, ChatRoomStorage chatroomStorage, UserStorage userStorage){
        this.socket = Objects.requireNonNull(socket);
        this.chatRoomStorage = Objects.requireNonNull(chatroomStorage);
        this.userStorage = Objects.requireNonNull(userStorage);
    }

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
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}