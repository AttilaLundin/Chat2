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

/**
 * Represents the graphical user interface (GUI) for the chat application, allowing users to interact with the application.
 *
 * The GUI class provides an interface for users to interact with the chat application, including sending messages and images,
 * as well as navigating through various features and functionalities.
 */
public class GUI extends JFrame{

    private JPanel rootPanel;
    private JPanel sidePanel;
    private JButton homeButton;
    private JButton gitHubButton;
    private JButton messageButton;
    private JButton contactsButton;
    private JPanel homePanel;
    private User user;
    private Client client;

    /**
     * Constructs a new GUI instance associated with the provided User object.
     *
     * Initializes the graphical user interface, sets its size and position, and establishes a connection to the server.
     *
     * @param user User object representing the current user interacting with the GUI
     */
    public GUI(User user){
        this.user = user;
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeHomePanel();
        setVisible(true);
        client = new Client();
        client.connectToServer();
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
                    List<File> images = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    ArrayList<String> filePaths = new ArrayList<>();
                    for (File i : images) {
                        filePaths.add(i.getAbsolutePath());
                        System.out.println("File path: " + i.getAbsolutePath());
                    }
                    //client.imageProcessing(user, new ChatRoom(), filePaths);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}