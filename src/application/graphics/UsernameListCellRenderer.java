package application.graphics;

import javax.swing.*;
import java.awt.*;

public class UsernameListCellRenderer extends DefaultListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String username = (String) value;
        label.setText(username);
        return label;
    }
}
