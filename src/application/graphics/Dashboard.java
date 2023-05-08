package application.graphics;

import application.Client;
import sharedresources.ChatRoom;
import sharedresources.requests.GetUsersRequest;
import sharedresources.User;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
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
    private JScrollPane chatRoomScrollPane;
    private JPanel chatRoomPanel;
    private JLabel displayNameLabel;
    private JList userList;
    private List<String> selectedUsernames = new ArrayList<>();
    private JButton createCapyHerdButton;
    private JPanel userPanel;
    private JButton createChatRoomButton;
    private ChatRoom displayedChatroom;
    private User user;
    private Client client;

    public Dashboard(Client client){

        this.client = client;
        user = client.getSessionUser();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        userList.setModel(listModel);
        userList.setCellRenderer(new UsernameListCellRenderer());
        userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedIndex = userList.locationToIndex(e.getPoint());
                if(clickedIndex != -1){
                    String clickedUsername = listModel.getElementAt(clickedIndex);
                    if (selectedUsernames.contains(clickedUsername)){
                        selectedUsernames.remove(clickedUsername);
                        userList.removeSelectionInterval(clickedIndex, clickedIndex);
                    }
                    else{
                        selectedUsernames.add(clickedUsername);
                        userList.addSelectionInterval(clickedIndex, clickedIndex);
                    }
                    System.out.println("Selected usernames: " + selectedUsernames);
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
//        initChatRoomDisplay();
        setVisible(true);

    }
/*
    public void initChatRoomDisplay(){

        List<ChatRoom> chatRooms = client.getChatRooms();
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

 */

    private void setDisplayName(){
        displayNameLabel.setText("Welcome " + user.getDisplayName() + ", have a capybara day!");
    }


    private void initializeDragAndDrop(){
        //TODO: ändra mainPanel nedan till den panelen som kommer displaya chatrummet.
        mainPanel.setDropTarget(new DropTarget(mainPanel, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transferable = dtde.getTransferable();
                    if (!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) dtde.rejectDrop();

                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    if(!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) throw new Exception();
                    Object object = transferable.getTransferData(DataFlavor.javaFileListFlavor);

                    if(object instanceof List<?> list){
                        for(Object o : list){
                            if(o instanceof File image){
                                client.sendImageMessage(image.getAbsolutePath(), displayedChatroom.getChatRoomID());
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    private void displayUsersList(){
        ArrayList<User> users = client.getUsersList(new GetUsersRequest());

        for(User user : users){

        }

    }




























/*
    public class ChatRoomListModel extends AbstractListModel<String> {
        private final List<ChatRoom> chatRooms;

        public ChatRoomListModel(List<ChatRoom> chatRooms) {
            this.chatRooms = chatRooms;
        }

        @Override
        public int getSize() {
            return chatRooms.size();
        }

        @Override
        public String getElementAt(int index) {
            ChatRoom chatRoom = chatRooms.get(index);
            List<SessionUser> users = chatRoom.getUsersInChatRoom();
            StringBuilder userDisplayNames = new StringBuilder();
            for (SessionUser user : users) {
                userDisplayNames.append(user.getDisplayname()).append(", ");
            }
            return "ChatRoom " + (index + 1) + ": " + userDisplayNames.toString();
        }
    }

    public class ChatRoomListCellRenderer extends JLabel implements ListCellRenderer<String> {
        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value);

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);

            return this;
        }
    }

    public class ChatRoomList extends JList<String> implements MouseListener{
        public ChatRoomList(List<ChatRoom> chatRooms) {
            super(new ChatRoomListModel(chatRooms));
            setCellRenderer(new ChatRoomListCellRenderer());
            addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Double-click
                        int selectedIndex = getSelectedIndex();
                        if (selectedIndex != -1) {
                            // Handle the chat room selection, e.g., display the chat room view
                            System.out.println("Chat room " + (selectedIndex + 1) + " selected");
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { // Double-click
                int selectedIndex = getSelectedIndex();
                if (selectedIndex != -1) {
                    // Handle the chat room selection, e.g., display the chat room view
                    System.out.println("Chat room " + (selectedIndex + 1) + " selected");
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

 */

}
