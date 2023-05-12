package graphics.customlist;

import common.ChatRoom;
import common.RegisteredUser;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.*;
import java.util.List;
import java.util.StringJoiner;


import javax.swing.border.EmptyBorder;


/**

 The ChatRoomListCellRenderer class is responsible for rendering ChatRoom objects in a JList.

 It customizes the appearance of the list cells by displaying the chat room name and its members.
 */
public class ChatRoomListCellRenderer extends DefaultListCellRenderer{
    private static final int VERTICAL_PADDING = 8;
    private static final int HORIZONTAL_PADDING = 12;
    private static final int FONT_SIZE = 20;

    /**
     * Returns the component used to render the list cell.
     *
     * @param list          The JList being painted.
     * @param value         The value to be rendered.
     * @param index         The cell index.
     * @param isSelected   True if the specified cell is selected; otherwise, false.
     * @param cellHasFocus  True if the specified cell has focus; otherwise, false.
     * @return              The component used to render the list cell.
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        ChatRoom chatRoom = (ChatRoom) value;
        List<RegisteredUser> userList = chatRoom.getUsers();
        StringJoiner allUsers = new StringJoiner(", ");
        for(RegisteredUser u : userList){
            allUsers.add(u.getUsername());
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
