package view.graphics;

import controller.Client;
import model.ChatRoom;
import model.SessionUser;

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

public class Dashboard extends JFrame{
    private JPanel rootPanel;
    private JPanel sidePanel;
    private JButton dashboardButton;
    private JButton githubButton;
    private JButton contactsButton;
    private JButton messageButton;
    private JPanel mainPanel;
    private JTable chatroomTable;
    private JScrollPane chatRoomScrollPane;
    private JPanel chatRoomPanel;
    private JLabel displayNameLabel;
    private JLabel dispalynameLabel;
    private SessionUser sessionUser;
    private Client client;

    public Dashboard(Client client){
        this.user = user;
        this.client = client;
        sessionUser = client.getSessionUser();
        client.connectToServer();
        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeMainPanel();
        setDisplayName();
        setVisible(true);
    }

    private void setDisplayName(){
        dispalynameLabel.setText("Welcome " + sessionUser.getDisplayname() + ", have a capybara day!");
    }


    private void initializeMainPanel(){
        mainPanel.setDropTarget(new DropTarget(mainPanel, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transferable = dtde.getTransferable();
                    if (!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) dtde.rejectDrop();

                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    if(!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) throw new Exception();
                    List <File> images = new ArrayList<>();
                    Object object = transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    if(object instanceof List<?> list){
                        for(Object o : list){
                            if(o instanceof File image) images.add(image);

                        }
                    }

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
