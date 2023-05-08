package server;

import sharedresources.Message;
import sharedresources.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;


public class ClientHandler implements Runnable{
    private final Socket socket;
    private ChatHistory chatHistory;
    private final RegisteredUsers registeredUsers;

    public ClientHandler(Socket socket, ChatHistory chatHistory, RegisteredUsers registeredUsers){
        this.socket = Objects.requireNonNull(socket);
        this.chatHistory = Objects.requireNonNull(chatHistory);
        this.registeredUsers = Objects.requireNonNull(registeredUsers);
    }

    public void run(){
        try(ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream())){
            socket.setKeepAlive(true);


            while (true){
                Object object = input.readObject();
                if(object instanceof Message message){
                    message.messageHandler(chatHistory);
                }
                else if(object instanceof User user){
                    user.userHandler(registeredUsers, chatHistory, output);
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}