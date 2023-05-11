package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class Server {
    private static final int PORT = 1234;
    private static final String USER_STORAGE_FILE = "userStorage.ser";
    private static final String CHAT_ROOM_STORAGE_FILE = "chatRoomStorage.ser";

    public static void main(String[] args){

        ChatRoomStorage chatroomStorage = loadChatRoomStorage();
        UserStorage userStorage = loadUserStorage();

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                saveUserStorage(userStorage);
                saveChatRoomStorage(chatroomStorage);
            }));

            while(true){
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, chatroomStorage, userStorage);
                new Thread(clientHandler).start();
                System.out.println("New Thread started for socket: " + socket.getPort());
            }
        } catch (IOException e){
            System.err.println("Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static UserStorage loadUserStorage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter filepath if you want to load file for users\nType \"n\" if you want a new database\n");
        String filePath = scanner.nextLine();
        if(filePath.equals("n")) return new UserStorage();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (UserStorage) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading user storage: " + e.getMessage());
            return new UserStorage();
        }
    }

    private static void saveUserStorage(UserStorage userStorage) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_STORAGE_FILE))) {
            oos.writeObject(userStorage);
        } catch (IOException e) {
            System.err.println("Error saving user storage: " + e.getMessage());
        }
    }

    private static ChatRoomStorage loadChatRoomStorage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter filepath if you want to load file for users\nType \"n\" if you want a new database\n");
        String filePath = scanner.nextLine();
        if(filePath.equals("n")) return new ChatRoomStorage();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ChatRoomStorage) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading chat room storage: " + e.getMessage());
            return new ChatRoomStorage();
        }
    }

    private static void saveChatRoomStorage(ChatRoomStorage chatRoomStorage) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CHAT_ROOM_STORAGE_FILE))) {
            oos.writeObject(chatRoomStorage);
        } catch (IOException e) {
            System.err.println("Error saving chat room storage: " + e.getMessage());
        }
    }
}