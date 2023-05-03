package server;

import application.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Represents a client handler for the server, responsible for managing communication with a connected client.
 *
 * The ClientHandler class manages communication between the server and a connected client. It reads incoming messages
 * from clients and takes appropriate actions based on the message type. The class implements the Runnable interface,
 * allowing it to be run in a separate thread.
 */
public class ClientHandler implements Runnable{
    private final Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private ChatHistory chatHistory;

    public ClientHandler(Socket socket, ChatHistory chatHistory){
        this.socket = socket;
        this.chatHistory = chatHistory;
    }

    public void run(){
        try(ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream())){
            socket.setKeepAlive(true);

            while (true){
                Object object = input.readObject();
                if(object instanceof Message message){
                    chatHistory.addMessage(message.getChatRoomID(), message);
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}


/*

    /**
     * Constructor for ClientHandler object.
     *
     * Creates a new ClientHandler object to handle incoming client connections.
     * Initializes instance variables and creates a new LoginHandler object.
     *
     * @param socket Socket representing the connection between the server and a client
private final LoginHandler loginHandler;

    public ClientHandler(Socket socket){
    this.socket = socket;
    loginHandler = new LoginHandler();
    }

    /**
     * Handles incoming client connections.
     *
     * This method reads incoming messages from clients and takes appropriate actions based on the message type.
     * Message types are indicated by an integer value, which is read from the input stream. Depending on the message type,
     * the method may read additional data from the input stream, write data to the output stream, or perform other
     * actions. The method runs until a message with a message type of -1 is received, at which point it closes the
     * input and output streams and the socket.

    @Override
    public void run() {
        try(DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())){
            socket.setKeepAlive(true);
            boolean done = false;
            while (!done){

                // Read an integer from the input stream, which represents the message type
                int messageType = dataInputStream.readInt();

                // Use a switch statement to handle different message types based on the received integer
                // Handle messageType -1: set done to true to exit the while loop, closing the connection
                // Handle messageType  0: text messages
                // Handle messageType  1: image messages
                // Handle messageType  2: user authentication, login attempt
                // Handle messageType  3: user registration
                switch (messageType){
                    case -1:
                        done = true;
                        break;
                    case 0:
                        //meddelanden
                        break;
                    case 1:
                        System.out.println("Sender: " + dataInputStream.readUTF());
                        System.out.println("Chatroom" + dataInputStream.readInt());

                        int length = dataInputStream.readInt();
                        System.out.println(length);
                        byte[] imageBytes = new byte[length];
                        dataInputStream.readFully(imageBytes);
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                        BufferedImage receivedImage = ImageIO.read(byteArrayInputStream);
                        break;
                    case 2:
                        String userName = dataInputStream.readUTF();
                        String password = dataInputStream.readUTF();
                        String[] sessionUser = loginHandler.validateCredentials(userName, password);
                        if(sessionUser == null){
                            dataOutputStream.writeInt(404);
                            dataOutputStream.flush();
                            break;
                        }
                        dataOutputStream.writeInt(0);
                        dataOutputStream.writeUTF(sessionUser[0]);
                        dataOutputStream.writeUTF(sessionUser[1]);
                        dataOutputStream.flush();
                        break;
                    case 3:
                        userName = dataInputStream.readUTF();
                        password = dataInputStream.readUTF();
                        String displayName = dataInputStream.readUTF();

                        int userCreation = loginHandler.createNewUser(userName,password,displayName);
                        dataOutputStream.writeInt(userCreation);



                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }







 */
