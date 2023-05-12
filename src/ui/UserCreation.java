package ui;

import application.Client;
import common.requests.RegisterRequest;

import javax.swing.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A JFrame class that provides a user registration interface for users to input their username and password.
 * The interface includes buttons to submit the registration information and go to the login page.
 */
public class UserCreation extends JFrame {
    private JPanel rootPanel;
    private JButton createButton;
    private JTextField usernameField;
    private JPasswordField passwordField2;
    private JPasswordField passwordField;
    private JButton capybara;
    private JButton loginButton;
    private Client client;
    private final String[] invalidUsernameChars = {"&", "=", "_", "'", "-", ",", "<", ">", "."};
    private final String[] invalidPasswordChars = { "(", ")", "{", "}", "[", "]", "|", "'", "´", "¬", "¦", "!", "\"", "£", "$", "%",
                                                    "<", ">", "&", "*", ";", ":", "#", "^", "-", "_", "~", "+", "=", ",", "@", "."};


    /**
     * Constructs a new UserCreation instance with a given client.
     * This also sets up the registration interface and makes it visible.
     *
     * @param client the client that is used to send registration requests
     */
    public UserCreation(Client client) {
        this.client = client;

        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width / 5,screeSize.height  / 2);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeButtons();
        getRootPane().setDefaultButton(createButton);
        setVisible(true);
    }

    /**
     * Initializes the buttons on the registration interface, including their event listeners.
     * The 'create user' button sends a registration request with the inputted username and password,
     * opens a new Dashboard window and closes the registration window if the registration is successful,
     * or displays an error message and clears the input fields if the registration is unsuccessful.
     * The 'login' button opens a new LoginWindow and closes the registration window.
     */
    public void initializeButtons(){
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String controllPassword = new String(passwordField2.getPassword());
                if (password.equals(controllPassword) && validUsername(username) && validPassword(password)){
                    boolean registrationSuccessful = client.sendRegistrationRequest(new RegisterRequest.RegisterBuilder().username(username).password(password).build());
                    if(registrationSuccessful){
                        Dashboard dashboard = new Dashboard(client);
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Invalid credentials, please try again!", "Registration unsuccessful", JOptionPane.INFORMATION_MESSAGE);
                        usernameField.setText("");
                        passwordField.setText("");
                        passwordField2.setText("");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Username already taken\nPlease try again", "Registration unsuccessful" , JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginWindow loginWindow = new LoginWindow(client);
                dispose();
            }
        });
    }

    /**
     * Checks if a given username is valid.
     * A username is considered valid if it is between 4 and 30 characters long, and does not contain any invalid characters.
     *
     * @param userName the username to check
     * @return true if the username is valid, false otherwise
     */
    private boolean validUsername(String userName){
        if(userName.length() < 4 || userName.length() > 30) return false;
        for(String s : invalidUsernameChars){
            if(userName.contains(s)) return false;
        }
        return true;
    }

    /**
     * Checks if a given password is valid.
     * A password is considered valid if it is between 4 and 30 characters long, and does not contain any invalid characters.
     *
     * @param password the password to check
     * @return true if the password is valid, false otherwise
     */
    private boolean validPassword(String password){
        if(password.length() < 4 || password.length() > 30) return false;
        for(String s : invalidPasswordChars){
            if(password.contains(s)) return false;
        }
        return true;
    }
}
