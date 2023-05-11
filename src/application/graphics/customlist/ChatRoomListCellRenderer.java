package application.graphics.customlist;

import sharedresources.ChatRoom;
import sharedresources.User;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.*;
import java.util.List;


import javax.swing.border.EmptyBorder;



public class ChatRoomListCellRenderer extends DefaultListCellRenderer{
    private static final int VERTICAL_PADDING = 8;
    private static final int HORIZONTAL_PADDING = 12;
    private static final int FONT_SIZE = 20;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        ChatRoom chatRoom = (ChatRoom) value;
        List<User> userList = chatRoom.getUsers();
        StringBuilder allUsers = new StringBuilder();
        for(User u : userList){
            allUsers.append(u.getUsername()).append(", ");
        }

        label.setText("Chat room name: " + chatRoom.getChatRoomName() + "   Members: " + allUsers);

        // Set an empty border with padding around each item
        label.setBorder(new EmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));
        label.setHorizontalAlignment(JLabel.CENTER);
        // Set the font size
        Font currentFont = label.getFont();
        label.setFont(new Font(currentFont.getName(), currentFont.getStyle(), FONT_SIZE));

        return label;
    }
}
