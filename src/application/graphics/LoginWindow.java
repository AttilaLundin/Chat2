package application.graphics;

import application.Client;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame{
    private JButton skapaAnvändareButton;
    private JPanel rootPanel;
    private JButton button1;
    private JButton button2;
    private JTextField textField1;
    private JPasswordField passwordField1;
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
