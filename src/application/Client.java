package application;

import sharedresources.User;
import sharedresources.ImageMessage;
import sharedresources.TextMessage;
import sharedresources.ChatRoom;
import sharedresources.requests.GetUsersRequest;
import sharedresources.requests.LoginRequest;
import sharedresources.requests.RegisterRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

    public void sendTextMessage(String text, UUID chatRoomID){
        try{
            output.writeObject(new TextMessage.TextMessageBuilder().text(text).sender(user).chatRoomID(chatRoomID).build());
            output.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
  public void sendImageMessage(String filePath, UUID chatRoomID){
        try{
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            output.writeObject(new ImageMessage.ImageMessageBuilder().image(bufferedImage).sender(user).chatRoomID(chatRoomID).build()); // skickar msg till server, vad näst?
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

    public ArrayList<User> getUsersList(GetUsersRequest usersRequest){
        try {
            ArrayList<User> users = new ArrayList<>();

            output.writeObject(usersRequest);
            output.flush();

            Object object = input.readObject();

            if(object instanceof ArrayList<?> list){
                for(Object o : list){
                    if(o instanceof User s) users.add(s);
                }
                return users;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public List<ChatRoom> getChatRooms(){

        try {
            output.writeObject(user);
            output.flush();

            Object object = input.readObject();
            List <ChatRoom> chatRooms = new ArrayList<>();
            if(object instanceof List<?> list){
                for(Object o : list) if(o instanceof ChatRoom chatRoom) chatRooms.add(chatRoom);
            }
            return chatRooms;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }


    public User getSessionUser(){
        return user;
    }


}
