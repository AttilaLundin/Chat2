package application.graphics;

import application.Client;
import sharedresources.ChatRoom;
import sharedresources.ImageMessage;
import sharedresources.User;
import sharedresources.requests.AddChatRoomRequest;
import sharedresources.requests.GetUsersRequest;
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
import java.util.Map;
import java.util.Set;

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
    private List<User> selectedUsernames = new ArrayList<>();
    private JButton createCapyHerdButton;
    private JPanel userPanel;
    private JPanel displayBarPanel;
    private JList chatRoomList;
    private JScrollPane userListScrollPane;
    private JButton enterButton;
    private JButton button1;
    private JButton createChatRoomButton;
    private sharedresources.ChatRoom displayedChatroom;
    private User user;
    private Client client;

    public Dashboard(Client client){

        this.client = client;
        user = client.getSessionUser();

        DefaultListModel<User> userListModel = new DefaultListModel<>();
        userList.setModel(userListModel);
        userList.setCellRenderer(new UsernameListCellRenderer());
        userList.setSelectionModel(new UsernameListSelectionModel());
        userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //TODO: ta bort när vi är klara med testning
        DefaultListModel<String> chatRoomListModel = new DefaultListModel<>();
        chatRoomList.setModel(chatRoomListModel);
        chatRoomList.setCellRenderer(new ChatRoomListCellRenderer());


        chatRoomListModel.addElement("Attila");

        chatRoomListModel.addElement("Odai");
        chatRoomListModel.addElement("Roger");
        chatRoomListModel.addElement("Shark");
        chatRoomListModel.addElement("Binki");


        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedUsernames.clear();
                    System.out.println("Selected usernames: ");
                    int i = 0;
                    for (int index : userList.getSelectedIndices()) {
                        selectedUsernames.add(userListModel.getElementAt(index));
                        System.out.println(" " + selectedUsernames.get(i));
                        i++;
                    }
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

        createCapyHerdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<User> selectedUsers = userList.getSelectedValuesList();
                if(selectedUsers == null || selectedUsers.isEmpty()) return;
                selectedUsers.add(user);
                ChatRoom chatRoom = client.addChatRoom(new AddChatRoomRequest(selectedUsers));
                if(chatRoom != null){
                    dispose();
                    Chat chat = new Chat(client, chatRoom);
                }
            }
        });





        Map<String, User> userMap= client.getUsersList(new GetUsersRequest());
        Set<String> usersUserNameSet= client.getUsersList(new GetUsersRequest()).keySet();
        for(String s : usersUserNameSet){
            if(s.equals(user.getUsername()))continue;
            userListModel.addElement(userMap.get(s));
        }


        Dimension minmumWindowSize = new Dimension(500, 300);
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(rootPanel);
        setSize(screeSize.width * 3 / 5,screeSize.height * 3 / 5);
        setMinimumSize(minmumWindowSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeDragAndDrop();
        setDisplayName();
        //initChatRoomDisplay();
        setVisible(true);

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
