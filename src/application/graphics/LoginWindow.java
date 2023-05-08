package application.graphics;

import application.Client;
import sharedresources.requests.LoginRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    public LoginWindow(Client client){
        this.client = client;
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width / 5,screeSize.height  / 2);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeButtons();
        getRootPane().setDefaultButton(loginButton);
        setVisible(true);
    }


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
                    System.out.println("Incorrect credentials: try again. JLAbel");
                    usernameField.setText("");
                    passwordField.setText("");
                }


                }

        });



    }
}
