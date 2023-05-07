package controller;

import model.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;
    private Socket socket;
    private SessionUser sessionUser;
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
            output.writeObject(new ImageMessage.ImageMessageBuilder().image(bufferedImage).sender(sessionUser).chatRoomID(UUID.randomUUID()).timeSent().build()); // skickar msg till server, vad näst?
            output.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean sendLoginRequest(Login login){
        try{

            output.writeObject(login);
            output.flush();

            SessionUser sessionUser = (SessionUser) input.readObject();
            if(sessionUser != null) {
                System.out.println("user received");
                this.sessionUser = sessionUser;
                return true;
            }
            return false;

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean sendRegistrationRequest(Register register){
        try{

            output.writeObject(register);
            output.flush();

            SessionUser sessionUser = (SessionUser) input.readObject();
            if(sessionUser != null) {
                System.out.println("new user received");
                this.sessionUser = sessionUser;
                return true;
            }
            return false;

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return false;
    }

    public List<ChatRoom> getChatRooms(SessionUser sessionUser){

        try {
            output.writeObject(sessionUser);
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

    public SessionUser getSessionUser(){
        return sessionUser;
    }


}
