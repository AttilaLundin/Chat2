package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {
    private static final int PORT = 1234;

    public static void main(String[] args){
        //lägg till läser in chathistory
        ChatHistory chatHistory = new ChatHistory();
        RegisteredUsers registeredUsers = new RegisteredUsers();
        try (ServerSocket serverSocket = new ServerSocket(PORT)){


            while(true){
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, chatHistory, registeredUsers);
                new Thread(clientHandler).start();
                System.out.println("New Thread started for socket: " + socket.getPort());
            }
        } catch (IOException e){
            System.err.println("Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}