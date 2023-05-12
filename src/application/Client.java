package application;

import sharedresources.User;
import sharedresources.ChatRoom;
import sharedresources.interfaces.Message;
import sharedresources.requests.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

/**
 * A class representing a client in the chat application. This class is responsible for creating connections
 * to the server and handling data transfer between the client and server. It manages user actions such as
 * login, registration, sending messages, fetching users, chat rooms and messages.
 */
public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;
    private Socket socket;
    private User user;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Connects to the server.
     */
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

    /**
     * Sends a message to the server.
     *
     * @param sendMessage the message to send
     */
    public void sendMessage(SendMessage sendMessage){
        try{
            output.writeObject(sendMessage);
            output.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Sends a login request to the server.
     *
     * @param loginRequest the login request to send
     * @return true if the login was successful, false otherwise
     */
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

    /**
     * Sends a registration request to the server.
     *
     * @param registerRequest the registration request to send
     * @return true if the registration was successful, false otherwise
     */
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

    /**
     * Requests a list of all users from the server.
     *
     * @param fetchAllUser the fetch all user request to send
     * @return a list of users
     */
    public List<User> getUsersList(FetchAllUser fetchAllUser){
        try {

            output.writeObject(fetchAllUser);
            output.flush();

            List <User> userList = new ArrayList<>();
            while(true){
                Object o = input.readObject();
                if(o == null)return new ArrayList<>();
                if(o instanceof Boolean) return userList;
                if(o instanceof User u) userList.add(u);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Requests a list of all chat rooms from the server.
     *
     * @return a list of chat rooms
     */
    public List<ChatRoom> getAllChatRooms(){

        try {
            output.writeObject(new FetchAllChatRooms(user));
            output.flush();

            List <ChatRoom> chatRoomList = new ArrayList<>();
            while(true){
                Object o = input.readObject();
                if(o == null)return new ArrayList<>();
                if(o instanceof Boolean) return chatRoomList;
                if(o instanceof ChatRoom c) chatRoomList.add(c);
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return new ArrayList<>();

    }

    /**
     * Requests a list of messages from the server.
     *
     * @param fetchMessages the fetch messages request to send
     * @return a list of messages
     */
    public List<Message> getMessages(FetchMessages fetchMessages){
        try{
            output.writeObject(fetchMessages);
            output.flush();

            List<Message> messages = new ArrayList<>();
            while(true){;
                Object o = input.readObject();
                if(o instanceof Boolean) return messages;
                if(o instanceof Message m) messages.add(m);
            }
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Requests to create a new chat room on the server.
     *
     * @param createNewChatRoom the create new chat room request to send
     * @return the created chat room
     */
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

    /**
     * Returns the user of the current session.
     *
     * @return the user of the current session
     */
    public User getUser(){
        return user;
    }


}
