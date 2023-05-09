package application.graphics;

import application.Client;
import sharedresources.ImageMessage;
import sharedresources.User;
import sharedresources.requests.SendMessageRequest;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JFrame{
    private JPanel rootPanel;
    private JPanel sidePanel;
    private JButton gitHubButton;
    private JButton homeScreenButton;
    private JPanel mainPanel;
    private JScrollPane chatRoomScrollPane;
    private JPanel chatRoomPanel;
    private JLabel displayNameLabel;
    private JList userList;
    private List<String> selectedUsernames = new ArrayList<>();
    private JButton createCapyHerdButton;
    private JPanel userPanel;
    private JButton createChatRoomButton;
    private sharedresources.ChatRoom displayedChatroom;
    private User user;
    private Client client;

    public Dashboard(Client client){

        this.client = client;
        user = client.getSessionUser();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        userList.setModel(listModel);
        userList.setCellRenderer(new UsernameListCellRenderer());
        userList.setSelectionModel(new CustomListSelectionModel());
        userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedUsernames.clear();
                    for (int index : userList.getSelectedIndices()) {
                        selectedUsernames.add(listModel.getElementAt(index));
                    }
                    System.out.println("Selected usernames: " + selectedUsernames);
                }
            }
        });

        gitHubButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try{
                        desktop.browse(new URI(url));
                    }catch (IOException | URISyntaxException i){
                        i.printStackTrace();
                    }
                }
            }
        });

        listModel.addElement("Attila");
        listModel.addElement("Odai");
        listModel.addElement("Roger");
        listModel.addElement("Shark");
        listModel.addElement("Binki");

        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeDragAndDrop();
        setDisplayName();
        chatRoomPanel.setLayout(new BorderLayout());
        initChatRoomDisplay();
        setVisible(true);

    }

    public void initChatRoomDisplay(){

        List<sharedresources.ChatRoom> chatRooms = client.getChatRooms();
        System.out.println(chatRooms.size());
        if(chatRooms.isEmpty()) return;
        String[] items = new String[chatRooms.size()];
        for(int i = 0; i < chatRooms.size(); i++){
            List<User> usersInRoom = chatRooms.get(i).getUsersInChatRoom();
            StringBuilder stringBuilder = new StringBuilder();
            for(int j = 0; j < usersInRoom.size(); j++){
                stringBuilder.append(usersInRoom.get(j).getDisplayName());
                if(j < usersInRoom.size() - 1)stringBuilder.append(", ");
            }
            items[i] = stringBuilder.toString();

        }

        JList<String> list = new JList<>(items);

        chatRoomScrollPane = new JScrollPane(list);
        chatRoomScrollPane.setBackground(new Color(26,26,36));
        chatRoomPanel.add(chatRoomScrollPane, BorderLayout.CENTER);

    }

    private void setDisplayName(){
        displayNameLabel.setText("Welcome " + user.getDisplayName() + ", have a capybara day!");
    }


    private void initializeDragAndDrop(){
        mainPanel.setDropTarget(new DropTarget(mainPanel, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            //TODO: ändra mainpanel till chattrumspanelen eller den här Jlist
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transferable = dtde.getTransferable();
                    if (!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) dtde.rejectDrop();

                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    if(!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) throw new UnsupportedFlavorException(DataFlavor.javaFileListFlavor);
                    Object object = transferable.getTransferData(DataFlavor.javaFileListFlavor);

                    if(object instanceof List<?> list){
                        for(Object o : list){
                            if(o instanceof File image){
                                BufferedImage bufferedImage = ImageIO.read(image);
                                client.sendMessage(new SendMessageRequest(displayedChatroom, new ImageMessage.ImageMessageBuilder().image(bufferedImage).sender(user).build()));
                            }
                        }
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}
