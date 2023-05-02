import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;
    private Socket socket;

    private ObjectOutputStream ois;
    private ObjectInputStream oos;

    public void connectToServer(){
        boolean connected = false;

        while(!connected){
            try {
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                socket.setKeepAlive(true);
                connected = socket.isConnected();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
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

    public void sendMessage(){

    }


}
