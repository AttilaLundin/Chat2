package graphics.customlist;

import javax.swing.DefaultListSelectionModel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**

 The UsernameListSelectionModel class is responsible for managing the selection of items in a JList.

 It keeps track of the last clicked index and provides a delayed clearing of the selection after consecutive clicks.
 */
public class UsernameListSelectionModel extends DefaultListSelectionModel {
    private int lastClickedIndex = -1;
    private Timer timer;

    /**

     Creates a new instance of UsernameListSelectionModel.
     Initializes the timer with a delay of 200 milliseconds and sets an ActionListener
     to reset the lastClickedIndex after the delay.
     */
    public UsernameListSelectionModel() {
        int delay = 200; // A 200 milliseconds delay between consecutive clicks
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastClickedIndex = -1;
            }
        });
        timer.setRepeats(false);
    }

    /**

     Sets the selection interval for the list.
     Overrides the DefaultListSelectionModel's setSelectionInterval method.
     If the specified index is the same as the last clicked index, it returns without making any changes.
     If the index is already selected, it removes the selection.
     Otherwise, it adds the selection.
     Sets the last clicked index to the specified index and restarts the timer.

     @param index0 The index of the first item in the interval.

     @param index1 The index of the last item in the interval.
     */
    @Override
    public void setSelectionInterval(int index0, int index1) {
        if (index0 == lastClickedIndex) {
            return;
        }

        if (isSelectedIndex(index0)) {
            super.removeSelectionInterval(index0, index1);
        } else {
            super.addSelectionInterval(index0, index1);
        }

        lastClickedIndex = index0;
        timer.restart();
    }
}
