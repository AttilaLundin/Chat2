package application.graphics;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class ChatRoomListCellRenderer extends DefaultListCellRenderer {
    private static final int VERTICAL_PADDING = 4;
    private static final int HORIZONTAL_PADDING = 12;
    private static final int FONT_SIZE = 24;
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String chatRoomName = (String) value;

        label.setBorder(new EmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));

        Font currentFont = label.getFont();
        label.setFont(new Font(currentFont.getName(), currentFont.getStyle(), FONT_SIZE));

        return label;
    }
}
