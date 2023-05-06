package controller;

import model.ChatRoom;
import model.Login;
import model.Register;
import model.SessionUser;

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
    private SessionUser sessionUser;
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
          //  output.writeObject(new TextMessage("test", bufferedImage, new SessionUser.Builder("test", "testp").build(), chatRoom.getChatRoomID())); // skickar msg till server, vad näst?
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


}
