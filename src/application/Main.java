package application;

import application.graphics.Gui;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Client client = new Client();
        Gui gui = new Gui(client);
        client.connectToServer();
    }

}