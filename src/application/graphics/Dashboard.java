package application.graphics;

import application.Client;
import sharedresources.ChatRoom;
import sharedresources.ImageMessage;
import sharedresources.User;
import sharedresources.requests.CreateNewChatRoom;
import sharedresources.requests.FetchAllUser;
import sharedresources.requests.SendMessage;

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
    private static final String GITHUB_URL = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
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
    private JButton refreshButton;
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


        DefaultListModel<String> chatRoomListModel = new DefaultListModel<>();
        chatRoomList.setModel(chatRoomListModel);
        chatRoomList.setCellRenderer(new ChatRoomListCellRenderer());

        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedUsernames.clear();
                    for (int index : userList.getSelectedIndices()) {
                        selectedUsernames.add(userListModel.getElementAt(index));
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
                ChatRoom chatRoom = client.addChatRoom(new CreateNewChatRoom(selectedUsers));
                if(chatRoom != null){
                    Chat chat = new Chat(client, chatRoom, user);
                    dispose();
                }
            }
        });

        Map<String, User> userMap= client.getUsersList(new FetchAllUser());
        Set<String> usersUserNameSet= client.getUsersList(new FetchAllUser()).keySet();
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
        setDisplayName();
        setVisible(true);

    }

    private void setDisplayName(){
        displayNameLabel.setText("Welcome " + user.getDisplayName() + ", have a capybara day!");
    }

}
