package application;

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
    // private User user;
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
            output.writeObject(new Message("test", bufferedImage, new User(), chatRoom.getChatRoomID())); // skickar msg till server, vad näst?
            output.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean sendLoginRequest(User user){
        try{
            output.writeObject(user);
            output.flush();

            long timeout = System.currentTimeMillis();
            while(System.currentTimeMillis() - timeout < 5000){
                Object object = input.readObject();
                if(object instanceof Boolean bool){
                    System.out.println("response from server " + bool);
                    return bool;
                }
            }

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

}
