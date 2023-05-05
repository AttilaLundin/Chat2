package controller;

import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class ClientHandler implements Runnable{
    private final Socket socket;
    private ChatHistory chatHistory;
    private RegisteredUsers registeredUsers;

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
                    chatHistory.addMessage(message.getChatRoomID(), message);
                }
                else if(object instanceof User user){
                    output.writeObject(registeredUsers.validateUser(user));
                    output.flush();
                    System.out.println("response from server sent ");
                }
                else if(object instanceof Register register){
                    output.flush();
                    output.writeObject(registeredUsers.createUser(register));
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}