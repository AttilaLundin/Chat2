package view.graphics;

import controller.Client;
import model.User;

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
    private Client client;
    public LoginWindow(Client client){
        this.client = client;
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width / 5,screeSize.height  / 2);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserCreation createUser = new UserCreation();

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

                System.out.println("username: " + username + " Password: " + password);
                if(username.equals("") || password.equals("")){
                    System.out.println("username and/or login field(s) empty, try again");
                }
                else{
                    User sessionUser = client.sendLoginRequest(username, password);
                    if(sessionUser != null){
                        dispose();
                        Dashboard dashboard = new Dashboard(sessionUser, client);
                    }
                    else{
                        System.out.println("Incorrect credentials: try again. JLAbel");
                        usernameField.setText("");
                        passwordField.setText("");
                    }
                }
            }
        });
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

}
