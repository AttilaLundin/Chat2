package controller;

import interfaces.Message;
import interfaces.User;
import model.*;
import model.RegisteredUsers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class ClientHandler implements Runnable{
    private final Socket socket;
    private ChatHistory chatHistory;
    private final RegisteredUsers registeredUsers;

    public ClientHandler(Socket socket, ChatHistory chatHistory, RegisteredUsers registeredUsers){
        this.socket = socket;
        this.chatHistory = chatHistory;
        this.registeredUsers = registeredUsers;
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
                    user.userHandler(registeredUsers, output);
                    System.out.println("response from server sent ");
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}