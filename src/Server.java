import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents a server for the chat application, responsible for managing incoming client connections and communication.
 *
 * The Server class creates a server socket to listen for incoming client connections. When a new connection is established,
 * it creates a new ClientHandler object and starts a new thread to handle the communication between the server and the client.
 */
public class Server {
    private static final int PORT = 1234;

    /**
     * Starts the server and listens for incoming connections.
     *
     * This method creates a ServerSocket object on the specified port, and listens for incoming connections.
     * When a new connection is made, a new ClientHandler object is created and started in a new thread.
     * The method runs indefinitely, listening for new connections.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args){

        try (ServerSocket serverSocket = new ServerSocket(PORT)){

            while(true){

                // Accept incoming client connections,
                // listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made.
                Socket socket = serverSocket.accept();

                // Create a new ClientHandler object to handle the client connection
                ClientHandler clientHandler = new ClientHandler(socket);

                // Create a new Thread with the ClientHandler object and start it
                // This allows the server to handle multiple client connections concurrently
                new Thread(clientHandler).start();
                System.out.println("New Thread started for socket: " + socket.getPort());
            }
        } catch (IOException e){
            System.err.println("Server Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
