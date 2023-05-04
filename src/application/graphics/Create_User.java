package application.graphics;

import application.Register;
import application.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Create_User extends JFrame {
    private JPanel panel1;
    private JButton createButton;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JTextField textField2;
    private JPasswordField passwordField2;
    private Register user;
public Create_User() {
    Dimension minmumWindowSize = new Dimension(500, 300);
    Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
    setContentPane(panel1);
    setSize(screeSize.width / 5,screeSize.height  / 5);
    setMinimumSize(minmumWindowSize);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
     user = new Register(textField1.getText(), passwordField1.getText(),textField2.getText());

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
