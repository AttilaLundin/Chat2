package View;

import Model.Client;

public class ChatGUI {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Client client = new Client();
        //Create_User createUser = new Create_User();
        LoginWindow loginWindow = new LoginWindow(client);

        client.connectToServer();
    }
}