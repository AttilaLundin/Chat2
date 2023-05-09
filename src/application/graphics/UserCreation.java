package application.graphics;

import application.Client;
import sharedresources.requests.RegisterRequest;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.Dimension;
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
    private final String[] invalidUsernameChars = {"&", "=", "_", "'", "-", ",", "<", ">", "."};
    private final String[] invalidPasswordChars = { "(", ")", "{", "}", "[", "]", "|", "'", "´", "¬", "¦", "!", "\"", "£", "$", "%",
            "<", ">", "&", "*", ";", ":", "#", "^", "-", "_", "~", "+", "=", ",", "@", "."};


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
                if (password.equals(controllPassword) && validUsername(username) && validPassword(password) && validDisplayName(displayName)){
                    boolean registrationSuccessful = client.sendRegistrationRequest(new RegisterRequest.RegisterBuilder().username(username).password(password).displayName(displayName).build());
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
                }else{
                    System.out.println("Invalid username or password: try again.");
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

    private boolean validUsername(String userName){
        if(userName.length() < 4 || userName.length() > 30) return false;
        for(String s : invalidUsernameChars){
            if(userName.contains(s)) return false;
        }
        return true;
    }

    private boolean validPassword(String password){
        if(password.length() < 4 || password.length() > 30) return false;
        for(String s : invalidPasswordChars){
            if(password.contains(s)) return false;
        }
        return true;
    }

    private boolean validDisplayName(String displayName){
        return displayName.length() >= 1 && displayName.length() <= 30;
    }


}
