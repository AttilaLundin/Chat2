package application;

import application.graphics.LoginWindow;

public class ChatGUI {
    public static void main(String[] args) {
        Client client = new Client();
        LoginWindow loginWindow = new LoginWindow(client);
        client.connectToServer();
    }
}