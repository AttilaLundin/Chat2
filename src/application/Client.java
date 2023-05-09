package application;

import sharedresources.User;
import sharedresources.ChatRoom;
import sharedresources.requests.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;


public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;
    private Socket socket;
    private User user;
    private ObjectOutputStream output;
    private ObjectInputStream input;


    public void connectToServer(){
        boolean connected = false;

        while(!connected){
            try {
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                socket.setKeepAlive(true);
                connected = socket.isConnected();
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                System.out.println("Connected to server");
            } catch (IOException e){
                System.out.println("waiting");
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException ie){
                    ie.getStackTrace();
                }
            }
        }
    }

    public void sendMessage(SendMessage sendMessage){
        try{
            System.out.println("msg sent");
            output.writeObject(sendMessage);
            output.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean sendLoginRequest(LoginRequest loginRequest){
        try{

            output.writeObject(loginRequest);
            output.flush();

            User user = (User) input.readObject();
            if(user != null) {
                this.user = user;

                return true;
            }
            return false;

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean sendRegistrationRequest(RegisterRequest registerRequest){
        try{

            output.writeObject(registerRequest);
            output.flush();

            User user = (User) input.readObject();
            if(user != null) {
                this.user = user;
                return true;
            }
            return false;

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return false;
    }

    public Map<String, User> getUsersList(FetchAllUser fetchAllUser){
        try {

            output.writeObject(fetchAllUser);
            output.flush();

            Object object = input.readObject();
            if(object instanceof Map<?,?> map){
                @SuppressWarnings ("unchecked")
                Map<String, User> userMap = (Map<String, User>) map;
                return userMap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public List<ChatRoom> getAllChatRoom(){

        try {
            output.writeObject(new FetchAllChatRooms(user));
            output.flush();

            Object object = input.readObject();
            List <ChatRoom> chatRooms = new ArrayList<>();
            if(object instanceof List<?> list){
                for(Object o : list) if(o instanceof ChatRoom chatRoom) chatRooms.add(chatRoom);
            }

            return chatRooms;

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return null;

    }

    public ChatRoom getChatRoom(FetchChatRoom fetchChatRoom){
        try{
            output.writeObject(fetchChatRoom);
            output.flush();
            Object object = input.readObject();
            if(object instanceof ChatRoom chatRoom) return chatRoom;
        }catch (IOException | ClassNotFoundException e){
             e.printStackTrace();
        }
        return null;
    }

    public ChatRoom addChatRoom(CreateNewChatRoom createNewChatRoom){

        try{
            output.writeObject(createNewChatRoom);
            output.flush();

            Object object = input.readObject();

            if(object instanceof ChatRoom chatRoom){
                return chatRoom;
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }




    public User getSessionUser(){
        return user;
    }


}
