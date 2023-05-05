package application;

import application.graphics.Create_User;
import application.graphics.LoginWindow;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Client client = new Client();
        //Create_User createUser = new Create_User();
        client.connectToServer();
        LoginWindow loginWindow = new LoginWindow(client);

    }
}