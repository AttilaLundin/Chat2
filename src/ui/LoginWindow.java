package ui;

import application.Client;
import common.requests.LoginRequest;


import javax.swing.*;


import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A JFrame class that provides a login interface for users to input their username and password.
 * The interface includes buttons to submit the login information, create a new user, and exit the application.
 */
public class LoginWindow extends JFrame{
    private JButton createUserButton;
    private JPanel rootPanel;
    private JButton loginButton;
    private JButton exitButton; //8===3
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel buttonPanel;
    private JButton button1;
    private final Client client;

    /**
     * Constructs a new LoginWindow instance with a given client.
     * This also sets up the login interface and makes it visible.
     *
     * @param client the client that is used to send login requests
     */
    public LoginWindow(Client client){
        this.client = client;
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width / 5,screeSize.height  / 2);
        setMinimumSize(minmumWindowSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeButtons();
        getRootPane().setDefaultButton(loginButton);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initializes the buttons on the login interface, including their event listeners.
     * The 'create user' button opens a new UserCreation window and closes the login window.
     * The 'exit' button closes the application.
     * The 'login' button sends a login request with the inputted username and password,
     * opens a new Dashboard window and closes the login window if the login is successful,
     * or displays an error message and clears the input fields if the login is unsuccessful.
     */
    public void initializeButtons(){
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserCreation userCreation = new UserCreation(client);
                dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                boolean loginSuccessful = client.sendLoginRequest(new LoginRequest.LoginBuilder().username(username).password(password).build());
                if(loginSuccessful){
                    Dashboard dashboard = new Dashboard(client);
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password\nTry again!", "Could not verify Account", JOptionPane.INFORMATION_MESSAGE);
                    usernameField.setText("");
                    passwordField.setText("");
                }


                }

        });



    }
}
