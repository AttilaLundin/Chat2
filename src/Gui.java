import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Gui extends JFrame{
    private JPanel homePanel;
    private Client client;

    public Gui(Client client){
        this.client = client;
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(homePanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeHomePanel();
        setVisible(true);
    }

    /**
     * Initializes the home panel, enabling drag and drop functionality for images.
     *
     * Sets up a DropTarget for the home panel, allowing users to drag and drop images onto the panel, and processes the dropped
     * images by sending them to the server.
     */
    private void initializeHomePanel(){
        homePanel.setDropTarget(new DropTarget(homePanel, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transferable = dtde.getTransferable();
                    if (!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) dtde.rejectDrop();

                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    if(!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) throw new Exception();
                    java.util.List<File> images = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    ArrayList<String> filePaths = new ArrayList<>();
                    for (File i : images) {
                        filePaths.add(i.getAbsolutePath());
                        System.out.println("File path: " + i.getAbsolutePath());
                    }
                    client.sendMessage(filePaths.get(0));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}
