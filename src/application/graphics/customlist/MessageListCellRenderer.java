package application.graphics.customlist;

import sharedresources.ImageMessage;
import sharedresources.TextMessage;
import sharedresources.interfaces.Message;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MessageListCellRenderer extends DefaultListCellRenderer{

    private static final int VERTICAL_PADDING = 8;
    private static final int HORIZONTAL_PADDING = 16;
    private static final int FONT_SIZE = 28;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setBorder(new EmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));
        label.setHorizontalAlignment(JLabel.LEFT);

        Font currentFont = label.getFont();
        label.setFont(new Font(currentFont.getName(), currentFont.getStyle(), FONT_SIZE));

        if (value instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) value;
            label.setText("<html><b>" + textMessage.getSender().getUsername() + ":</b> " + textMessage.getText() + "</html>");
            label.setIcon(null); // Remove any icon
        } else if (value instanceof ImageMessage) {
            ImageMessage imageMessage = (ImageMessage) value;
            label.setText("<html><b>" + imageMessage.getSender().getUsername() + ":</b></html>");
            label.setIcon(new ImageIcon(imageMessage.getImage()));
            label.setHorizontalTextPosition(JLabel.LEFT);
        }

        return label;
    }
}