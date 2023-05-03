package application.graphics;

import application.Client;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame{
    private JButton createUserButton;
    private JPanel rootPanel;
    private JButton okButton;
    private JButton exitButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Client client;
    public LoginWindow(Client client){
        this.client = client;
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width / 5,screeSize.height  / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

}
