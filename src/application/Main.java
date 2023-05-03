package application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Client client = new Client();
        client.connectToServer();
    }

}