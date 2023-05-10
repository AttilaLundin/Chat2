package application.graphics.customlist;

import javax.swing.DefaultListSelectionModel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsernameListSelectionModel extends DefaultListSelectionModel {
    private int lastClickedIndex = -1;
    private Timer timer;

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
