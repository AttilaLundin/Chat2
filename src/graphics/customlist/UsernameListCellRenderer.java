package graphics.customlist;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Component;

import common.RegisteredUser;

import javax.swing.border.EmptyBorder;
import java.awt.*;


/**

 The UsernameListCellRenderer class is responsible for rendering User objects in a JList.

 It customizes the appearance of the list cells by displaying the username of each user.
 */
public class UsernameListCellRenderer extends DefaultListCellRenderer{
    private static final int VERTICAL_PADDING = 4;
    private static final int HORIZONTAL_PADDING = 8;
    private static final int FONT_SIZE = 16;


    /**

     Returns the component used to render the list cell.

     @param list The JList being painted.

     @param value The value to be rendered.

     @param index The cell index.

     @param isSelected True if the specified cell is selected; otherwise, false.

     @param cellHasFocus True if the specified cell has focus; otherwise, false.

     @return The component used to render the list cell.
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        RegisteredUser user = (RegisteredUser) value;

        label.setText(user.getUsername());

        // Set an empty border with padding around each item
        label.setBorder(new EmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));
        label.setHorizontalAlignment(JLabel.CENTER);

        // Set the font size
        Font currentFont = label.getFont();
        label.setFont(new Font(currentFont.getName(), currentFont.getStyle(), FONT_SIZE));

        return label;
    }
}
