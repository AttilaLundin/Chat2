package application.graphics;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

class UsernameListCellRenderer extends DefaultListCellRenderer{
    private static final int VERTICAL_PADDING = 4;
    private static final int HORIZONTAL_PADDING = 8;
    private static final int FONT_SIZE = 16;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String username = (String) value;
        label.setText(username);

        // Set an empty border with padding around each item
        label.setBorder(new EmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));
        label.setHorizontalAlignment(JLabel.CENTER);

        // Set the font size
        Font currentFont = label.getFont();
        label.setFont(new Font(currentFont.getName(), currentFont.getStyle(), FONT_SIZE));

        return label;
    }
}
