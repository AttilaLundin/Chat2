package application;

import ui.LoginWindow;

/**
 * The main class for running the chat application.
 * This class initializes a client, opens a login window, and connects the client to the server.
 */
public class ChatGUI {

    /**
     * The entry point of the application.
     * This method creates a new Client, opens a LoginWindow, and connects the Client to the server.
     *
     * @param args an array of command-line arguments for the application
     */
    public static void main(String[] args) {
        Client client = new Client();
        LoginWindow loginWindow = new LoginWindow(client);
        client.connectToServer();
    }
}