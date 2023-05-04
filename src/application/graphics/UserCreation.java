package application.graphics;

import application.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserCreation extends JFrame {
    private JPanel rootPanel;
    private JButton createButton;
    private JTextField usernameField;
    private JPasswordField reenteredpasswordField;
    private JTextField textField2;
    private JPasswordField passwordField2;
    private JButton capybara;
    private Register user;
    public UserCreation() {
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width / 5,screeSize.height  / 2);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        user = new Register(usernameField.getText(), reenteredpasswordField.getText(),textField2.getText());

        createButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("User Created");
                //System.exit(0);
                dispose();
            }
        });
    }
}
