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

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public void connectToServer(){
        boolean connected = false;

        while(!connected){
            try {
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                socket.setKeepAlive(true);
                connected = socket.isConnected();
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
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
            oos.writeObject(new Message("test", bufferedImage, new User()));
            oos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }




}
