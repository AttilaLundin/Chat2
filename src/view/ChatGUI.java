package view;

import controller.Client;
import view.graphics.LoginWindow;

public class ChatGUI {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Client client = new Client();
        LoginWindow loginWindow = new LoginWindow(client);
        client.connectToServer();

    }
}