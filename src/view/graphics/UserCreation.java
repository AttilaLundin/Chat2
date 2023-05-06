package view.graphics;

import controller.Client;
import model.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserCreation extends JFrame {
    private JPanel rootPanel;
    private JButton createButton;
    private JTextField usernameField;
    private JPasswordField passwordField2;
    private JTextField displaynameField;
    private JPasswordField passwordField;
    private JButton capybara;
    private JButton loginButton;
    private Client client;


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


    public void initializeButtons(){
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String controllPassword = new String(passwordField2.getPassword());
                String displayName = displaynameField.getText();
                if (password.equals(controllPassword) ){
                    boolean registrationSuccessful = client.sendRegistrationRequest(new Register.RegisterBuilder().username(username).password(password).displayname(displayName).build());
                    if(registrationSuccessful){
                        Dashboard dashboard = new Dashboard(client);
                        dispose();
                    }
                    else{
                        System.out.println("Invalid username: try again. JLAbel");
                        usernameField.setText("");
                        passwordField.setText("");
                        passwordField2.setText("");
                        displaynameField.setText("");
                    }
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


}
