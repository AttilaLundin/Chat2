package application;

import application.graphics.LoginWindow;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Client client = new Client();
        LoginWindow loginWindow = new LoginWindow(client);
        client.connectToServer();
    }

}