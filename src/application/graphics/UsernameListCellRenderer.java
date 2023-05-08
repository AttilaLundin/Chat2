package application.graphics;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class UsernameListCellRenderer extends DefaultListCellRenderer{
    private static final int VERTICAL_PADDING = 4;
    private static final int HORIZONTAL_PADDING = 8;
    private static final int FONT_SIZE = 16;
    private static final Color SELECTED_BACKGROUND_COLOR = new Color(173, 216, 230);
    private static final Color SELECTED_FOREGROUND_COLOR = Color.WHITE;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, false, cellHasFocus);
        String username = (String) value;
        label.setText(username);

        label.setBorder(new EmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));
        Font currentFont = label.getFont();
        label.setFont(new Font(currentFont.getName(), currentFont.getStyle(), FONT_SIZE));

        boolean indexIsSelected = Arrays.stream(list.getSelectedIndices()).anyMatch(i -> i == index);

        if(isSelected || list.isSelectedIndex(index)){
            label.setBackground(SELECTED_BACKGROUND_COLOR);
            label.setForeground(SELECTED_FOREGROUND_COLOR);
            label.setOpaque(true);
        }
        else{
            label.setOpaque(false);
        }


        return label;
    }
}
