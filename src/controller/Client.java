package controller;

import model.ChatRoom;
import model.Message;
import model.Register;
import model.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;
    private Socket socket;
    private User user;
    private ChatRoom chatRoom;

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
                System.out.println("connected");
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

    public void sendMessage(String filePath){
        try{
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            output.writeObject(new Message("test", bufferedImage, new User.Builder("test", "testp").build(), chatRoom.getChatRoomID())); // skickar msg till server, vad näst?
            output.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public User sendLoginRequest(String username, String password){
        try{
            output.writeObject(new User.Builder(username, password).build());
            output.flush();

            User sessionUser = (User) input.readObject();
            if(sessionUser != null) {
                System.out.println("user received");
                return sessionUser;
            }
            return null;

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}
